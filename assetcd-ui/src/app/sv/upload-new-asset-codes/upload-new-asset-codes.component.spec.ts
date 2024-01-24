import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadNewAssetCodesComponent } from './upload-new-asset-codes.component';

describe('UploadNewAssetCodesComponent', () => {
  let component: UploadNewAssetCodesComponent;
  let fixture: ComponentFixture<UploadNewAssetCodesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadNewAssetCodesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadNewAssetCodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
