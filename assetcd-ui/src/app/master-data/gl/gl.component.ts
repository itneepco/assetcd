import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, startWith, tap } from 'rxjs/operators';
import { MasterDataService } from '../master-data.service';

@Component({
  selector: 'app-gl',
  templateUrl: './gl.component.html',
  styleUrls: ['./gl.component.scss']
})
export class GlComponent implements OnInit {

  errorMessage: any;

  glCodeControl: FormControl = new FormControl();
  filteredGlCodes: Observable<any[]> = of([]);

  glCodes: any[] = [];
  selectedGlCode: any = null;
  
    
  isNew = false;
  isEdit = false;
  isUpload = false;

  newCode = '';
  newDescr = '';
  newFullCode = "";
  descr = '';
  glCode = '';
  uploadInProgress = false;
  file: File;
  uploadMessage: string = '';
  uploadError: string = '';
  uploadId: string = "";



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

  onGlCodeChange(code: any) {
    console.log(code)
    this.selectedGlCode = code;
    
    if (code) {
      this.descr = code.descr;
      this.glCode = code.glCode;
    } else {
      this.descr = '';
      this.glCode = '';
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
      glCode: this.newCode,
      descr: this.newDescr,
      status: 1,
      userCode: 'abc'
    }
    this.errorMessage = null;

    this.masterDataService.addGlCode(code)
            .subscribe(
              success => {
                console.log(success); 
                if (success.error) {
                  this.errorMessage = success.error;
                } else {
                  this.errorMessage = null;
                  this.reset();
                  this.fetchGlCodes('');
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
    this.onGlCodeChange(this.selectedGlCode);
    this.isEdit = false;
    this.isNew = false;
    this.isUpload = false;
  }

  onUpdate() {

    //let table = 't0506';
    let id = this.selectedGlCode.id;
    let changes = {
      glCode: this.glCode,
      descr: this.descr,
      //userCode: 'abc';
      updatedAt: new Date()
    };

    this.masterDataService.updateGlCode(id, changes)
                        .subscribe(
                          success  => {
                            console.log(success); 
                            this.reset();
                            this.isEdit = false;
                            this.fetchGlCodes('');
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
      //parentCode : this.selectedGlCode.glCode
    };
    this.masterDataService.exportGlCodes(ecc)
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
      parentCode : '', 
      //fullCodeLength : 6
    };
    this.masterDataService.uploadGlCodes(this.file, ucc)
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

    this.masterDataService.downloadGCErrors(this.uploadId)
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
    this.selectedGlCode = this.glCodes.find(x => x.glCode == code.glCode);
    this.glCodeControl.setValue(this.selectedGlCode); // for autocomplete
    //console.log(this.code);
    this.onGlCodeChange(this.selectedGlCode);
    this.isNew = false;
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
