import { Component, OnInit, ViewChild } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
import { AppEventService } from '../services/app-event.service';
import { MatSidenav, MAT_DRAWER_DEFAULT_AUTOSIZE_FACTORY } from '@angular/material/sidenav';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @ViewChild('drawer') drawer: MatSidenav;

  errorMessage: any = '';
  pageTitle: string = '';

  isAdmin = false;
  //isEmployee = false;
  isSystemUser = false;
  isAssetIssuer = false;
  isHOD = false;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    private appService: AppService,
    private appEventService: AppEventService,
    private authService: AuthService
  ) {}

  txAssetCode = () => {
    //this.pageTitle = "Mapping New Code";
    this.router.navigate(['/dashboard/assetcode']);
    this._dToggle();
  }

  txEditNewAssetCode = () => {
    this.router.navigate(['/dashboard/editmappingcode']);
    this._dToggle();
  }

  txEditInsufficientInputRejectedAssetCode = () => {
    this.router.navigate(['/dashboard/editiirmappingcode']);
    this._dToggle();
  }

  txResendSchemeNotFoundRejectedAssetCode = () => {
    this.router.navigate(['/dashboard/resemdsnfrmappingcode']);
    this._dToggle();
  }

  rAssetDetail = (p: any) => { console.log(p)
    this.router.navigate(['/dashboard/assetdetail', p]);
    this._dToggle();
  }

  rRejStatus = () => {
    this.router.navigate(['/dashboard/rejstatus']);
    this._dToggle();
  }

  rCodeMappingProgress = () => {
    this.router.navigate(['/dashboard/codemappingprogress']);
    this._dToggle();
  }

  rCoderProgress = () => {
    this.router.navigate(['/dashboard/coderprogress']);
    this._dToggle();
  }

  addUpdateCode = () => {
    this.router.navigate(['/dashboard/mscheme']);
    this._dToggle();
  }

  /*addUpdateAttr = () => {
    this.router.navigate(['/attr']);
  }*/

  addUpdateMfglClassCode = () => {
    this.router.navigate(['/dashboard/mfgl']);
    this._dToggle();
  }

  addUpdateGlCode = () => {
    this.router.navigate(['/dashboard/gl']);
    this._dToggle();
  }

  addUpdateProj = () => {
    this.router.navigate(['/dashboard/proj']);
    this._dToggle();
  }

  userAdministration = () => {
    this.router.navigate(['/dashboard/searchuser']);
    this._dToggle();
  }

  uploadAssetCode = () => {
    this.router.navigate(['/dashboard/uploadassetcodes']);
    this._dToggle();
  }

  uploadNewAssetCode = () => {
    this.router.navigate(['/dashboard/uploadnewassetcodes']);
    this._dToggle();
  }

  uploadMappedCode = () => {
    this.router.navigate(['/dashboard/uploadmappedcodes']);
    this._dToggle();
  }

  showHelp = () => {
    this.router.navigate(['/dashboard/help']);
    this._dToggle();
  }

  tableStats = () => {
    //this.router.navigate(['/dashboard/tabstats']);
    this.generateReport();
    this._dToggle();
  }

  generateReport() {
    this.appService.downloadReport()
                      .subscribe(
                       (res: any)  => {
                         let file = new Blob([res], { type: 'application/pdf' });
                         var fileURL = URL.createObjectURL(file);
                         window.open(fileURL);
                         
                         //this.showPdfInDialog(this.domSenitizer.bypassSecurityTrustResourceUrl(fileURL));
                        },
                       (error:any) =>  {this.errorMessage = <any>error});
  }

  private _dToggle() {
    if (this.breakpointObserver.isMatched(Breakpoints.Handset)) {
      this.drawer.toggle();
    }
    //this.isHandset$.subscribe(a => {if (a) this.drawer.toggle()});
  }

  private setRoles() {
    const authState = this.authService.getAuthState();console.log(authState)
    const roles = authState.user.roles;
    this.isAdmin = roles.includes('Administrator');
    this.isAssetIssuer = roles.includes('AssetIssuer');
    this.isSystemUser = roles.includes('SystemUser');
    this.isHOD = roles.includes('HOD');
  }

  ngOnInit() {
    this.appEventService.changeEmitted$
              .subscribe(
                change => {
                  if (change == 'drawer toggle') {
                    this.drawer.toggle();
                  }  
                }
              );

    this.setRoles();
  }

}
