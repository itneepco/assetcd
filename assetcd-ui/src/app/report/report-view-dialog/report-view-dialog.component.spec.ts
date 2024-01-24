import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportViewDialogComponent } from './report-view-dialog.component';

describe('ReportViewDialogComponent', () => {
  let component: ReportViewDialogComponent;
  let fixture: ComponentFixture<ReportViewDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportViewDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportViewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
