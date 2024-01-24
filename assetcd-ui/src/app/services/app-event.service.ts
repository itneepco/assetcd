import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppEventService {

  constructor() { }

  // Observable string sources
  private emitChangeSource = new Subject<any>();
  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();
  
  // Service message commands
  emitChange(change: any) {
      this.emitChangeSource.next(change);
  }

}

export interface AppEvent {
  name: string; // signedOut, signedIn etc.
  type: string;
  data: any;
}

