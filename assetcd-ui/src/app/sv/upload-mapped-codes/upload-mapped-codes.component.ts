import { Component, OnInit } from '@angular/core';

import { SvService } from '../sv.service';

@Component({
  selector: 'app-upload-mapped-codes',
  templateUrl: './upload-mapped-codes.component.html',
  styleUrls: ['./upload-mapped-codes.component.scss']
})
export class UploadMappedCodesComponent implements OnInit {

  file: File;
  message: string;
  error: string;
  uploadId: "";
  inProgress = false;

  errorMessage: any;

  constructor(
    private svService: SvService
  ) { }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
        let file: File = fileList[0];
        this.file = file;
    }
  }

  uploadMappedCodes() {
    this.inProgress = true;
    this.message = null;
    this.uploadId = "";
    this.error = null;
    this.svService.uploadMappedCodes(this.file)
                    .subscribe(
                       success  => {
                         this.inProgress = false;
                         this.error = success.error;
                         this.uploadId = success.uploadId;
                         this.message = success.message;
                         this.file = null;
                       },
                       error =>  {
                         this.inProgress = false;
                         this.file = null;
                         this.errorMessage = <any>error;console.log(error);
                      });
  } 

  downloadErrors() {

    this.svService.downloadMappedCodeErrors(this.uploadId)
                    .subscribe(
                       success  => {
                         this.downloadFile(success);
                       },
                       error =>  {
                         this.errorMessage = <any>error;console.log(error);
                      });
  }

  downloadFile(data: any) {
    //let parsedResponse = data.text();
    //let blob = new Blob([parsedResponse], { type: 'text/csv' });
    let blob = new Blob([data], { type: 'text/csv' });
    let url = window.URL.createObjectURL(blob);
    //window.open(url);

    if(navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, 'Error.csv');
    } else {
        let a = document.createElement('a');
        a.href = url;
        a.download = 'Error.csv';
        document.body.appendChild(a);
        a.click();        
        document.body.removeChild(a);
    }
    window.URL.revokeObjectURL(url);
  }


  ngOnInit() {
  }

}
