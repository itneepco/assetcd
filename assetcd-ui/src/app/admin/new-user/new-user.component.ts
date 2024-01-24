import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AdminService } from '../admin.service';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.scss']
})
export class NewUserComponent implements OnInit {

  user: any;

  errorMessage: any;

  
  constructor(
    private router: Router,
    private adminService: AdminService
  ) { }

  suggestUserCode() {
    //let f = this.user.firstName.toLowerCase;
    this.user.userCode = this.user.lastName.toLowerCase() + this.user.firstName.toLowerCase().slice(0, 1);
  }

  create() {
    this.adminService.createUser(this.user)
                        .subscribe(
                          success => {
                            console.log(success),
                            this.router.navigateByUrl('/dashboard/viewuser?usercode=' + this.user.userCode);
                          },
                          error => { console.log(error)}
                        );
  }

  ngOnInit() {
    this.user = {firstName: "", lastName: "", userCode: "", email: ""}
  }

}