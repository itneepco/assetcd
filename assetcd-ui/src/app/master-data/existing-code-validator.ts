import { Directive, Input, forwardRef } from '@angular/core';
import { AsyncValidatorFn, AsyncValidator, NG_ASYNC_VALIDATORS, AbstractControl, ValidationErrors } from '@angular/forms';
import { Observable, of, timer } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { MasterDataService } from './master-data.service';

export function existingCodeValidator(masterDataService: MasterDataService, auc: any): AsyncValidatorFn {
  //return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
  return (control: AbstractControl): any => {
      if (auc.tablel != control.value.length) {
          return of(null);
      }
    let debounceTime = 500; //milliseconds
    return timer(debounceTime).pipe(switchMap(()=> {
      let codeStart = (auc.parentGroup)? auc.parentCode.slice(0, -1* auc.parentAbsCode.length) : auc.parentCode;
      //return masterDataService.fetchCodes(auc.table, auc.parentCode + control.value).map(
      return masterDataService.fetchCodes(auc.table, codeStart + control.value).pipe(map(
        (codes: any) => {
          return (codes && codes.length > 0) ? {"codeExists": true} : null;
          //return (codes ) ? {"codeExists": true} : null;
        }
      ));
    }));
  };
} 

@Directive({
  selector: '[codeExists][formControlName],[codeExists][formControl],[codeExists][ngModel]',
  providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: forwardRef(() => ExistingCodeValidatorDirective), multi: true}]
})
export class ExistingCodeValidatorDirective implements AsyncValidator {
    @Input('codeExists') codeExixts: any;
  constructor(private masterDataService: MasterDataService) {  }

  //validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
  validate(control: AbstractControl): any {
    return existingCodeValidator(this.masterDataService, this.codeExixts)(control);  
  }
} 