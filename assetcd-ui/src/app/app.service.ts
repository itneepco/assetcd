import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(
    private http: HttpClient
  ) { }

  // fetch company detail
  fetchCompany(): Observable<any> { 

    //let options = { withCredentials: true };
    //return this.http.get(environment.apiUrl + '/company/1', options);
    return this.http.get(environment.apiUrl + '/company/1');
  }

  downloadReport(): any {
    let options = { responseType: "blob" };
    return this.http.get(environment.apiUrl + '/report/tabstats', { responseType: "blob" });
  }


}
