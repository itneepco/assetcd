import { Component, OnInit } from '@angular/core';
import { SvService } from '../sv.service';

@Component({
  selector: 'app-help',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.scss']
})
export class HelpComponent implements OnInit {

  mfgls = [];
  page = 'legacy matfin mapping'; // 'subclass mfgl mapping'

  constructor(
    private svService: SvService
  ) { }

  fetchMfgls() {
    this.svService.fetchMfgls()
    .subscribe(
      mfgls => {this.mfgls = mfgls; console.log(mfgls)},
      error => {console.log(error);this.mfgls = []}
    );
  }

  setPage(value) {
    this.page = value;
    if (value === 'subclass mfgl mapping') {
      this.mfgls = this.mfgls.sort((a, b) =>  (a.mfglCode > b.mfglCode) ? 1 : ((b.mfglCode > a.mfglCode) ? -1 : 0));
    } else if (value === 'legacy matfin mapping') {
      this.mfgls = this.mfgls.sort((a, b) =>  {
        if (a.glCode > b.glCode) {
          return 1;
        } else if (b.glCode > a.glCode) {
          return -1;
        } else {
          //return 0;
          if (a.subClass > b.subClass) {
            return 1;
          } else if (b.subclass > a.subclass) {
            return -1;
          } else {
            //return 0;
            if (a.mfglCode > b.mfglCode) {
              return 1;
            } else if (b.mfglCode > a.mfglCode) {
              return -1;
            } else {
              return 0;
            }
          }
        }
      });
    }
  }

  ngOnInit() {
    this.fetchMfgls();
  }

}
