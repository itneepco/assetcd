import { Component, OnInit } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

import { Project, ReportService } from '../report.service';
import { MatDialog } from '@angular/material/dialog';
import { MessageDialogComponent } from 'src/app/dialog/message-dialog/message-dialog.component';

@Component({
  selector: 'app-code-mapping-progress',
  templateUrl: './code-mapping-progress.component.html',
  styleUrls: ['./code-mapping-progress.component.scss']
})
export class CodeMappingProgressComponent implements OnInit {

  projects: Project[] = [];
  errorMessage: any;
  
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

  constructor(
    private reportService: ReportService,
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

  generateReport1() {
    let projCodes = [];
    this.dataSource.data.forEach(row => this.selection.isSelected(row) ? projCodes.push(row.projCode) : {} );
    console.log(projCodes);
    if (projCodes.length > 0) {
      this.reportService.codeMappingProgressReport(projCodes)
                      .subscribe(
                       res  => {
                         let file = new Blob([res], { type: 'application/pdf' });
                         var fileURL = URL.createObjectURL(file);
                         window.open(fileURL);
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

  ngOnInit() {
    this.loadProjects();
  }

}
