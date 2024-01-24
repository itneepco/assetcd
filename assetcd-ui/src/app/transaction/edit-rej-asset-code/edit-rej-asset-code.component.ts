import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { debounceTime, delay, distinctUntilChanged, filter, map, shareReplay, switchMap } from 'rxjs/operators';
import { ConfirmMessageDialogComponent } from 'src/app/dialog/confirm-message-dialog/confirm-message-dialog.component';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';
import { TransactionService } from '../transaction.service';

@Component({
  selector: 'app-edit-rej-asset-code',
  templateUrl: './edit-rej-asset-code.component.html',
  styleUrls: ['./edit-rej-asset-code.component.scss']
})
export class EditRejAssetCodeComponent implements OnInit {

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
    this.transactionService.fetchMappingRejCodesR(projCode, assetCode)
                      .subscribe(
                       mappingCodes  => {this.mappingCodes = mappingCodes;console.log(mappingCodes)},
                       error =>  {this.errorMessage = <any>error; this.mappingCodes = []});
  }

  

  onProjectChange(project) {
    this.mappingCode = null;
    this.mappingCodeControl.setValue('');
    //this.resetFields();
  }

  setMappingCode(mappingCode) {
    this.mappingCode = mappingCode;
    /*if (mappingCode) {
      this.fetchGlMfglClassCode(mappingCode.mfglCode);
    } else {
      this.glMfglClassCode = null;
    }*/
  }


  displayFn(mappingCode: any): string {
    //return firm ? firm.name + ', ' + firm.address : firm;
    return mappingCode ? mappingCode.assetCode + " :: (" + mappingCode.projCode + ") :: " + mappingCode.assetDesc : mappingCode;
  }

  preSave() {
    this.msgConfirmSave();
  }

  save() {

    let mc = this.constructMappingCode(this.mappingCode);

    this.transactionService.updateMappingRejCode(mc, this.mappingCode.id)
                      .subscribe(
                       success  => {console.log(success); this.openSnackBar(this.mappingCode.assetCode + " - updated", "")},
                       error =>  {this.errorMessage = <any>error; console.log(error)});

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
    this.transactionService.fetchNextMappingRejCodeR(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                         //this.showSave = true;
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'after');
                         //this.showSave = false;
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
    this.transactionService.fetchPreviousMappingRejCodeR(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                         //this.showSave = true;
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'before');
                         //this.showSave = false;
                        });
  }

  constructMappingCode(mappingRejCode) {
    let mappingCode = JSON.parse(JSON.stringify(mappingRejCode));
    mappingCode.id = null;
    return mappingCode;
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

  resetFields() {
    this.mappingCodes = [];
    this.mappingCodeControl.setValue("");

    this.mappingCode = null;
    
  }

  msgMappingCodeNotFound(assetCode, str) {
    let dialogRef = this.dialog.open(MessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Message!", messages: ['No Mapping code ' + str + ' ' + assetCode] }
    });
  }

  msgConfirmSave() {
    let dialogRef = this.dialog.open(ConfirmMessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Confirm!", message: "The code will be re-sent for mapping after save and further update will not be possible. Do you want to continue with save?" }
    });

    dialogRef.afterClosed().subscribe(result => {console.log(result);
      console.log('The dialog was closed');
      if (result.action == 'ok') {
        this.save();
      }
    });
  }



  ngOnInit(): void {

    this.loadProjects();
    /*this.filteredMappingCodes = this.mappingCodeControl.valueChanges.pipe(
        debounceTime(500),
        //.startWith(null)
        map(name => {
          if (name && name.length >= 1) {
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
      switchMap(value => this.project === 'All' ? this.transactionService.fetchMappingRejCodesR('All', value) : this.transactionService.fetchMappingRejCodesR(this.project.projCode, value))
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
