import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MessageService } from '../message.service';


@Injectable({
  providedIn: 'root'
})
export class MasterDataService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  // fetch projs
  fetchProjs(): Observable<any> { 

    return this.http.get(environment.apiUrl + '/project/search/al')
      .pipe(
        map((res: any) => res._embedded.projects || []),
        catchError(this.handleError<any[]>('fetchProjs', []))
      );
  }

  // save proj
  saveProj(proj: any) : Observable<any> {console.log(proj);

    return this.http.post(environment.apiUrl + '/project', proj);
  }

  // update proj
  updateProj(proj: any) : Observable<any> {console.log(proj);

   return this.http.put(environment.apiUrl + `/project/${proj.id}`, proj);
  }



  // fetch gl codes
  fetchGlCodes(code): Observable<any> {
    return this.http.get(environment.apiUrl + '/gc/search/all')
      .pipe(
        map((res:any) => res['_embedded']['glCodes'] || []),
        catchError(this.handleError<any[]>('fetchGlCodes', []))
      );
  }

  // fetch gl_mfgl_class_codes by gl_code
  fetchGlMfglClassCodesByGlCode(code: number): Observable<any> { console.log(code);

    return this.http.get(environment.apiUrl + '/gmcc/search/byglcode?code=' + code)
      .pipe(
        map((res:any) => res['_embedded']['glMfglClassCodes'] || []),
        catchError(this.handleError<any[]>('fetchGlMfglClassCodes', []))
      );
  }

  // fetch gl mfgl class codes
  fetchGlMfglClassCodes(code: string): Observable<any> { console.log(code);

    //return this.http.get(environment.apiUrl + '/search/bycode1?code=' + code)
    return this.http.get(environment.apiUrl + '/gmcc/search/all')
      .pipe(
        map((res:any) => res['_embedded']['glMfglClassCodes'] || []),
        catchError(this.handleError<any[]>('fetchGlMfglClassCodes', []))
      );
  }

  // fetch sub asset class codes
  fetchSubAssetClassCodes(code: string): Observable<any> { console.log(code);

    return this.http.get(environment.apiUrl + '/t0506/search/bycode1?code=' + code)
      .pipe(
        map((res:any) => res['_embedded']['t0506s'] || []),
        catchError(this.handleError<any[]>('fetchSubAssetClassCodes', []))
      );
  }

  // fetch codes
  fetchCodes(table: string, code: string): Observable<any> { console.log(table); console.log(code);

    return this.http.get(environment.apiUrl + '/' + table + '/search/bycode1?code=' + code)
      .pipe(
        map((res: any) => res['_embedded'][table+'s'] || []),
        catchError(this.handleError<any[]>('fetchCodes', []))
      );;
  }

  // save code
  saveCode(table: string, code: any): Observable<any> { console.log(table); console.log(code);

    return this.http.post(environment.apiUrl + '/' + table, code);
      /*.pipe(
        map(res => res['_embedded'][table+'s'] || { }),
        catchError(this.handleError<any[]>('saveCode', []))
      );;*/
  }

  // add new code
  addCode(table: string, code: any): Observable<any> { console.log(table); console.log(code);

    return this.http.post(environment.apiUrl + '/masdata/addcode', {code: code, table: table});
      /*.pipe(
        map(res => res['_embedded'][table+'s'] || { }),
        catchError(this.handleError<any[]>('saveCode', []))
      );;*/
  }

  // update code 
  updateCode(table: string, id: number, changes: any): Observable<any> { console.log(table); console.log(id); console.log(changes);

    return this.http.patch(environment.apiUrl + '/' + table + '/'+id, changes);

  }

  // upload codes 
  uploadCodes(file: any, ucc: any) : Observable<any> {

    const blobucc = new Blob([JSON.stringify(ucc)], {
      type: 'application/json',
    });

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);
    formData.append('ucc', blobucc);

    return this.http.post(environment.apiUrl + '/masdata/uploadcodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadCodes'))
      );
  }

  downloadErrors(uploadId: string): Observable<any> {

    let options = { withCredentials: true, responseType: "blob" };
    return this.http.get(environment.apiUrl + '/masdata/downloaderrors?uploadid=' + uploadId, { responseType: "blob" });
  }

  // upload mfgl_class_codes
  uploadGlMfglClassCodes(file: any, ucc: any) : Observable<any> {

    const blobucc = new Blob([JSON.stringify(ucc)], {
      type: 'application/json',
    });

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);
    formData.append('ucc', blobucc);

    return this.http.post(environment.apiUrl + '/masdata/uploadglmfglclasscodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadGlMfglClassCodes'))
      );
  }

  downloadGMCCErrors(uploadId: string): Observable<any> {

    let options = { withCredentials: true, responseType: "blob" };
    return this.http.get(environment.apiUrl + '/masdata/downloadgmccerrors?uploadid=' + uploadId, { responseType: "blob" });
  }



  // upload gl_codes
  uploadGlCodes(file: any, ucc: any) : Observable<any> {

    const blobucc = new Blob([JSON.stringify(ucc)], {
      type: 'application/json',
    });

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);
    formData.append('ucc', blobucc);

    return this.http.post(environment.apiUrl + '/masdata/uploadglcodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadGlCodes'))
      );
  }

  downloadGCErrors(uploadId: string): Observable<any> {

    let options = { withCredentials: true, responseType: "blob" };
    return this.http.get(environment.apiUrl + '/masdata/downloadgcerrors?uploadid=' + uploadId, { responseType: "blob" });
  }


  // export codes
  exportCodes(ecc: any): Observable<any> {

    return this.http.post(environment.apiUrl + '/masdata/exportcodes', ecc, { responseType: "blob" });

  }


  // add new gl mfgl class code
  addGlMfglClassCode(code: any): Observable<any> { console.log(code);

    return this.http.post(environment.apiUrl + '/masdata/addglmfglclasscode', code);
      /*.pipe(
        map(res => res['_embedded'][table+'s'] || { }),
        catchError(this.handleError<any[]>('saveCode', []))
      );;*/
  }

  // update gl_mfgl_class_code  
  updateGlMfglClassCode(id: number, changes: any): Observable<any> { console.log(id); console.log(changes);

    //return this.http.patch(environment.apiUrl + '/gmcc/'+id, changes);
    return this.http.post(environment.apiUrl+ '/masdata/updateglmfglclasscode/'+ id, changes);
  }

  // export gl_mfgl_class_code codes
  exportGlMfglClassCodes(ecc: any): Observable<any> {

    return this.http.post(environment.apiUrl + '/masdata/exportglmfglclasscodes', ecc, { responseType: "blob" });

  }



  // add new gl code
  addGlCode(code: any): Observable<any> { console.log(code);

    return this.http.post(environment.apiUrl + '/masdata/addglcode', code);
      /*.pipe(
        map(res => res['_embedded'][table+'s'] || { }),
        catchError(this.handleError<any[]>('saveCode', []))
      );;*/
  }

  // update gl_code  
  updateGlCode(id: number, changes: any): Observable<any> { console.log(id); console.log(changes);

    return this.http.patch(environment.apiUrl + '/gc/'+id, changes);

  }

  // export gl_code codes
  exportGlCodes(ecc: any): Observable<any> {

    return this.http.post(environment.apiUrl + '/masdata/exportglcodes', ecc, { responseType: "blob" });

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
