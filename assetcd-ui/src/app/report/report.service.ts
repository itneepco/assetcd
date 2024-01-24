import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  // fetch projects
  fetchProjects(): Observable<Project[]> { 

      return this.http.get(environment.apiUrl + '/project/search/all').pipe(
        map((res: any) => res._embedded.projects || []),
        catchError(this.handleError<Project[]>('fetchProjects', []))
      );
                  

  }

  exportMNC(projCodes: string[], assetCode: string): any {
    let body = { projCodes, assetCode };
    return this.http.post(environment.apiUrl + '/report/exportmnc', body, { responseType: "blob" });
  }

  downloadReport(projCodes, codeStart: string, urlstr: string): any {
    let body = {projCodes: projCodes, codeStart: codeStart};
    let options = { responseType: "blob" };
    //return this.http.post(environment.apiUrl + '/report/matcode', body, { headers: headers, withCredentials: true, responseType: "blob" });
    return this.http.post(environment.apiUrl + urlstr, body, { responseType: "blob" });
    /*return this.http 
    .get(`http://localhost:8080/report/matcode?projs=1000,2000`, { withCredentials: true, responseType: "blob" });*/
    
  }

  rejStatusReport(projCodes): any {
    
    let options = { responseType: "blob" };
    return this.http.post(environment.apiUrl + '/report/rejstatus', projCodes, { responseType: "blob" });
    
  }

  codeMappingProgressReport(projCodes): any {
    let options = { responseType: "blob" };
    return this.http.post(environment.apiUrl + '/report/codemappingprogress', projCodes, { responseType: "blob" });
    
  }

  coderProgressReport(): any {
    let options = { responseType: "blob" };
    return this.http.post(environment.apiUrl + '/report/coderprogress', {}, { responseType: "blob" });
    
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
    this.messageService.add('MasterDataService: ' + message);
  }

}



export class Project {
  constructor(
    /*public id: number;
    public projCode: string;
    public projDesc: string;
    public projLoc: string;
    public status: string;
    public title: string;*/
    ) { }

    public id: number;
    public projCode: string;
    public projDesc: string;
    public projLoc: string;
    public status: string;
    public title: string;
    
}

