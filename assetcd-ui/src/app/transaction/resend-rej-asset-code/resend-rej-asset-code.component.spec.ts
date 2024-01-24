import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResendRejAssetCodeComponent } from './resend-rej-asset-code.component';

describe('ResendRejAssetCodeComponent', () => {
  let component: ResendRejAssetCodeComponent;
  let fixture: ComponentFixture<ResendRejAssetCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResendRejAssetCodeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResendRejAssetCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
