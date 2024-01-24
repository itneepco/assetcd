import { Injectable } from '@angular/core';
import { HttpClient, HttpXsrfTokenExtractor } from '@angular/common/http';
import { HttpHeaders, HttpResponse, HttpParams, HttpErrorResponse } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { MessageService } from '../message.service';
import { Observable, of } from 'rxjs';

@Injectable()
export class AdminService {

  constructor(
    private http: HttpClient,
    private tokenExtractor: HttpXsrfTokenExtractor,
    private messageService: MessageService
  ) { }


  // search users
  searchUsers(searchBy: string, value: string): Observable<any> { //console.log(searchBy); console.log(value);

    let options = { withCredentials: true };
    let url = '';

    if (searchBy == 'user_code') {
      url = environment.apiUrl + '/ruser/search/byuc?usercode=' + value;
    } else if (searchBy == 'user_name') {
      url = environment.apiUrl + '/ruser/search/byname?name=' + value;
    } else if (searchBy == 'email') {
      url = environment.apiUrl + '/ruser/search/byemail?email=' + value;
    }
    return this.http.get(url, options)
      .pipe(
        map((res: any) => res['_embedded']['users'] || { }),
        catchError(this.handleError<any[]>('searchUsers', []))
      );;
  }


  // create user
  createUser(user: any) {

    return this.http.post(environment.apiUrl + '/a/createuser', user);

  }

  // update user 
  updateUser(id: number, changes: any) {
    return this.http.patch(environment.apiUrl + '/ruser/'+id, changes);
  }

  // fetch user by user code
  fetchUserByUserCode(userCode: string) {

    return this.http.get(environment.apiUrl + '/ruser/search/byuc1?projection=default&uc=' + userCode)
                      .pipe(
                        catchError(this.handleError<any>('fetchUserByUserCode', null))
                      );
  }

  // fetch user by id
  fetchUserById(id: number) {

    return this.http.get(environment.apiUrl + '/ruser/{id}' + '?projection=default')
                      .pipe(
                        catchError(this.handleError<any>('fetchUserById', null))
                      );
  }

  // fetch roles
  fetchRoles() {

    return this.http.get(environment.apiUrl + '/rrole')
                      .pipe(
                        map((res: any) => res['_embedded']['roles'] || { }),
                        catchError(this.handleError<any>('fetchRoles', []))
                      );
  }

  // add role 
  addRole(userId: number, roleId: number) {
    return this.http.get(environment.apiUrl + '/a/addrole?userid='+userId+'&roleid='+roleId);
  }

  // remove role
  removeRole(userId: number, roleId: number) {
    return this.http.get(environment.apiUrl + '/a/removerole?userid='+userId+'&roleid='+roleId)
  }

  // resetPassword
  resetPassword(userId: string) : Observable<any> {

    let params = new HttpParams();
    params = params.set('userid', userId);

    
    return this.http.post(environment.apiUrl + '/a/resetpassword', params);
  }

  // changePassword
  changePassword(data: any) : Observable<any>{

    let formData = new FormData();
    formData.append('currentpassword', data.currentPassword);
    formData.append('newpassword', data.newPassword);
    formData.append('usercode', data.userCode);

     let params = new HttpParams();
    params = params.set('currentpassword', data.currentPassword);
    params = params.set('newpassword', data.newPassword);
    params = params.set('usercode', data.userCode);

    return this.http.post(environment.apiUrl + '/u/changepwd', params);

  }


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add('AdminService: ' + message);
  }

}

