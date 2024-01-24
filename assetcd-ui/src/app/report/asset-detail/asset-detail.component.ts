import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';
import { ReportViewDialogComponent } from '../report-view-dialog/report-view-dialog.component';
import { Project, ReportService } from '../report.service';

@Component({
  selector: 'app-asset-detail',
  templateUrl: './asset-detail.component.html',
  styleUrls: ['./asset-detail.component.scss']
})
export class AssetDetailComponent implements OnInit, OnDestroy  {

  private sub: any;
  p: any;
  heading: string = "";

  projects: Project[] = [];
  errorMessage: any;
  url: any;
  codeStart: string = "";
   
  displayedColumns = ['select', 'projCode', 'projDesc'];
  dataSource = new MatTableDataSource<Project>(this.projects);
  selection = new SelectionModel<Project>(true, []);

  
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  /*checkboxLabel(row?: Project): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
  }*/


  constructor(
    private reportService: ReportService,
    private domSenitizer: DomSanitizer,
    private route: ActivatedRoute,
    public dialog: MatDialog
  ) { }

  loadProjects() {
    this.reportService.fetchProjects()
                      .subscribe(
                       projects  => {
                         this.projects = projects;
                         console.log(this.projects);
                         this.dataSource = new MatTableDataSource<Project>(this.projects);
                         this.selection = new SelectionModel<Project>(true, []);
                        },
                       error =>  {this.errorMessage = <any>error; this.projects = null});
  }


  exportCsv() {
    let projCodes = [];
    this.dataSource.data.forEach(row => this.selection.isSelected(row) ? projCodes.push(row.projCode) : {} );
console.log(projCodes); console.log(this.codeStart)
    this.reportService.exportMNC(projCodes, this.codeStart)
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



  generateReport1(sd) {
    //this.url = environment.apiUrl + 'report/matcode?projs=1000,2000';
    let urlstr = "";
    if (this.p == 'ovn') {
      urlstr = sd ? '/report/assetcodesd' : '/report/assetcode';
    } else if (this.p == 'nvo') {
      urlstr = '/report/assetcodenvo';
    } else if (this.p == 'sor') {
      urlstr = '/report/assetcodesor';
    } else if (this.p == 'gbu') {
      urlstr = '/report/assetcodegbu';
    }
    let projCodes = [];
    this.dataSource.data.forEach(row => this.selection.isSelected(row) ? projCodes.push(row.projCode) : {} );
    console.log(projCodes);
    if (projCodes.length > 0) {
      this.reportService.downloadReport(projCodes, this.codeStart, urlstr)
                      .subscribe(
                       res  => {
                         let file = new Blob([res], { type: 'application/pdf' });
                         var fileURL = URL.createObjectURL(file);
                         window.open(fileURL);
                         
                         //this.showPdfInDialog(this.domSenitizer.bypassSecurityTrustResourceUrl(fileURL));
                        },
                       error =>  {this.errorMessage = <any>error});
    } else {
      let dialogRef = this.dialog.open(MessageDialogComponent, {
      width: '300px',
      height: '250px',
      data: { title: "Error!", messages: ['Please select at least one Project.'] }
    });

    dialogRef.afterClosed().subscribe(result => {
      //console.log(result);
    });
    }
  }

  showPdfInDialog(fileURL:any) {
    
    let dialogRef = this.dialog.open(ReportViewDialogComponent, {
      width: '80%',
      //height: '80%',
      data: { pdf: fileURL }
    });

    dialogRef.afterClosed().subscribe(result => {
      //console.log(result);
    });

  }


  ngOnInit(): void {

    this.sub = this.route.params.subscribe(params => {
      //this.id = +params['id']; // (+) converts string 'id' to a number
      this.p = params['p'];

      if (this.p == 'ovn') {
        this.heading = "Old vs New";
      } else if (this.p == 'nvo') {
        this.heading = "New vs Old";
      } else if (this.p == 'sor') {
        this.heading = "Old vs New sorted on RefId";
      }

   });

   this.loadProjects();

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }


}
