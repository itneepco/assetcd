import { Directive, Input, forwardRef } from '@angular/core';
import { AsyncValidatorFn, AsyncValidator, NG_ASYNC_VALIDATORS, AbstractControl, ValidationErrors } from '@angular/forms';
import { AdminService } from './admin.service';
import { Observable, timer } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

export function existingCodeValidator(adminService: AdminService, uce: any): AsyncValidatorFn {
  //return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
  return (control: AbstractControl): any => {
    let debounceTime = 500; //milliseconds
    return timer(debounceTime).pipe(switchMap(()=> {
      return adminService.fetchUserByUserCode(control.value).pipe(map(
        code => { console.log(code);
          return (code) ? {"userCodeExists": true} : null;
          //return (codes ) ? {"codeExists": true} : null;
        }
      ));
    }));
  };
} 

@Directive({
  selector: '[userCodeExists][formControlName],[userCodeExists][formControl],[userCodeExists][ngModel]',
  providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: forwardRef(() => ExistingUserCodeValidatorDirective), multi: true}]
})
export class ExistingUserCodeValidatorDirective implements AsyncValidator {
    @Input('userCodeExists') userCodeExists: any;
  constructor(private adminService: AdminService) {  }

  //validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
  validate(control: AbstractControl): any {
    return existingCodeValidator(this.adminService, this.userCodeExists)(control);  
  }
} 