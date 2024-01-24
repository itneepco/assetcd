import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router }       from '@angular/router';


import { AdminService } from '../admin.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {

  user: any;

  constructor(
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }


  saveChanges () {
    let changes = {firstName: this.user.firstName, lastName: this.user.lastName, email: this.user.email};
    this.adminService.updateUser(this.user.id, changes)
                        .subscribe(
                          success => {console.log(success)},
                          error => { console.log(error)}
                        );
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      (param: any) => {
        this.user = param['user'];console.log(this.user);
    });
  }

}
