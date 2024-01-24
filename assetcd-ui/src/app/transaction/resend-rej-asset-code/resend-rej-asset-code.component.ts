import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { debounceTime, delay, distinctUntilChanged, filter, map, shareReplay, switchMap } from 'rxjs/operators';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';
import { TransactionService } from '../transaction.service';

@Component({
  selector: 'app-resend-rej-asset-code',
  templateUrl: './resend-rej-asset-code.component.html',
  styleUrls: ['./resend-rej-asset-code.component.scss']
})
export class ResendRejAssetCodeComponent implements OnInit {

  errorMessage: any;

  mappingCodeControl: FormControl = new FormControl();
  mappingCodes: any[];
  filteredMappingCodes: Observable<any[]>;
  mappingCode: any;

  

  projects: any[];
  project: any = 'All';


  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private transactionService: TransactionService,
    private breakpointObserver: BreakpointObserver,
    public snackBar: MatSnackBar,
    public dialog: MatDialog
  ) { }


  loadProjects() {
    this.transactionService.fetchProjects()
                      .subscribe(
                       projects  => {
                         this.projects = projects;
                         console.log(this.projects);
                        },
                       error =>  {this.errorMessage = <any>error; this.projects = []});
  }

  fetchMappingCodes(projCode: string, assetCode: string) {
    this.transactionService.fetchMappingRejCodesS(projCode, assetCode)
                      .subscribe(
                       mappingCodes  => {this.mappingCodes = mappingCodes;console.log(mappingCodes)},
                       error =>  {this.errorMessage = <any>error; this.mappingCodes = []});
  }

  onProjectChange(project) {
    this.mappingCode = null;
    this.mappingCodeControl.setValue('');
    this.resetFields();
  }

  setMappingCode(mappingCode) {
    this.mappingCode = mappingCode;
    //this.showResend = mappingCode ? true : false;
  }


  displayFn(mappingCode: any): string {
    //return firm ? firm.name + ', ' + firm.address : firm;
    return mappingCode ? mappingCode.assetCode + " :: (" + mappingCode.projCode + ") :: " + mappingCode.assetDesc : mappingCode;
  }

  next() {
    let select = "";
    let projCode = "";
    if (this.project == 'All') {
      projCode = this.mappingCode.projCode;
      select = 'All';
    } else {
      projCode = this.project.projCode;
      select = projCode;
    }
    this.transactionService.fetchNextMappingRejCodeS(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                         //this.showResend = true;
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'after');
                         //this.showResend = false;
                        });
  }

  previous() {
    let select = "";
    let projCode = "";
    if (this.project == 'All') {
      projCode = this.mappingCode.projCode;
      select = 'All';
    } else {
      projCode = this.project.projCode;
      select = projCode;
    }
    this.transactionService.fetchPreviousMappingRejCodeS(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                         //this.showResend = true;
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'before');
                         //this.showResend = false;
                        });
  }

  resend() {

    this.transactionService.resendMappingRejCode(this.mappingCode)
                      .subscribe(
                       success  => {console.log(success), this.openSnackBar(this.mappingCode.assetCode + " - resent", "")},
                       error =>  {this.errorMessage = <any>error; console.log(error)});

  }

  resetFields() {
    this.mappingCodes = [];
    this.mappingCodeControl.setValue("");

    this.mappingCode = null;
  }

  openSnackBar(message: string, action: string) {
    let snackBarRef = this.snackBar.open(message, action, {
      duration: 3000,
    });

    snackBarRef.afterDismissed().subscribe(() => {
      // reset the page (commented on request)
      //this.resetFields();
      this.next();
    });


    snackBarRef.onAction().subscribe(() => {
      console.log('The snack-bar action was triggered!');
    });

  }



  msgMappingCodeNotFound(assetCode, str) {
    let dialogRef = this.dialog.open(MessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Message!", messages: ['No Mapping code ' + str + ' ' + assetCode] }
    });
  }



  ngOnInit(): void {

    this.loadProjects();
    
    /*this.filteredMappingCodes = this.mappingCodeControl.valueChanges.pipe(
        debounceTime(500),
        //.startWith(null)
        map(name => {
          if (name && name.length >= 3) {
            if (this.project === 'All') {
              this.fetchMappingCodes('All', name);
            } else {
              this.fetchMappingCodes(this.project.projCode, name);
            }
          } else {
            this.mappingCodes = [];
          }
          return this.mappingCodes; 
        }),
        delay(500),
        map(list => { return this.mappingCodes; }));*/

    this.mappingCodeControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      //.startWith(null)
      filter(value => value && value.length >= 3),
      switchMap(value => this.project === 'All' ? this.transactionService.fetchMappingRejCodesS('All', value) : this.transactionService.fetchMappingRejCodesS(this.project.projCode, value))
    ).subscribe(
      mappingCodes  => {
        this.mappingCodes = mappingCodes;console.log(mappingCodes);
        //this.filteredMappingCodes = mappingCodes;
      },
      error =>  {
        this.errorMessage = <any>error; this.mappingCodes = [];
        //this.filteredMappingCodes = Observable([])
      }
    );

  }

}
