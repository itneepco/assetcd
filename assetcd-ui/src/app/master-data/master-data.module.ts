import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddUpdateProjComponent } from './add-update-proj/add-update-proj.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatListModule } from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MasterSchemeComponent } from './master-scheme/master-scheme.component';
import { MfglComponent } from './mfgl/mfgl.component';
import { GlComponent } from './gl/gl.component';



@NgModule({
  declarations: [
    AddUpdateProjComponent,
    MasterSchemeComponent,
    MfglComponent,
    GlComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule, 
    MatGridListModule,
    MatCardModule, 
    MatToolbarModule, 
    MatIconModule, 
    MatInputModule, 
    MatFormFieldModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatDividerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatListModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule

  ]
})
export class MasterDataModule { }
