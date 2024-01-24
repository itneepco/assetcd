import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root'
})
export class SvService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  // fetch mfgls
  fetchMfgls(): Observable<any> {
    return this.http.get(environment.apiUrl + '/masdata/mfgl');
  }

  // upload asset codes 
  uploadAssetCodes(file) : Observable<any> {

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);

    return this.http.post(environment.apiUrl + '/sv/uploadassetcodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadAssetCodes'))
      );
  }

  downloadErrors(uploadId): Observable<any> {

    let options = { responseType: "blob" };
    return this.http.get(environment.apiUrl + '/sv/downloaderrors?uploadid=' + uploadId, { responseType: "blob" });
  }

  // upload new asset codes 
  uploadNewAssetCodes(file) : Observable<any> {

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);

    return this.http.post(environment.apiUrl + '/sv/uploadnewassetcodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadNewAssetCodes'))
      );
  }

  downloadNewAssetCodesErrors(uploadId): Observable<any> {

    let options = { responseType: "blob" };
    return this.http.get(environment.apiUrl + '/sv/downloadnacerrors?uploadid=' + uploadId, { responseType: "blob" });
  }

  // upload mapped codes 
  uploadMappedCodes(file) : Observable<any> {

    let formData = new FormData();
    //formData.append('design', id);
    formData.append('file', file, file.name);

    return this.http.post(environment.apiUrl + '/sv/uploadmappingnewcodes', formData).pipe(
        //map(this.extractUploadDData),
        catchError(this.handleError<any>('uploadMappedCodes'))
      );
  }

  downloadMappedCodeErrors(uploadId): Observable<any> {

    let options = { responseType: "blob" };
    return this.http.get(environment.apiUrl + '/sv/downloadmncerrors?uploadid=' + uploadId, { responseType: "blob" });
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


