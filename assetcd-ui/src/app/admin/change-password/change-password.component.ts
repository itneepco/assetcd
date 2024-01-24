import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router }       from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AdminService } from '../admin.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  //private subscription: Subscription;
  //private qsubscription: Subscription;

  errorMessage:any;

  error:string = '';

  userId:number = 0;
  userCode:string = '';

  currentPassword:string = '';
  newPassword:string = '';
  cNewPassword:string = '';

  constructor(
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public snackBar: MatSnackBar
  ) { }

  changePassword() {
    this.error = '';
    let data = {currentPassword: this.currentPassword, newPassword: this.newPassword, userCode: this.userCode};
    this.adminService.changePassword(data)
                      .subscribe(
                        success => {console.log(success);
                          if (success.error) {
                            this.error = success.error;
                          } else {
                            this.openSnackBar("Password successfully changed.", "")
                          }
                        },
                        error => {console.log(error)}
                      );

  }

  cancel() {
    this.router.navigateByUrl('/dashboard/viewuser?usercode=' + this.userCode);
  }

  openSnackBar(message: string, action: string) {
    let snackBarRef = this.snackBar.open(message, action, {
      duration: 3000,
    });

    snackBarRef.afterDismissed().subscribe(() => {
      // go to user profile page
      this.router.navigateByUrl('/dashboard/viewuser?usercode=' + this.userCode);
    });


    snackBarRef.onAction().subscribe(() => {
      console.log('The snack-bar action was triggered!');
    });

  }

  ngOnInit() {

    /*this.subscription = this.activatedRoute.params.subscribe(
      (param: any) => {
        this.id = param['id'];
    });*/

    /*this.qsubscription = this.activatedRoute.queryParams.subscribe(
      (param: any) => {
        this.userCode = param['usercode'];
    });*/

    this.activatedRoute.queryParams.subscribe(
      (param: any) => {
        this.userCode = param['usercode'];
    });
  }

}
