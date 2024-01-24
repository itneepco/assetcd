import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChangePasswordComponent } from './admin/change-password/change-password.component';
import { NewUserComponent } from './admin/new-user/new-user.component';
import { SearchUserComponent } from './admin/search-user/search-user.component';
import { ViewUserComponent } from './admin/view-user/view-user.component';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddUpdateProjComponent } from './master-data/add-update-proj/add-update-proj.component';
import { GlComponent } from './master-data/gl/gl.component';
import { MasterSchemeComponent } from './master-data/master-scheme/master-scheme.component';
import { MfglComponent } from './master-data/mfgl/mfgl.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AssetDetailComponent } from './report/asset-detail/asset-detail.component';
import { CodeMappingProgressComponent } from './report/code-mapping-progress/code-mapping-progress.component';
import { CoderProgressComponent } from './report/coder-progress/coder-progress.component';
import { RejStatusComponent } from './report/rej-status/rej-status.component';
import { AuthGuardService } from './services/auth-guard.service';
import { HelpComponent } from './sv/help/help.component';
import { UploadAssetCodesComponent } from './sv/upload-asset-codes/upload-asset-codes.component';
import { UploadMappedCodesComponent } from './sv/upload-mapped-codes/upload-mapped-codes.component';
import { UploadNewAssetCodesComponent } from './sv/upload-new-asset-codes/upload-new-asset-codes.component';
import { AssetCodeComponent } from './transaction/asset-code/asset-code.component';
import { EditNewAssetCodeComponent } from './transaction/edit-new-asset-code/edit-new-asset-code.component';
import { EditRejAssetCodeComponent } from './transaction/edit-rej-asset-code/edit-rej-asset-code.component';
import { ResendRejAssetCodeComponent } from './transaction/resend-rej-asset-code/resend-rej-asset-code.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuardService],
    children: [
      // admin module
      { path: 'searchuser', component: SearchUserComponent },
      { path: 'newuser', component: NewUserComponent },
      { path: 'viewuser/:id', component: ViewUserComponent },
      { path: 'viewuser', component: ViewUserComponent },
      { path: 'changepassword', component: ChangePasswordComponent },
      // master data module 
      { path: 'mscheme', component: MasterSchemeComponent },
      { path: 'mfgl', component: MfglComponent },
      { path: 'gl', component: GlComponent },
      { path: 'proj', component: AddUpdateProjComponent },
      // trnsation module
      { path: 'assetcode', component: AssetCodeComponent },
      { path: 'editmappingcode', component: EditNewAssetCodeComponent },
      { path: 'editiirmappingcode', component: EditRejAssetCodeComponent },
      { path: 'resemdsnfrmappingcode', component: ResendRejAssetCodeComponent },
      // report module
      { path: 'assetdetail/:p', component: AssetDetailComponent },
      { path: 'rejstatus', component: RejStatusComponent },
      { path: 'codemappingprogress', component: CodeMappingProgressComponent },
      { path: 'coderprogress', component: CoderProgressComponent },
      // sv module
      { path: 'uploadassetcodes', component: UploadAssetCodesComponent },
      { path: 'uploadnewassetcodes', component: UploadNewAssetCodesComponent },
      { path: 'uploadmappedcodes', component: UploadMappedCodesComponent },
      { path: 'help', component: HelpComponent },
      // page not found
      //{ path: '**', component: PageNotFoundComponent }
      
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
