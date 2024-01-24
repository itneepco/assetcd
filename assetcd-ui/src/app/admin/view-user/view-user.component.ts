import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router }       from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Subscription }     from 'rxjs';

import { AdminService } from '../admin.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.scss']
})
export class ViewUserComponent implements OnInit, OnDestroy  {

  private subscription: Subscription;
  private qsubscription: Subscription;
  userCode: string = '';
  id: any;
  user:any;
  roles = [];
  filteredRoles:any = [];

  isEdit = false;
  isAdmin = false;
  isCurrentUser = false;
  currentUser: any = null;

  //edit fields 
  firstName = '';
  lastName = '';
  email = '';


  constructor(
    private adminService: AdminService,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public snackBar: MatSnackBar
  ) { 
    this.subscription = this.activatedRoute.params.subscribe(
      (param: any) => {
        this.id = param['id'];
        if (this.id) this.fetchUser();
    });

    this.qsubscription = this.activatedRoute.queryParams.subscribe(
      (param: any) => {
        this.userCode = param['usercode'];
        if (this.userCode) this.fetchUserByUserCode();
    });
  }

  fetchUser() {
    this.adminService.fetchUserById(this.id)
                        .subscribe(
                          success => {
                            this.user = success; 
                            this.filterRoles(); 
                            this.isCurrentUser = (this.user.userCode == this.currentUser.name);
                            console.log(success)
                          },
                          error => {this.user = null; console.log(error)}
                        );
  }

  fetchUserByUserCode() {
    this.adminService.fetchUserByUserCode(this.userCode)
                        .subscribe(
                          success => {this.user = success; 
                            this.filterRoles(); 
                            this.isCurrentUser = (this.user.userCode == this.currentUser.name);
                            console.log(success)
                          },
                          error => {this.user = null; console.log(error)}
                        );
  }

  fetchRoles() {
    this.adminService.fetchRoles()
                        .subscribe(
                          success => {this.roles = success; this.filterRoles(); console.log(success)},
                          error => {this.user = null; console.log(error)}
                        );
  }

  filterRoles() {
    if (this.user)
    this.filteredRoles = this.roles.filter((x:any) => !this.user.roles.some((y:any) => x.name == y.name));
  }

  addRole(role: any) {
    this.adminService.addRole(this.user.id, role.id)
          .subscribe(
              success => {
                this.user.roles.push(role); this.filteredRoles = this.filteredRoles.filter((x:any) => x.id != role.id);
                 console.log(success)
                },
              error => {console.log(error)}
            );
  }

  deleteRole(role: any) {
    this.adminService.removeRole(this.user.id, role.id)
          .subscribe(
              success => {
                this.user.roles = this.user.roles.filter((x:any) => x.id != role.id); 
                this.filteredRoles.push(role); 
                console.log(success)
              },
              error => {console.log(error)}
            );
  }

  edit() {
    //this.router.navigate(['/edituser', {user: this.user}]);
    this.firstName = this.user.firstName;
    this.lastName = this.user.lastName;
    this.email = this.user.email;
    this.isEdit = true;
  }

  saveChanges() {
    let changes = {firstName: this.firstName, lastName: this.lastName, email: this.email};
    this.adminService.updateUser(this.user.id, changes)
                        .subscribe(
                          success => { 
                            this.user.firstName = this.firstName; 
                            this.user.lastName = this.lastName;
                            this.user.email = this.email;
                            this.isEdit = false; 
                            console.log(success);
                          },
                          error => { console.log(error)}
                        );
  }

  cancelChanges() {
    this.isEdit = false;
  }

  resetPassword() {
    this.adminService.resetPassword(this.user.id)
                      .subscribe(
                        success => {
                          console.log();
                          this.openSnackBar("Password reset successfully.", "")
                        },
                        error => {
                          console.log();
                          this.openSnackBar("Password reset failed.", "")
                        }
                      );
  }

  changePassword() {
    //this.router.navigate(['/changepassword', {usercode: this.user.userCode}]);
    this.router.navigateByUrl('/dashboard/changepassword?usercode=' + this.user.userCode);
  }

  openSnackBar(message: string, action: string) {
    let snackBarRef = this.snackBar.open(message, action, {
      duration: 3000,
    });

    snackBarRef.afterDismissed().subscribe(() => {
      //
    });


    snackBarRef.onAction().subscribe(() => {
      console.log('The snack-bar action was triggered!');
    });

  }

  ngOnInit() {
    this.currentUser = this.authService.getAuthState().user;
    const roles = this.currentUser.roles as string[];
    this.isAdmin = roles.some(x => x === 'Administrator');//(roles.findIndex(x => x === 'Administrator')) !== -1;
    if (this.isAdmin) this.fetchRoles();

    /*this.subscription = this.activatedRoute.params.subscribe(
      (param: any) => {
        this.id = param['id'];
        if (this.id) this.fetchUser();
    });

    this.qsubscription = this.activatedRoute.queryParams.subscribe(
      (param: any) => {
        this.userCode = param['usercode'];
        if (this.userCode) this.fetchUserByUserCode();
    });*/
  }

  ngOnDestroy() {
    // prevent memory leak by unsubscribing
    this.subscription.unsubscribe();
    this.qsubscription.unsubscribe();
  }


}



/// reference 
/*
var myArrayFiltered = myArray.filter(function (el) {
    return myFilter.some(function (f) {
        return f.userid === el.userid && f.projectid === el.projectid;
    });
});

*/
