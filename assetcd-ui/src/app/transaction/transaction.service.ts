import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  // fetch projects
  fetchProjects(): Observable<any> { 

    return this.http.get(environment.apiUrl + '/project/search/all').pipe(
        map((res: any) => res._embedded.projects || []),
        catchError(this.handleError<any[]>('fetchProjects', []))
      );
  }

  // fetch mapping codes for project
  fetchMappingCodes(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingcode/search/bypac1?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingcode/search/bypac?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingCodes || []),
        catchError(this.handleError<any[]>('fetchMappingCodes', []))
      );;
  }

  // fetch next mapping code for project
  fetchNextMappingCode(projCode, assetCode, mfglCode, select): Observable<any> { 

    let options = { withCredentials: true };
    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingcode/search/nextc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingcode/search/next?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url, options);
  }

  // fetch previous mapping code for project
  fetchPreviousMappingCode(projCode, assetCode, mfglCode, select): Observable<any> { 

    let options = { withCredentials: true };
    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingcode/search/prvc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingcode/search/prv?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url, options);
  }

  // fetch gl codes
  fetchGlCodes(): Observable<any> {
    return this.http.get(environment.apiUrl + '/gc/search/all').pipe(
      map((res: any) => res._embedded.glCodes)
    );
  }

  // fetch mfgl codes
  fetchMfglCodes(): Observable<any> {
    return this.http.get(environment.apiUrl + '/gmcc/search/all').pipe(
      map((res: any) => res._embedded.glMfglClassCodes)
    );
  }

  // fetch gl_mfgl_class_code by mfgl_code
  fetchGlMfglClassCodeByMfglCode(mfglCode: number): Observable<any> {
    return this.http.get(environment.apiUrl + '/gmcc/search/bym1?code=' + mfglCode);
  }


  // fetch codes (sub asset class codes)
  fetchCodes(table: string, code: string): Observable<any> { console.log(table); console.log(code);

    return this.http.get(environment.apiUrl + '/' + table + '/search/bycode1?code=' + code)
      .pipe(
        map((res: any) => res['_embedded'][table+'s'] || []),
        catchError(this.handleError<any[]>('fetchCodes', []))
      );
  }

  // fetch asset_code_mas
  fetchAssetCodeMaster(code: string): Observable<any> {
    return this.http.get(environment.apiUrl + '/acm/search/bync?newcode=' + code);
  }

  // fetch asset_code_mas_desc
  fetchAssetCodeMasDescs(code: string): Observable<any> {
    return this.http.get(environment.apiUrl + '/acmd/search/bync?newcode=' + code)
      .pipe(
        map((res: any) => res._embedded.assetCodeMasDescs || []),
        catchError(this.handleError<any[]>('fetchAssetCodeMasDesc', []))
      );
  }

  //fetch mas_desc
  fetchMasDesc(code: string, preDesc: string, postDesc: string, shortDesc: string) {
    return this.http.get(environment.apiUrl + 'acmd/search/byd?newcode=' + code + '&predesc=' + preDesc + '&postdesc=' + postDesc + '&shortdesc=' + shortDesc);
  }


  // save mapping new code
  saveMappingNewCode(mappingNewCode, mcId) : Observable<any> {console.log(mappingNewCode); console.log(mcId);

    return this.http.post(environment.apiUrl + '/tx/cmnc', {mappingNewCode: mappingNewCode, mappingCodeId: mcId});

  }

  // reject mapping code
  rejectMappingCode(mappingRejectedCode, mcId) : Observable<any> {

   return this.http.post(environment.apiUrl + '/tx/rmc', {mappingRejectedCode: mappingRejectedCode, mappingCodeId: mcId});

  }

  // update mapping new code
  updateMappingNewCode(mappingNewCode, mncId) : Observable<any> {

   return this.http.post(environment.apiUrl + '/tx/umnc', {mappingNewCode: mappingNewCode, mappingNewCodeId: mncId});

  }


  // update mapping rejected code
  updateMappingRejCode(mappingCode, mrcId) : Observable<any> {

    return this.http.post(environment.apiUrl + '/tx/umrc', {mappingCode: mappingCode, mappingRejectedCodeId: mrcId});

  }

  // resend mapping rejected code
  resendMappingRejCode(mappingRejectedCode) : Observable<any> {

   return this.http.post(environment.apiUrl + '/tx/rmrc', mappingRejectedCode);

  }



  // search mapping new codes for project by new asset code and new asset desc
  searchMappingNewCodes(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingnewcode/search/bync1?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingnewcode/search/bync?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingNewCodes || []),
        catchError(this.handleError<any[]>('fetchMappingNewCodes', []))
      );;
  }

  // fetch mapping new codes for project by new aaset code
  fetchMappingNewCodesByNewAssetCode(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingnewcode/search/byanc?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingnewcode/search/bypcnanc?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingNewCodes || []),
        catchError(this.handleError<any[]>('fetchMappingNewCodes', []))
      );;
  }

  // fetch mapping new codes for project
  fetchMappingNewCodes(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingnewcode/search/bypac1?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingnewcode/search/bypac?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingNewCodes || []),
        catchError(this.handleError<any[]>('fetchMappingNewCodes', []))
      );;
  }

  // fetch next mapping new code for project
  fetchNextMappingNewCode(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingnewcode/search/nextc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingnewcode/search/next?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
  }

  // fetch previous mapping new code for project
  fetchPreviousMappingNewCode(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingnewcode/search/prvc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingnewcode/search/prv?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
  }




  // fetch mapping rejected codes with status R for project
  fetchMappingRejCodesR(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/bypacr1?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/bypacr?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingRejectedCodes || []),
        catchError(this.handleError<any[]>('fetchMappingRejCodesR', []))
      );;
  }

  // fetch next mapping rejected code with status R for project
  fetchNextMappingRejCodeR(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/nextrc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/nextr?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
  }

  // fetch previous mapping rejected code with status R for project
  fetchPreviousMappingRejCodeR(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/prvrc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/prvr?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
  }


  // fetch mapping rejected codes with status S for project
  fetchMappingRejCodesS(projCode, assetCode): Observable<any> { 

    let url = "";
    if (projCode == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/bypacs1?assetcode='+assetCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/bypacs?projcode='+projCode+'&assetcode='+assetCode;
    }
    return this.http.get(url)
      .pipe(
        map((res: any) => res._embedded.mappingRejectedCodes || []),
        catchError(this.handleError<any[]>('fetchMappingRejCodesS', []))
      );;
  }

  // fetch next mapping rejected code with status S for project
  fetchNextMappingRejCodeS(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/nextsc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/nexts?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
  }

  // fetch previous mapping rejected code with status S for project
  fetchPreviousMappingRejCodeS(projCode, assetCode, mfglCode, select): Observable<any> { 

    let url = "";
    if (select == 'All') {
      url = environment.apiUrl + '/mappingrejcode/search/prvsc?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    } else {
      url = environment.apiUrl + '/mappingrejcode/search/prvs?projcode='+projCode+'&assetcode='+assetCode+'&mfglcode='+mfglCode;
    }
    return this.http.get(url);
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
