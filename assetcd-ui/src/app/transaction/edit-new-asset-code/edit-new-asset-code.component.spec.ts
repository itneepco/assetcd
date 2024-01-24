import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNewAssetCodeComponent } from './edit-new-asset-code.component';

describe('EditNewAssetCodeComponent', () => {
  let component: EditNewAssetCodeComponent;
  let fixture: ComponentFixture<EditNewAssetCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditNewAssetCodeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditNewAssetCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
