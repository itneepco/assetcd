import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule }    from '@angular/forms';

import { SearchUserComponent } from './search-user/search-user.component';
import { NewUserComponent } from './new-user/new-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { AdminRoutingModule } from './admin-routing/admin-routing.module';
import { AdminService } from './admin.service';
import { ExistingUserCodeValidatorDirective } from './existing-usercode-validator';
import { ViewUserComponent } from './view-user/view-user.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatLineModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import {MatRadioModule} from '@angular/material/radio';
import {MatTableModule} from '@angular/material/table';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule, 
    MatCardModule,
    MatTableModule, 
    MatToolbarModule, 
    MatIconModule, 
    MatInputModule, 
    MatFormFieldModule,
    MatLineModule,
    MatListModule,
    MatRadioModule,
    MatDividerModule,
    MatSnackBarModule
  ],
  declarations: [
    SearchUserComponent, 
    NewUserComponent, 
    EditUserComponent,
    ExistingUserCodeValidatorDirective,
    ViewUserComponent,
    ChangePasswordComponent
  ],
  providers: [
    AdminService
  ]
})
export class AdminModule { }
