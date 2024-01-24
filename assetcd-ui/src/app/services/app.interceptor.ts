import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { Observable } from 'rxjs/Observable';
import { tap } from 'rxjs/operators';
import { MessageDialogComponent } from '../dialog/message-dialog/message-dialog.component';
import { ConfirmMessageDialogComponent } from '../dialog/confirm-message-dialog/confirm-message-dialog.component';
import { AppEventService } from '../services/app-event.service';
import { PersistanceService } from './persistance.service';



//import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class AppInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
    private appEventService: AppEventService,
    private persistanceService: PersistanceService,
    public dialog: MatDialog
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          // do stuff with response if you want
        }
      }, (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401) {
            // redirect to the login route
            // or show a modal
            const authState = { state: 'signedOut', user: null };
            this.persistanceService.setToSession('authState', authState);
            this.appEventService.emitChange({name: "401-Unauthorised", value: err.error});
          } else if (err.status === 500) {
            this.msg500(err);
          } else if (err.status === 403) {
            this.cnfMsg403(err);
          }
        }
      }
      ));
  }


  msg500(err) {console.log(err)
    let dialogRef = this.dialog.open(MessageDialogComponent, {
      width: '400px',
      height: '250px',
      data: { title: "Error!", messages: ['Some server internal error. Please contact Admin with following details.', JSON.stringify(err.error)] }
    });
  }

  cnfMsg403(err) {
    let dialogRef = this.dialog.open(ConfirmMessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Access Error!", message: "You do not have access to this resource. Please contact administrator or login with other credentials. Do you want to login with other credentials now?" }
    });

    dialogRef.afterClosed().subscribe(result => {console.log(result);
      console.log('The dialog was closed');
      if (result.action == 'ok') {
        //this.router.navigate(['/login']);
        this.appEventService.emitChange('relogin');
      }
    });
  }

}