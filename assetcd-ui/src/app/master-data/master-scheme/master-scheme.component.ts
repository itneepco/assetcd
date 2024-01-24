import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, startWith, tap } from 'rxjs/operators';
import { MasterDataService } from '../master-data.service';

@Component({
  selector: 'app-master-scheme',
  templateUrl: './master-scheme.component.html',
  styleUrls: ['./master-scheme.component.scss']
})
export class MasterSchemeComponent implements OnInit {

  errorMessage: any;

  glMfglClassCodeControl: FormControl = new FormControl();
  filteredGlMfglClassCodes: Observable<any[]> = of([]);

  glMfglClassCodes: any[] = [];
  selectedGlMfglClassCode: any = null;

  subAssetClassCodeControl: FormControl = new FormControl();
  filteredSubAssetClassCodes: Observable<any[]> = of([]);

  subAssetClassCodes: any[] = [];
  selectedSubAssetClassCode: any = null;

  isNew = false;
  isEdit = false;
  isUpload = false;

  newCode = '';
  newDescr = '';
  newFullCode = "";
  descr = '';
  uploadInProgress = false;
  file: File;
  uploadMessage: string = '';
  uploadError: string = '';
  uploadId: string = "";





  constructor(
    private masterDataService: MasterDataService
  ) { }

  fetchGlMfglClassCodes(code: string) {
    this.masterDataService.fetchGlMfglClassCodes(code)
                      .subscribe(
                       codes  => {
                         this.glMfglClassCodes = codes; 
                         this.glMfglClassCodes.sort((a,b) => (a.classCode > b.classCode) ? 1 : ((b.classCode > a.classCode) ? -1 : 0));
                         this.initGlMfglClassCodeFilter(); 
                         console.log(codes)
                        },
                       error =>  {this.errorMessage = <any>error; this.glMfglClassCodes = []});
  }

  fetchSubAssetClassCodes(code: string) {
    this.masterDataService.fetchSubAssetClassCodes(code)
                      .subscribe(
                       codes  => {
                         this.subAssetClassCodes = codes; 
                         this.subAssetClassCodes.sort((a,b) => (a.code > b.code) ? 1 : ((b.code > a.code) ? -1 : 0));
                         this.initSubAssetClassCodeFilter(); 
                         console.log(codes)
                        },
                       error =>  {this.errorMessage = <any>error; this.subAssetClassCodes = []});
  }

  onGlMfglClassCodeChange(code: any) {
    console.log(code)
    this.selectedGlMfglClassCode = code;
    this.selectedSubAssetClassCode = null;
    this.subAssetClassCodeControl.setValue('');
    if (code) {
      this.fetchSubAssetClassCodes(code.classCode);
    } else {
      
    }
  }

  onSubAssetClassCodeChange(code: any) {
    console.log(code)
    this.selectedSubAssetClassCode = code;
    if (code) {
      this.descr = code.descr;
    } else {
      this.descr = '';
    }
  }


  onNewCode() {
    this.isNew = true;
    this.isUpload = false;
    this.isEdit = false;
    //this.onCodeChange(null);
  }



  onSaveNew() {
    let table = 't0506';
    let code = {
      code: this.selectedGlMfglClassCode.classCode + this.newCode,
      descr: this.newDescr,
      status: 1,
      userCode: 'abc'
    }
    this.errorMessage = null;

    this.masterDataService.addCode(table, code)
            .subscribe(
              success => {
                console.log(success); 
                if (success.error) {
                  this.errorMessage = success.error;
                } else {
                  this.errorMessage = null;
                  this.reset();
                  this.fetchSubAssetClassCodes(this.selectedGlMfglClassCode.classCode);
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
    //this.reset();
  }

  onEditCode() {
    this.isEdit = true;
    this.isNew = false;
    this.isUpload = false;
    //this.descr = this.selectedSubAssetClassCode.descr;
  }

  onCancel() {
    this.onSubAssetClassCodeChange(this.selectedSubAssetClassCode);
    this.isEdit = false;
    this.isNew = false;
    this.isUpload = false;
    //console.log(this.selectedSubAssetClassCode)
    //console.log(this.subAssetClassCodeControl.value)
  }

  onUpdate() {

    let table = 't0506';
    let id = this.selectedSubAssetClassCode.id;
    let changes = {
      descr: this.descr,
      //userCode: 'abc';
      updatedAt: new Date()
    };

    this.masterDataService.updateCode(table, id, changes)
                        .subscribe(
                          success  => {
                            console.log(success); 
                            this.reset();
                            this.isEdit = false;
                            this.fetchSubAssetClassCodes(this.selectedGlMfglClassCode.classCode);
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
      table : 't0506', 
      parentCode : this.selectedGlMfglClassCode.classCode
    };
    this.masterDataService.exportCodes(ecc)
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
      table : 't0506', 
      parentCode : this.selectedGlMfglClassCode.classCode, 
      fullCodeLength : 6
    };
    this.masterDataService.uploadCodes(this.file, ucc)
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

    this.masterDataService.downloadErrors(this.uploadId)
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
    this.newDescr = '';
    this.newFullCode = "";
    this.descr = "";
    
  }

  selectNewCode(code: any) {//console.log('select new code - ' + JSON.stringify(code)); console.log(this.codes)
    this.selectedSubAssetClassCode = this.subAssetClassCodes.find(x => x.code == code.code);
    this.subAssetClassCodeControl.setValue(this.selectedSubAssetClassCode); // for autocomplete
    //console.log(this.code);
    this.onSubAssetClassCodeChange(this.selectedSubAssetClassCode);
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
    return code ? code.classCode + " :: " + code.descr : code;
  }

  private _filterGlMfglClassCodes(value: any): any[] {console.log(value)

    let v = value.classCode ? value.classCode : value;
    return this.glMfglClassCodes.filter(x => {
      return x.classCode.toLowerCase().startsWith(v.toLowerCase()) || x.descr.toLowerCase().includes(v.toLowerCase());
    });
  }

  initSubAssetClassCodeFilter() {
    this.filteredSubAssetClassCodes = this.subAssetClassCodeControl.valueChanges.pipe(
      startWith(null),
      tap(value => typeof value === 'string' ? value : this.onSubAssetClassCodeChange(null)),
      map(val => val ? this._filterSubAssetClassCodes(val) : this.subAssetClassCodes)
    );
  }

  displayFn1(code: any): string {
    return code ? code.code.slice(-2) + " :: " + code.descr : code;
  }

  private _filterSubAssetClassCodes(value: any): any[] {console.log(value)

    let v = value.code ? value.code : value;
    return this.subAssetClassCodes.filter(x => {
      const absCode = x.code.slice(-2);
      return absCode.toLowerCase().startsWith(v.toLowerCase()) || x.descr.toLowerCase().includes(v.toLowerCase());
    });
  }

  ngOnInit(): void {
    this.fetchGlMfglClassCodes('');
    this.initGlMfglClassCodeFilter();
  }

}
