import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, of } from 'rxjs';
import { debounceTime, delay, distinctUntilChanged, filter, map, shareReplay, startWith, switchMap } from 'rxjs/operators';
import { ConfirmMessageDialogComponent } from 'src/app/dialog/confirm-message-dialog/confirm-message-dialog.component';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';
import { TransactionService } from '../transaction.service';

@Component({
  selector: 'app-edit-new-asset-code',
  templateUrl: './edit-new-asset-code.component.html',
  styleUrls: ['./edit-new-asset-code.component.scss']
})
export class EditNewAssetCodeComponent implements OnInit {

  errorMessage: any;

  //mappingCodeControl: FormControl = new FormControl();
  mappingCodes: any[];
  //filteredMappingCodes: Observable<any[]>;
  mappingCode: any;
  searchText: string = '';
  searchCount: number = 0;
  loading = false;
  prvAssetCode: string = '';
  prvRunNo: number = 1;

  glMfglClassCode: any;
  mfglCodes = [];

  codeControl: FormControl = new FormControl();
  filteredCodes: Observable<any[]>;
  codes: any[];
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
  descr: string = ''
  shortDescr: string = '';
  noOfCodeChanges = 0;
  codeChanged = false;
  masDescs: any[] = [];
  masDesc: any = null;

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
    this.transactionService.searchMappingNewCodes(projCode, assetCode)
                      .subscribe(
                       mappingCodes  => {this.mappingCodes = mappingCodes; this.loading=false;  this.setMappingCode(null); console.log(mappingCodes)},
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
                          //if (this.glMfglClassCode?.mfglCode !== success.mfglCode) {
                          //  this.onCodeChange(null);
                          //}
                          this.glMfglClassCode = success; 
                          this.fetchCodes(success.classCode);
                          console.log(success);
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
                       codes  => {//console.log(this.prvAssetCode); console.log(this.prvRunNo)
                         this.codes = codes; 
                         this.code = this.codes.find(x => x.code == this.prvAssetCode);
                         this.codeControl.setValue(this.code);//console.log(this.code)
                         this.runNo = this.prvRunNo;
                         this.shortDescr = this.mappingCode.newAssetShortDesc;
                         //const d = this.mappingCode.newAssetDesc.split(this.code.descr);
                         //this.preDescr = d[0].trim();
                         //this.postDescr = d[1].trim();
                         this.descr = this.mappingCode.newAssetDesc;
                         this.initCodeFilter(); 
                         this.fetchMasDescs(this.code.code);
                         console.log(codes)},
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
                        success => {this.masDescs = success;console.log(success)},
                        error => {this.masDescs = [];console.log(error)}
                      );
  }

  onMasDescChange(masDesc) {console.log(masDesc)
    if (masDesc) {
      this.descr = masDesc.newDesc;
      this.shortDescr = masDesc.shortDesc;
    } else {
      this.descr = '';
      this.shortDescr = '';
    }
    this.fieldChanged();
  }



  onProjectChange(project) {
    this.mappingCode = null;
    this.mappingCodes = [];
    //this.mappingCodeControl.setValue('');
    this.resetFields();
  }

  setMappingCode(mappingCode) {console.log(mappingCode)
    this.mappingCode = mappingCode;
    if (mappingCode) {
      //this.fetchGlMfglClassCode(mappingCode.mfglCode);
      let mfgl = this.mfglCodes.find(x => x.mfglCode == mappingCode.mfglCode);
      let gl = this.glCodes.find(x => x.glCode == mfgl.glCode);
      mfgl['glDesc'] = gl.descr;
      this.glMfglClassCode = JSON.parse(JSON.stringify(mfgl));
      let newMfgl = this.mfglCodes.find(x => x.mfglCode == mappingCode.newMfglCode);console.log(newMfgl)
      let newGl = this.glCodes.find(x => x.glCode == newMfgl.glCode);
      this.glCode = newGl;
      this.code = newMfgl;
      this.codes = this.mfglCodes.filter(x => x.glCode == newGl.glCode);
      this.glCodeControl.setValue(newGl);
      this.codeControl.setValue(newMfgl);
      this.prvAssetCode = mappingCode.newAssetCode.slice(0, 6);//console.log(this.prvAssetCode)
      this.prvRunNo = +(mappingCode.newAssetCode.slice(-6));
      this.onCodeChange(newMfgl);
    } else {
      this.glMfglClassCode = null;
      this.prvAssetCode = '';
      this.prvRunNo = 1;
      setTimeout(() => this.mappingCode = null);
    }
  }

  onGlCodeChange(glCode) {
    this.glCode = glCode;
    this.codes = this.mfglCodes.filter(x => x.glCode == glCode.glCode);
    //console.log(this.codes)
    this.initCodeFilter(); 
    this.codeChanged = true;
    this.onCodeChange(null);
  }

  onCodeChangePre() {//console.log("pre")
    this.codeChanged = true;
  }

  onCodeChange(code) {console.log(code);

    this.code = code; // required for auto complete
    if (code) {console.log(1)
      const ac = this.glCode.glCode.slice(-4) + code.subClass;//console.log(ac);console.log(this.prvAssetCode);console.log(this.prvRunNo)
      if (ac === this.prvAssetCode) {
        this.runNo = this.prvRunNo;
      } else {console.log(2)
        this.fetchAssetCodeMaster(ac);
      }
      this.fetchMasDescs(ac);
      //this.shortDescr = code.descr;
      //this.descr = code.descr;
      this.shortDescr = this.mappingCode.newAssetShortDesc;
      this.descr = this.mappingCode.newAssetDesc;
    } else {
      this.runNo = 1;
      this.shortDescr = '';
      this.descr = '';
      this.codeControl.setValue('');
      this.masDescs = [];
      this.masDesc = null;

    }
    //this.preDescr = '';
    //this.postDescr = '';
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

    this.transactionService.updateMappingNewCode(mappingNewCode, this.mappingCode.id)// this.preDescr, this.postDescr)
                      .subscribe(
                       success  => {console.log(success), this.openSnackBar(success.newAssetCode + " - updated", "")},
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
    this.transactionService.fetchNextMappingNewCode(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
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
    this.transactionService.fetchPreviousMappingNewCode(projCode, this.mappingCode.assetCode, this.mappingCode.mfglCode, select)
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

  constructMappingNewCode(mappingCode) {
    let mappingNewCode = {
      projCode: mappingCode.projCode,
      mfglCode: mappingCode.mfglCode,
      assetCode: mappingCode.assetCode,
      assetDesc: mappingCode.assetDesc,
      newMfglCode: this.code.mfglCode,
      newAssetCode: this.glCode.glCode.slice(-4) + this.code.subClass + this.prependZero(this.runNo),
      newAssetDesc: this.descr, //(this.preDescr + ' ' + this.code.descr + ' ' + this.postDescr).trim(),
      newAssetShortDesc: this.shortDescr,
      rem1: mappingCode.rem1,
      slno: mappingCode.slno
    }
    return mappingNewCode;
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
    this.shortDescr = '';
    this.descr = '';
    this.noOfCodeChanges = 0;
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
    //this.shortDescr = (this.preDescr + ' ' + this.code.descr + ' ' + this.postDescr).trim();
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

    /*this.mappingCodeControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      //.startWith(null)
      filter(value => value && value.length >= 3),
      switchMap(value => this.project === 'All' ? this.transactionService.fetchMappingNewCodes('All', value) : this.transactionService.fetchMappingNewCodes(this.project.projCode, value))
      /-*switchMap(value => {
        if (value && value.length >= 3) {
          if (this.project === 'All') {
            return this.transactionService.fetchMappingCodes('All', value);
          } else {
            return this.transactionService.fetchMappingCodes(this.project.projCode, value);
          }
        } else {
          return of([]);
        }
      })*-/
    ).subscribe(
      mappingCodes  => {
        this.mappingCodes = mappingCodes;console.log(mappingCodes);
        //this.filteredMappingCodes = mappingCodes;
      },
      error =>  {
        this.errorMessage = <any>error; this.mappingCodes = [];
        //this.filteredMappingCodes = Observable([])
      }
    );*/
  }

}
