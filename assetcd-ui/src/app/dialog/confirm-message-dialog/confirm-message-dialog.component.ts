import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-message-dialog',
  templateUrl: './confirm-message-dialog.component.html',
  styleUrls: ['./confirm-message-dialog.component.scss']
})
export class ConfirmMessageDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ConfirmMessageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ok() {
    this.dialogRef.close({ action: 'ok' });
  }

  cancel() {
    this.dialogRef.close({ action: 'cancel' });
  }

  ngOnInit() {
    this.dialogRef.disableClose = true;
  }

}
