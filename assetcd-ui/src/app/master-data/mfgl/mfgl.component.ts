import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, startWith, tap } from 'rxjs/operators';
import { MasterDataService } from '../master-data.service';

@Component({
  selector: 'app-mfgl',
  templateUrl: './mfgl.component.html',
  styleUrls: ['./mfgl.component.scss']
})
export class MfglComponent implements OnInit {

  errorMessage: any;

  glCodeControl: FormControl = new FormControl();
  filteredGlCodes: Observable<any[]> = of([]);
  glCodes: any[] = [];
  selectedGlCode: any = null;
  
  glMfglClassCodeControl: FormControl = new FormControl();
  filteredGlMfglClassCodes: Observable<any[]> = of([]);
  glMfglClassCodes: any[] = [];
  selectedGlMfglClassCode: any = null;

  
  isNew = false;
  isEdit = false;
  isUpload = false;

  newCode = '';
  newMfglCode = '';
  newDescr = '';
  newFullCode = "";
  descr = '';
  mfglCode = '';
  uploadInProgress = false;
  file: File;
  uploadMessage: string = '';
  uploadError: string = '';
  uploadId: string = "";

  regx = /^[a-zA-Z]{2}[0-9]{2}$/;
  regxnum = /^[0-9]{6}$/;



  constructor(
    private masterDataService: MasterDataService
  ) { }

  fetchGlCodes(code: string) {
    this.masterDataService.fetchGlCodes(code)
                      .subscribe(
                       codes  => {
                         this.glCodes = codes; 
                         this.glCodes.sort((a,b) => (a.glCode > b.glCode) ? 1 : ((b.glCode > a.glCode) ? -1 : 0));
                         this.initGlCodeFilter(); 
                         console.log(codes)
                        },
                       error =>  {this.errorMessage = <any>error; this.glCodes = []});
  }

  fetchGlMfglClassCodes(code: number) {
    this.masterDataService.fetchGlMfglClassCodesByGlCode(code)
                      .subscribe(
                       codes  => {
                         this.glMfglClassCodes = codes; 
                         this.glMfglClassCodes.sort((a,b) => (a.subClass > b.subClass) ? 1 : ((b.subClass > a.subClass) ? -1 : 0));
                         this.initGlMfglClassCodeFilter(); 
                         console.log(codes)
                        },
                       error =>  {this.errorMessage = <any>error; this.glMfglClassCodes = []});
  }

  onGlCodeChange(code: any) {
    console.log(code)
    this.selectedGlCode = code;
    this.selectedGlMfglClassCode = null;
    this.glMfglClassCodeControl.setValue('');
    if (code) {
      this.fetchGlMfglClassCodes(code.glCode);
    } else {
      
    }
  }

  onGlMfglClassCodeChange(code: any) {
    console.log(code)
    this.selectedGlMfglClassCode = code;
    if (code) {
      this.descr = code.descr;
      this.mfglCode = code.mfglCode;
    } else {
      this.descr = '';
      this.mfglCode = '';
    }
  }


  onNewCode() {
    this.isNew = true;
    this.isUpload = false;
    this.isEdit = false;
    //this.onCodeChange(null);
  }



  onSaveNew() {
    let code = {
      glCode: this.selectedGlCode.glCode,
      subClass: this.newCode,
      mfglCode: this.newMfglCode,
      descr: this.newDescr,
      status: 1,
      userCode: 'abc'
    }
    this.errorMessage = null;

    this.masterDataService.addGlMfglClassCode(code)
            .subscribe(
              success => {
                console.log(success); 
                if (success.error) {
                  this.errorMessage = success.error;
                } else {
                  this.errorMessage = null;
                  this.reset();
                  this.fetchGlMfglClassCodes(this.selectedGlCode.glCode);
                  setTimeout(()=>{ this.selectNewCode(success); }, 2000);
                }
              },
              error => {this.errorMessage = <any>error;}
            );

  }

  onSelectExisitingCode() {
    this.isNew = false;
    this.isUpload = false;
    this.isEdit = false;
    this.reset();
  }

  onEditCode() {
    this.isEdit = true;
    this.isNew = false;
    this.isUpload = false;
    //this.descr = this.selectedSubAssetClassCode.descr;
  }

  onCancel() {
    this.onGlMfglClassCodeChange(this.selectedGlMfglClassCode);
    this.isEdit = false;
    this.isNew = false;
    this.isUpload = false;
  }

  onUpdate() {

    let table = 't0506';
    let id = this.selectedGlMfglClassCode.id;
    let changes = {
      mfglCode: this.mfglCode + '',
      descr: this.descr,
      //userCode: 'abc';
      updatedAt: new Date()
    };

    this.masterDataService.updateGlMfglClassCode(id, changes)
                        .subscribe(
                          success  => {
                            console.log(success); 
                            this.reset();
                            this.isEdit = false;
                            this.fetchGlMfglClassCodes(this.selectedGlCode.glCode);
                            setTimeout(()=>{ this.selectNewCode(success); }, 2000);
                            },
                            error =>  {this.errorMessage = <any>error;}
                         );
                        /*.subscribe(
                          success => { 
                            this.code = success;
                            this.isEdit = false; 
                            console.log(success);
                          },
                          error => { console.log(error)}
                        );*/

  }


