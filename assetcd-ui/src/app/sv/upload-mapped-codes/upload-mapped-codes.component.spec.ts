import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadMappedCodesComponent } from './upload-mapped-codes.component';

describe('UploadMappedCodesComponent', () => {
  let component: UploadMappedCodesComponent;
  let fixture: ComponentFixture<UploadMappedCodesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadMappedCodesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadMappedCodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
