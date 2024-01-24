import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, Inject, OnInit } from '@angular/core';
import { tick } from '@angular/core/testing';
import { FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, delay, map, shareReplay, startWith } from 'rxjs/operators';
import { ConfirmMessageDialogComponent } from 'src/app/dialog/confirm-message-dialog/confirm-message-dialog.component';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';
import { TransactionService } from '../transaction.service';

@Component({
  selector: 'app-asset-code',
  templateUrl: './asset-code.component.html',
  styleUrls: ['./asset-code.component.scss']
})
export class AssetCodeComponent implements OnInit {

  errorMessage: any;

  //mappingCodeControl: FormControl = new FormControl();
  mappingCodes: any[];
  //filteredMappingCodes: Observable<any[]>;
  mappingCode: any;
  searchText: string = '';
  searchCount: number = 0;
  loading = false;

  glMfglClassCode: any;
  mfglCodes = [];

  codeControl: FormControl = new FormControl();
  filteredCodes: Observable<any[]>;
  codes: any[] = [];
  code: any;

  glCodeControl: FormControl = new FormControl();
  filteredGlCodes: Observable<any[]>;
  glCodes: any[] = [];
  glCode: any;

  assetCodeMas: any;

  projects: any[];
  project: any = 'All';

  runNo: number = 1;
  //preDescr: string = '';
  //postDescr: string = '';
  shortDescr: string = '';
  descr: string = '';
  noOfCodeChanges = 0;
  codeChanged = false;
  masDescs: any[] = [];
  masDesc: any = null;

  
  rejCodes = ['R', 'S'];
  rejCode: any;
  rejRem = "";


