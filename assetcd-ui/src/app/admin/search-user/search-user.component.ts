import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
//import {MatPaginator, MatTableDataSource} from '@angular/material';

import { AdminService } from '../admin.service';

@Component({
  selector: 'app-search-user',
  templateUrl: './search-user.component.html',
  styleUrls: ['./search-user.component.scss']
})
export class SearchUserComponent implements OnInit {

  errorMessage: any;

  searchBy = "user_code"
  userCode = "";
  userName = "";
  email = "";

  users = [];

  /*displayedColumns = ['firstName', 'lastName', 'email', 'userCode'];
  dataSource = new MatTableDataSource(this.users);*/
  
  constructor(
    private router: Router,
    private adminService: AdminService
  ) { }


  createUser () {
    this.router.navigate(['dashboard/newuser']);
  }

  search () {
    let value = '';
    if (this.searchBy == 'user_code') {
      value = this.userCode;
    } else if (this.searchBy == 'user_name') {
      value = this.userName;
    } else if (this.searchBy == 'email') {
      value = this.email;
    }
    this.adminService.searchUsers(this.searchBy, value)
                .subscribe(
                  success => {this.users = success},
                  error => {this.errorMessage = <any>error; this.users = []}
                );
  }


  ngOnInit() {
  }

}
