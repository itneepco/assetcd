import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssetCodeComponent, RejectMappingCodeDialog } from './asset-code/asset-code.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { EditNewAssetCodeComponent } from './edit-new-asset-code/edit-new-asset-code.component';
import { EditRejAssetCodeComponent } from './edit-rej-asset-code/edit-rej-asset-code.component';
import { ResendRejAssetCodeComponent } from './resend-rej-asset-code/resend-rej-asset-code.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';



@NgModule({
  declarations: [
    AssetCodeComponent,
    RejectMappingCodeDialog,
    EditNewAssetCodeComponent,
    EditRejAssetCodeComponent,
    ResendRejAssetCodeComponent
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
    MatProgressSpinnerModule
  ],
  entryComponents: [
    RejectMappingCodeDialog
  ],
})
export class TransactionModule { }
