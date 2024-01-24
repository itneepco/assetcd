import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  urlsToSkipXsrfToken = [
    'company/1'
  ];

  constructor(
    private tokenExtractor: HttpXsrfTokenExtractor
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    //const accessToken = this.persistanceService.get('accessToken');
    let newReq = request;
    const token = this.tokenExtractor.getToken() as string;

    /*if (accessToken) {
      request = request.clone({ headers: request.headers.append('USER-KEY', accessToken) });
    }*/

    if (this.isSkipXsrfToken(request.url)) {
      request = request.clone({ withCredentials: true });
    } else {
      request = request.clone({ headers: request.headers.append('X-XSRF-TOKEN', token), withCredentials: true });
    }
    
    //console.log(request)
  
    return next.handle(request); 
  }

  private isSkipXsrfToken(url: string): boolean {//console.log(url)
    for (let u of this.urlsToSkipXsrfToken) {
      if (url.endsWith(u)) {
        return true;
      }
    }
    return false;
  }

}
