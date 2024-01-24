import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadAssetCodesComponent } from './upload-asset-codes.component';

describe('UploadAssetCodesComponent', () => {
  let component: UploadAssetCodesComponent;
  let fixture: ComponentFixture<UploadAssetCodesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadAssetCodesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadAssetCodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
