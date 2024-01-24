import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatExpansionModule } from '@angular/material/expansion';
import { AuthModule } from './auth/auth.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { AdminModule } from './admin/admin.module';
import { MasterDataModule } from './master-data/master-data.module';
import { AppInterceptor } from './services/app.interceptor';
import { MatDialogModule } from '@angular/material/dialog';
import { MessageDialogComponent } from './dialog/message-dialog/message-dialog.component';
import { ConfirmMessageDialogComponent } from './dialog/confirm-message-dialog/confirm-message-dialog.component';
import { TransactionModule } from './transaction/transaction.module';
import { ReportModule } from './report/report.module';
import { SvModule } from './sv/sv.module';
import { AppInitService } from './services/app-init.service';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    PageNotFoundComponent,
    MessageDialogComponent,
    ConfirmMessageDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),

    AppRoutingModule,
    AuthModule,
    AdminModule,
    MasterDataModule,
    TransactionModule,
    ReportModule,
    SvModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatExpansionModule,
    MatDialogModule
  ],
  entryComponents: [
    MessageDialogComponent,
    ConfirmMessageDialogComponent
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: (appInit: AppInitService) => () => appInit.loadConfig(),
      deps: [AppInitService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
