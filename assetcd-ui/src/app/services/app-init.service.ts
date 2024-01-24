import { Location } from '@angular/common';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AppInitService {

  constructor(
    private location: Location
  ) { }

  loadConfig() {

    //console.log(this.location.path());
    //console.log(window.location.href);
    const url = new URL(window.location.href);
    //console.log(url.hostname)
    //console.log(url.origin)
    //console.log(url.port)
    //console.log(url.protocol)
    if (environment.production) {
      environment.apiUrl = url.origin;
      environment.authUrl = url.origin;
      environment.imgUrl = url.origin;
    }
    //console.log(window.navigator.userAgent);
  }
}
