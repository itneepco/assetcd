import { Component, OnInit } from '@angular/core';

import { Project, ReportService } from '../report.service';

@Component({
  selector: 'app-coder-progress',
  templateUrl: './coder-progress.component.html',
  styleUrls: ['./coder-progress.component.scss']
})
export class CoderProgressComponent implements OnInit {

  errorMessage: any;
  
  
  constructor(
    private reportService: ReportService
  ) { }

  

  generateReport1() {
      this.reportService.coderProgressReport()
                      .subscribe(
                       res  => {
                         let file = new Blob([res], { type: 'application/pdf' });
                         var fileURL = URL.createObjectURL(file);
                         window.open(fileURL);
                        },
                       error =>  {this.errorMessage = <any>error});
    
  }

  ngOnInit() {
    
  }


}
