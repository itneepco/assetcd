import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { HelpComponent } from './help/help.component';
import { UploadMappedCodesComponent } from './upload-mapped-codes/upload-mapped-codes.component';
import { UploadAssetCodesComponent } from './upload-asset-codes/upload-asset-codes.component';
import { UploadNewAssetCodesComponent } from './upload-new-asset-codes/upload-new-asset-codes.component';



@NgModule({
  declarations: [
    UploadAssetCodesComponent,
    UploadMappedCodesComponent,
    HelpComponent,
    UploadNewAssetCodesComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule, 
    MatCardModule, 
    MatToolbarModule, 
    MatIconModule, 
    MatInputModule, 
    MatFormFieldModule,
    MatDividerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatListModule,
    MatProgressSpinnerModule
  ]
})
export class SvModule { }
