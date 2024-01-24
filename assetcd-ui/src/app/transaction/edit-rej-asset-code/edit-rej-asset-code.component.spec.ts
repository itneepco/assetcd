import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRejAssetCodeComponent } from './edit-rej-asset-code.component';

describe('EditRejAssetCodeComponent', () => {
  let component: EditRejAssetCodeComponent;
  let fixture: ComponentFixture<EditRejAssetCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditRejAssetCodeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditRejAssetCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
