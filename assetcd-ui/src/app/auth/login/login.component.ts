import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppEventService } from 'src/app/services/app-event.service';
import { PersistanceService } from 'src/app/services/persistance.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  errorMessage: string = '';
  data: any;

  constructor(
    private authService: AuthService, 
    private persistanceService: PersistanceService,
    private appEventService: AppEventService,
    private router: Router
  ) { }

  login() {
    this.authService.login(this.data)
          .subscribe(
            success => {console.log(success)
              if (success.error) {
                this.errorMessage = success.error;
                
              } else {//console.log(1)
                this.errorMessage = '';
                this.router.navigate(['/'])

              }
            },
            error => {console.log(error);this.errorMessage = error}
          );

  }

  //logout() {}
  

  onSubmit(event: any) {
    event = event;
    this.login();
  }


  ngOnInit(): void {
    this.data = {username: "", passowrd: ""};
  }

}
