import { Component, OnInit } from '@angular/core';
import { MasterDataService } from '../master-data.service';

@Component({
  selector: 'app-add-update-proj',
  templateUrl: './add-update-proj.component.html',
  styleUrls: ['./add-update-proj.component.scss']
})
export class AddUpdateProjComponent implements OnInit {

  errorMessage: any;
  mode = 'list';
  error: any;

  projs = [];
  proj: any;

  projCode = "";
  projDesc = "";
  title = "";
  projLoc = "";
  status = "1"; 

  constructor(
    private masterDataService: MasterDataService
  ) { }

  fetchProjs() {
    this.masterDataService.fetchProjs()
                      .subscribe(
                       projs  => {
                         this.projs = projs;
                         console.log(projs);
                        },
                       error =>  {this.errorMessage = <any>error; this.projs = []});
  }

  newProj() {
    this.reset();
    this.mode = 'new';
  }

  editProj(proj: any) {
    this.proj = JSON.parse(JSON.stringify(proj));
    this.mode = 'edit';
    this.error = "";
  }

  cancel() {
    this.mode = 'list';
  }

  saveNew() {
    let proj = this.projs.find((x:any) => x.projCode == this.projCode);
    if (proj) {
      this.error = "Proj Code already exists."
    } else {
      this.save();
    }
  }

  save() {
    let proj = {projCode: this.projCode, projDesc: this.projDesc, title: this.title, projLoc: this.projLoc, status: this.status};
    this.masterDataService.saveProj(proj)
                    .subscribe(
                      success => {
                        this.mode = 'list';
                        this.reset();
                        this.fetchProjs();
                      },
                      error => {this.errorMessage = <any>error;}
                    );
  }

  update() {
    let proj = this.projs.find((x:any) =>x.id != this.proj.id && x.projCode == this.proj.projCode);
    if (proj) {
      this.error = "Proj Code already exists."
    } else {
      this.masterDataService.updateProj(this.proj)
                    .subscribe(
                      success => {
                        this.mode = 'list';
                        this.reset();
                        this.fetchProjs();
                      },
                      error => {this.errorMessage = <any>error;}
                    );
    }
  }

  reset() {
    this.projCode = "";
    this.projDesc = "";
    this.title = "";
    this.projLoc = "";
    this.status = "1";
    this.proj = null;
    this.error = null;
  }


  ngOnInit() {
    this.fetchProjs();
    this.mode = 'list';
  }


}
