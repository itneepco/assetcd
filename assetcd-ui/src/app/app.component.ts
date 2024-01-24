import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { AppService } from './app.service';
import { AuthService } from './auth/auth.service';
import { AppEventService } from './services/app-event.service';
import { PersistanceService } from './services/persistance.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'assetcd-ui';
  company: any;

  errorMessage: any;
  authenticated = false;
  isAdmin = false;
  isEmployee = false;
  
  user = null;
  userFullName: string = '';

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor (
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    private authService: AuthService,
    private appService: AppService,
    private appEventService: AppEventService,
    private persistanceService: PersistanceService
  ) {}
  


  loadCompanyDetail() {
    this.appService.fetchCompany()
                    .subscribe(
                      success => {
                        this.company = success;
                      },
                      error =>  this.errorMessage = <any>error);
  }

  logout = () => {
    this.authService.logout()
                     .subscribe(
                       success  => {
                         this.user = null;
                         this.userFullName = '';
                         this.authenticated = false;
                         this.router.navigate(['/login']);
                        },
                       error =>  this.errorMessage = <any>error);
  }

  vewProfile = () => {
    this.router.navigateByUrl('/dashboard/viewuser?usercode=' + this.authService.getAuthState().user.name);
  }

  private authUpdate(authState: any) {

  }

  logout1(message) {
    this.user = null;
    this.userFullName = '';
    this.authenticated = false;
    this.loadCompanyDetail();
    this.router.navigate(['/login']);
  }

  drawerToggle() {
    this.appEventService.emitChange("drawer toggle");
  }



  ngOnInit() {

    this.loadCompanyDetail();
    let authState = this.authService.getAuthState();console.log(authState)
    //this.user = (authState.state === 'signedIn') ? authState.user : null
    if (authState.state === 'signedIn') {
      this.user = authState.user;
      this.userFullName = authState.user.firstName + ' ' + authState.user.lastName;
      this.authenticated = true;
    } else {
      this.user = null;
      this.userFullName = '';
      this.authenticated = false;
      this.router.navigate(['/login']);
    }


    this.appEventService.changeEmitted$
              .subscribe(
                change => {
                  if (change.name == 'login success') {
                    //this.updateMenuVars();
                    const authState = change.data;
                    this.user = authState.user;
                    this.userFullName = authState.user.firstName + ' ' + authState.user.lastName;
                    this.authenticated = true;
                  }  else if (change == 'relogin') {
                    this.logout();
                  } else if (change.name == '401-Unauthorised') {
                    this.logout1(change.value);
                  }
                }
              );

    //this.loadUserDetails();

    
  }

  


}
