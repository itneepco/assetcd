import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-report-view-dialog',
  templateUrl: './report-view-dialog.component.html',
  styleUrls: ['./report-view-dialog.component.scss']
})
export class ReportViewDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ReportViewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }



  ngOnInit() {
  }

}
