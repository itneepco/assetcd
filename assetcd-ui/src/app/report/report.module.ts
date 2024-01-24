import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssetDetailComponent } from './asset-detail/asset-detail.component';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatLineModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MatDialogModule } from '@angular/material/dialog';
import { MatRadioModule } from '@angular/material/radio';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { ReportViewDialogComponent } from './report-view-dialog/report-view-dialog.component';
import { RejStatusComponent } from './rej-status/rej-status.component';
import { CodeMappingProgressComponent } from './code-mapping-progress/code-mapping-progress.component';
import { CoderProgressComponent } from './coder-progress/coder-progress.component';



@NgModule({
  declarations: [
    AssetDetailComponent,
    ReportViewDialogComponent,
    RejStatusComponent,
    CodeMappingProgressComponent,
    CoderProgressComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatIconModule, 
    MatFormFieldModule,
    MatCardModule,
    MatLineModule,
    MatListModule,
    MatCheckboxModule,
    MatDialogModule,
    MatRadioModule
  ],
  entryComponents: [
    ReportViewDialogComponent
  ],
})
export class ReportModule { }