  onExportCodes() { 
    let ecc = {
      parentCode : this.selectedGlCode.glCode
    };
    this.masterDataService.exportGlMfglClassCodes(ecc)
                    .subscribe(
                       success  => {
                         this.downloadFile(success, 'Codes.csv');
                       },
                       error =>  {
                         this.errorMessage = <any>error;console.log(error);
                      });
  }

  downloadFile(data: any, fileName: string) {
    //let parsedResponse = data.text();
    //let blob = new Blob([parsedResponse], { type: 'text/csv' });
    let blob = new Blob([data], { type: 'text/csv' });
    let url = window.URL.createObjectURL(blob);
    //window.open(url);

    if(navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, fileName);
    } else {
        let a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();        
        document.body.removeChild(a);
    }
    window.URL.revokeObjectURL(url);
  }

  onUploadCodes() {
    this.isUpload = true;
    this.isNew = false;
    this.isEdit = false;
  }

  onBackToSelectExisitingCode() {
    this.isUpload = false;
    this.isNew = false;
    this.isEdit = false;
    //this.reset();
  }



  fileChange(event: any) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
        let file: File = fileList[0];
        this.file = file;
    }
  }

  uploadCodes() {
    this.uploadInProgress = true;
    this.uploadMessage = '';
    this.uploadId = "";
    this.uploadError = '';
    let ucc = {
      //table : 't0506', 
      parentCode : this.selectedGlCode.glCode, 
      //fullCodeLength : 6
    };
    this.masterDataService.uploadGlMfglClassCodes(this.file, ucc)
                    .subscribe(
                       success  => {
                         this.uploadInProgress = false;
                         this.uploadError = success.error;
                         this.uploadId = success.uploadId;
                         this.uploadMessage = success.message;
                         this.file = null;
                       },
                       error =>  {
                         this.uploadInProgress = false;
                         this.file = null;
                         this.errorMessage = <any>error;console.log(error);
                      });
  } 

  downloadErrors() {

    this.masterDataService.downloadGMCCErrors(this.uploadId)
                    .subscribe(
                       success  => {
                         this.downloadFile(success, 'Error.csv');
                       },
                       error =>  {
                         this.errorMessage = <any>error;console.log(error);
                      });
  }






  reset() {
    //this.selectedSubAssetClassCode = null;
    this.isNew = false;
    this.newCode = '';
    this.newMfglCode = '';
    this.newDescr = '';
    this.newFullCode = "";
    this.descr = "";
    this.mfglCode = "";
    this.errorMessage = "";
    
  }

  selectNewCode(code: any) {//console.log('select new code - ' + JSON.stringify(code)); console.log(this.codes)
    this.selectedGlMfglClassCode = this.glMfglClassCodes.find(x => x.classCode == code.classCode);
    this.glMfglClassCodeControl.setValue(this.selectedGlMfglClassCode); // for autocomplete
    //console.log(this.code);
    this.onGlMfglClassCodeChange(this.selectedGlMfglClassCode);
    this.isNew = false;
  }




  initGlMfglClassCodeFilter() {
    this.filteredGlMfglClassCodes = this.glMfglClassCodeControl.valueChanges.pipe(
      startWith(null),
      tap(value => typeof value === 'string' ? value : this.onGlMfglClassCodeChange(null)),
      map(val => val ? this._filterGlMfglClassCodes(val) : this.glMfglClassCodes)
    );
  }

  displayFn(code: any): string {
    return code ? code.subClass + " :: " + code.descr : code;
  }

  private _filterGlMfglClassCodes(value: any): any[] {console.log(value)

    let v = value.subClass ? value.subClass : value;
    return this.glMfglClassCodes.filter(x => {
      return x.subClass.toLowerCase().startsWith(v.toLowerCase()) || x.descr.toLowerCase().includes(v.toLowerCase());
    });
  }

  initGlCodeFilter() {
    this.filteredGlCodes = this.glCodeControl.valueChanges.pipe(
      startWith(null),
      tap(value => typeof value === 'string' ? value : this.onGlCodeChange(null)),
      map(val => val ? this._filterGlCodes(val) : this.glCodes)
    );
  }

  displayFn1(code: any): string {
    return code ? code.glCode + " :: " + code.descr : code;
  }

  private _filterGlCodes(value: any): any[] {console.log(value)

    let v = value.glCode ? value.glCode+'' : value;
    return this.glCodes.filter(x => {
      return (x.glCode+'').startsWith(v.toLowerCase()) || x.descr.toLowerCase().includes(v.toLowerCase());
    });
  }

  ngOnInit(): void {
    this.fetchGlCodes('');
    this.initGlCodeFilter();
  }


}
