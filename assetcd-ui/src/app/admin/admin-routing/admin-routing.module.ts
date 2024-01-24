import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SearchUserComponent } from '../search-user/search-user.component';
import { NewUserComponent } from '../new-user/new-user.component';
//import { EditUserComponent } from '../edit-user/edit-user.component';
import { ViewUserComponent } from '../view-user/view-user.component';
import { ChangePasswordComponent } from '../change-password/change-password.component';


const adminRoutes: Routes = [
  /*{
    path: 'searchuser',
    component: SearchUserComponent,
  },
  {
    path: 'newuser',
    component: NewUserComponent,
  },
  /*{
    path: 'edituser/:id',
    component: EditUserComponent,
  },*
  {
    path: 'viewuser/:id',
    component: ViewUserComponent,
  },
  /*{
    path: 'edituser',
    component: EditUserComponent,
  },*
  {
    path: 'viewuser',
    component: ViewUserComponent,
  },
  {
    path: 'changepassword',
    component: ChangePasswordComponent,
  }*/
];

@NgModule({
  imports: [
    RouterModule.forChild(adminRoutes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
  ]
})
export class AdminRoutingModule { }