  mappingNewCode = {};


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
    this.loading = true;
    this.transactionService.fetchMappingCodes(projCode, assetCode)
                      .subscribe(
                       mappingCodes  => {this.mappingCodes = mappingCodes;this.loading = false; this.setMappingCode(null); console.log(mappingCodes)},
                       error =>  {this.errorMessage = <any>error; this.loading = false; this.setMappingCode(null); this.mappingCodes = []});
  }

  fetchGlCodes() {
    this.transactionService.fetchGlCodes()
    .subscribe(
      glCodes => {this.glCodes = glCodes; this.initGlCodeFilter(); console.log(glCodes)},
      error => {console.log(error);this.glCodes = [];}
    );
  }

  fetchMfglCodes() {
    this.transactionService.fetchMfglCodes()
    .subscribe(
      mfglCodes => {this.mfglCodes = mfglCodes;  this.initCodeFilter(); console.log(mfglCodes)},
      error => {console.log(error);this.mfglCodes = [];this.codes = [];}
    );
  }

  fetchCount() {
    if (this.searchText && this.searchText.length >=3) {

    }
  }

  fetchMC() {
    if (this.searchText && this.searchText.length >=3) {
      if (this.project === 'All') {
        this.fetchMappingCodes('All', this.searchText);
      } else {
        this.fetchMappingCodes(this.project.projCode, this.searchText);
      }
    } 
  }

  /*fetchGlMfglClassCode(mfglCode: number) {
    this.transactionService.fetchGlMfglClassCodeByMfglCode(mfglCode)
                      .subscribe(
                        success => {
                          if (this.glMfglClassCode?.mfglCode !== success.mfglCode) {
                            this.onCodeChange(null);
                          }
                          this.glMfglClassCode = success; 
                          this.fetchCodes(success.classCode);
                          console.log(success)
                        },
                        error =>  {
                          this.errorMessage = <any>error; 
                          this.glMfglClassCode = null;
                          this.onCodeChange(null);
                          this.codes = [];
                        }
                      );
  }

  // fetch sub asset class codes t0506
  fetchCodes(parentCode) {
    this.transactionService.fetchCodes('t0506', parentCode)
                      .subscribe(
                       codes  => {this.codes = codes; this.initCodeFilter(); console.log(codes)},
                       error =>  {this.errorMessage = <any>error; this.codes = []});
  }*/

  fetchAssetCodeMaster(code: string) {
    this.transactionService.fetchAssetCodeMaster(code) 
                      .subscribe(
                        success  => {
                          this.assetCodeMas = success; 
                          this.runNo = this.assetCodeMas.newRunno + 1; 
                          console.log(success)
                        },
                        error =>  {
                          this.errorMessage = <any>error; 
                          this.assetCodeMas = null;
                          this.runNo = 1;
                        });
  }

  fetchMasDescs(code: string) {
    this.transactionService.fetchAssetCodeMasDescs(code)
                      .subscribe(
                        success => this.masDescs = success,
                        error => this.masDescs = []
                      );
  }

  onMasDescChange(masDesc) {console.log(masDesc)
    if (masDesc) {
      //this.postDescr = masDesc.postDesc;
      //this.preDescr = masDesc.preDesc;
      this.descr = masDesc.newDesc;
      //this.calcDesc();
      this.shortDescr = masDesc.shortDesc;
    } else {
      //this.postDescr = '';
      //this.preDescr = '';
      this.descr = '';
      this.shortDescr = '';
    }
    this.fieldChanged();
  }


  onProjectChange(project) {
    this.mappingCode = null;
    this.mappingCodes = [];
    //this.mappingCodeControl.setValue('');
    //this.resetFields();
  }

  setMappingCode(mappingCode) {console.log(mappingCode)
    this.mappingCode = mappingCode;
    if (mappingCode) {
      //this.fetchGlMfglClassCode(mappingCode.mfglCode);
      let mfgl = this.mfglCodes.find(x => x.mfglCode == mappingCode.mfglCode);
      let gl = this.glCodes.find(x => x.glCode == mfgl.glCode);
      mfgl['glDesc'] = gl.descr;
      this.glMfglClassCode = JSON.parse(JSON.stringify(mfgl));
      this.glCode = gl;
      this.code = mfgl;
      this.codes = this.mfglCodes.filter(x => x.glCode == gl.glCode);
      this.glCodeControl.setValue(gl);
      this.codeControl.setValue(mfgl);
      this.onCodeChange(mfgl);
    } else {
      this.glMfglClassCode = null;
      setTimeout(() => this.mappingCode = null);
    }
  }

  onGlCodeChange(glCode) {
    this.glCode = glCode;
    this.codes = this.mfglCodes.filter(x => x.glCode == glCode.glCode);
    //console.log(this.codes)
    this.initCodeFilter(); 
    this.onCodeChange(null);
  }


  onCodeChangePre() {//console.log("pre")
    this.codeChanged = true;
  }

  onCodeChange(code) {console.log(code);

    this.code = code; // required for auto complete
    //this.preDescr = '';
    //this.postDescr = '';
    if (code) {
      const c = code.glCode.slice(-4) + code.subClass;//console.log(c)
      this.fetchAssetCodeMaster(c);
      this.fetchMasDescs(c);
      //this.descr = code.descr;
      //this.calcDesc();
      this.descr = this.mappingCode.assetDesc;
      this.shortDescr = this.mappingCode.assetDesc;
    } else {
      this.runNo = 1;
      this.shortDescr = '';
      this.descr = '';
      this.codeControl.setValue('');
      this.masDescs = [];
      this.masDesc = null;

    }
    
  }


  openRejectDialog() {
    let dialogRef = this.dialog.open(RejectMappingCodeDialog, {
      width: '250px',
      data: { rejCodes: this.rejCodes }
    });

    dialogRef.afterClosed().subscribe(result => {console.log(result);
      console.log('The dialog was closed');
      if (result.action == 'ok') {
        this.rejCode = result.rejCode;
        this.rejRem = result.rejRem;
        this.reject();
      }
    });
  }

  reject() {
    let mappingRejectedCode = this.constructMappingRejectedCode(this.mappingCode);

    this.transactionService.rejectMappingCode(mappingRejectedCode, this.mappingCode.id)
                      .subscribe(
                       success  => {
                         console.log(success);
                         if (success.error) {
                          this.msgMappingCodeAlreadyProcessed(this.mappingCode);
                         } else {
                          this.openSnackBar(this.mappingCode.assetCode + " - rejected", "");
                         }
                        },
                       error =>  {this.errorMessage = <any>error; console.log(error)});
  }

  preSave() {
    if (this.noOfCodeChanges == 0 && !this.codeChanged) {
      this.msgConfirmSave();
    } else {
      this.save();
    }
  }

  save() {

    let mappingNewCode = this.constructMappingNewCode(this.mappingCode);

    this.transactionService.saveMappingNewCode(mappingNewCode, this.mappingCode.id)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         if (success.error) {
                           this.msgMappingCodeAlreadyProcessed(this.mappingCode);
                         } else {
                          this.openSnackBar(success.newAssetCode + " - saved", "");
                          this.runNo = +(success.newAssetCode.slice(-6)) + 1;
                          this.fetchMasDescs(this.code.code);
                         }
                        },
                       error =>  {this.errorMessage = <any>error; console.log(error)});

  }


  /*next() {
    let select = "";
    let projCode = "";
    if (this.project == 'All') {
      projCode = this.mappingCode.projCode;
      select = 'All';
    } else {
      projCode = this.project.projCode;
      select = projCode;
    }
    this.noOfCodeChanges = 0;
    this.codeChanged = false;
    this.transactionService.fetchNextMappingCode(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'after');
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
    this.noOfCodeChanges = 0;
    this.codeChanged = false;
    this.transactionService.fetchPreviousMappingCode(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
                      .subscribe(
                       success  => {
                         console.log(success); 
                         this.mappingCodeControl.setValue(success); 
                         this.setMappingCode(success);
                        },
                       error =>  {
                         this.errorMessage = <any>error; 
                         console.log(error);
                         this.msgMappingCodeNotFound(this.mappingCode.assetCode, 'before');
                        });
  }*/


  setNext() {
    const i = this.mappingCodes.findIndex(x => x.id === this.mappingCode.id);//console.log(i)
    this.mappingCodes.splice(i, 1);
    if (i < this.mappingCodes.length) {
      this.mappingCode = this.mappingCodes[i];
    } else {
      this.mappingCode = null;
    }
    this.setMappingCode(this.mappingCode);
    this.noOfCodeChanges = 0;
    this.codeChanged = false;
  }


  prependZero(runNo: number): string {
    let rn = '';
    //const i = (runNo+'').length;
    for (let i = (runNo+'').length; i < 6 ;i++) {
      rn = '0' + rn;
    }
    return rn + runNo;
  }


  /*displayFn(mappingCode: any): string {
    //return firm ? firm.name + ', ' + firm.address : firm;
    return mappingCode ? mappingCode.assetCode + " :: (" + mappingCode.projCode + ") :: " + mappingCode.assetDesc : mappingCode;
  }*/



  displayFn1(code: any): string {//console.log(code); console.log(this.scc.tablel)
    return code ? code.subClass + " :: " + code.descr : code;
  }

  filter(val: any): any[] { console.log(val);//console.log(this.codes)
    let v = val.subClass ? val.subClass : val;
    return this.codes.filter(x => {
      let subClass = x.subClass
      let desc = x.descr;
      let glCode = x.glCode;
      return subClass.startsWith(v.toLowerCase()) || desc.toLowerCase().includes(v.toLowerCase());
    });
  }

  initCodeFilter() {

    this.filteredCodes = this.codeControl.valueChanges.pipe(
      startWith(null),
      map(val => val ? this.filter(val) : this.codes)
    );

  }

  displayFn2(code: any): string {//console.log(code); console.log(this.scc.tablel)
    return code ? code.glCode + " :: " + code.descr : code;
  }

  filterGlCodes(val: any): any[] { console.log(val)
    let v = val.glCode ? val.glCode : val;
    return this.glCodes.filter(x => {
      let glCode = x.glCode;
      let desc = x.descr;
      return glCode.startsWith(v.toLowerCase()) || desc.toLowerCase().includes(v.toLowerCase());
    });
  }

  initGlCodeFilter() {

    this.filteredGlCodes = this.glCodeControl.valueChanges.pipe(
      startWith(null),
      map(val => val ? this.filterGlCodes(val) : this.glCodes)
    );

  }

  fieldChanged() {
    this.noOfCodeChanges++;
  }

  updateShortDesc() {
    this.shortDescr = this.descr;
  }

  calcDesc() {
    /*if (this.code.descr.toLowerCase().trim() === 'misc' || this.code.descr.toLowerCase().trim() === 'miscellaneous') {
      this.descr = this.preDescr.trim() + ' ' + this.postDescr.trim();
    } else {
      this.descr = this.preDescr.trim() + ' ' + this.code.descr.trim() + ' ' + this.postDescr.trim();
    }*/
    //this.shortDescr = this.preDescr.trim() + ' ' + this.descr.trim() + ' ' + this.postDescr.trim();
    //this.shortDescr = this.descr;
  }

  constructMappingNewCode(mappingCode) {

    let mappingNewCode = {
      projCode: mappingCode.projCode,
      mfglCode: mappingCode.mfglCode,
      assetCode: mappingCode.assetCode,
      assetDesc: mappingCode.assetDesc,
      newMfglCode: this.code.mfglCode,
      newAssetCode: this.glCode.glCode.slice(-4) + this.code.subClass,
      newAssetDesc: this.descr, //(this.preDescr.trim() + ' ' + this.descr.trim() + ' ' + this.postDescr.trim()).trim(),
      newAssetShortDesc: this.shortDescr,
      rem1: mappingCode.rem1,
      slno: mappingCode.slno
    }
    return mappingNewCode;
  }

  constructMappingRejectedCode(mappingCode) {

    let mappingRejectedCode = {
      projCode: mappingCode.projCode,
      mfglCode: mappingCode.mfglCode,
      assetCode: mappingCode.assetCode,
      assetDesc: mappingCode.assetDesc,
      rem1: mappingCode.rem1,
      slno: mappingCode.slno,
      rejRem: this.rejRem,
      rejCode: this.rejCode
    }
   return mappingRejectedCode;
  }

  openSnackBar(message: string, action: string) {
    let snackBarRef = this.snackBar.open(message, action, {
      duration: 3000,
    });

    snackBarRef.afterDismissed().subscribe(() => {
      // reset the page (commented on request)
      //this.resetFields();
      //this.noOfCodeChanges = 0;
      //this.next();
      this.setNext();
    });


    snackBarRef.onAction().subscribe(() => {
      console.log('The snack-bar action was triggered!');
    });

  }

  resetFields() {
    this.mappingCodes = [];
    //this.mappingCodeControl.setValue("");
    this.mappingCode = null;
    this.glMfglClassCode = null;
    this.code = null;
    this.codeControl.setValue('');
    this.codes = [];
    
    this.mappingNewCode = {};

    this.runNo = 1;
    //this.preDescr = '';
    //this.postDescr = '';
    this.descr = '';
    this.shortDescr = '';
    this.noOfCodeChanges = 0;
  
    this.rejCode = null;
    this.rejRem = "";

  }

  msgMappingCodeAlreadyProcessed(mappingCode) {
    let dialogRef = this.dialog.open(MessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Message!", messages: ['The mapping code ' + mappingCode.projCode + ' :: ' + mappingCode.assetCode + ' is already processed.'] }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.setNext();
    });
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
      data: { title: "Confirm!", message: "Do you want to save without making any change?" }
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
    this.fetchGlCodes();
    this.fetchMfglCodes();
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

  }

}




@Component({
  selector: 'reject-mapping-code-dialog',
  templateUrl: 'reject-mapping-code-dialog.html',
})
export class RejectMappingCodeDialog implements OnInit {

  rejCode: any;
  rejRem: any;

  constructor(
    public dialogRef: MatDialogRef<RejectMappingCodeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  /*onNoClick(): void {
    this.dialogRef.close();
  }*/

  ok() {
    this.dialogRef.close({ action: 'ok', rejCode: this.rejCode, rejRem: this.rejRem });
  }

  cancel() {
    this.dialogRef.close({ action: 'cancel' });
  }

  ngOnInit() {
    this.dialogRef.disableClose = true;//disable default close operation
    /*this.dialogRef.backdropClick().subscribe(result => {
      this.dialogRef.close({ action: 'cancel' }});
    });*/
  }

}

