import { HttpClient, HttpHeaders, HttpParams, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AppEventService } from '../services/app-event.service';
import CommonUtils from '../services/apputil';
import { PersistanceService } from '../services/persistance.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _user : any = null;

  constructor(
    private http: HttpClient,
    private tokenExtractor: HttpXsrfTokenExtractor,
    private persistanceService: PersistanceService,
    private appEventService: AppEventService
  ) { }

  getAuthState() {
    let authState = this.persistanceService.getFromSession('authState');console.log(authState)
    if (CommonUtils.isEmpty(authState)) {
      //const user = this.ud();
      this.ud();
      authState = (this._user && this._user.name) ? { state: 'signedIn', user: this._user} : { state: 'signedOut', user: null};
      this.persistanceService.setToSession('authState', authState);
    } 
    return authState;
  } 

  currentAuthenticatedUser() {
    // check in sessionstore
    //if set in sessionstore return the value from session store
    // else fetch from user2 and set to session store and return the fetched values 
    let authState = this.persistanceService.getFromSession('authState');
    if (CommonUtils.isEmpty(authState)) {
      //const user = this.ud();
      this.ud();
      authState = (this._user) ? { state: 'signedIn', user: this._user} : { state: 'signedOut', user: null};
      this.persistanceService.setToSession('authState', authState);
    } 
    return authState.user;
  }

  private async ud() {//console.log(1)
    //let user = null;
    try {
      this._user = await this.http.get(environment.authUrl + '/user2').toPromise();
      console.log(this._user);
      //return user;
    } catch (err) {
      this._user = null;
      console.log(err);
    }

    //return user;
  }

  getUserDetails(): Observable<any> {
    let params = new HttpParams();
    //params.set('projection', 'mini');
        
    //let options = { params: params, withCredentials: true };

    //return this.http.get(environment.authUrl + '/user2', options);
    //return this.http.get('localhost:8080/user2', options);
    return this.http.get(environment.authUrl + '/user2');
  }

  login(data: any): Observable<any> {

    let params = new HttpParams();
    params = params.set('username', data.username);
    params = params.set('password', data.password);

    /*let formData = new FormData();
    formData.append('username', data.username);
    formData.append('password', data.password);*/

    const token = this.tokenExtractor.getToken() as string;
    //console.log(token);

	  let headers = new HttpHeaders({ 
          'Content-Type': 'application/x-www-form-urlencoded',
          //'X-XSRF-TOKEN': token
    });
	  let options = { headers: headers, withCredentials: true };

    return this.http.post(environment.authUrl + '/login', params, options)
                    .pipe(
                      tap(
                        data => {
                          if (!data.error) {
                            const authState = { state: 'signedIn', user: data };
                            this.persistanceService.setToSession('authState', authState);
                            this.appEventService.emitChange({ name: "login success", type: "authStateChange", data: authState });
                          }
                        },
                        error => {}
                      )
                    )
    // handle 401 error - bad credentials
    //return this.http.post('http://localhost:8080/login', params, options);
  }

  logout() {

    let body = JSON.stringify({  });
    const token = this.tokenExtractor.getToken() as string;
    let headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      //'X-XSRF-TOKEN': token
    });
    let options = { headers: headers, withCredentials: true };

    return this.http.post(environment.authUrl + '/logout', {}, options)
                      .pipe(
                      tap(
                        data => {
                          const authState = { state: 'signedOut', user: null };
                          this.persistanceService.setToSession('authState', authState);
                          this.appEventService.emitChange({ name: "logout success", type: "authStateChange", data: authState });
                        },
                        error => {}
                      )
                    )
    //return this.http.post('http://localhost:8080/logout', {}, options);

  }



}



export interface AuthState {
  state: string; // signedOut, signedIn etc.
  user: any;
}
