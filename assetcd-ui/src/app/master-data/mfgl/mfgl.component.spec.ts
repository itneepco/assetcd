import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MfglComponent } from './mfgl.component';

describe('MfglComponent', () => {
  let component: MfglComponent;
  let fixture: ComponentFixture<MfglComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MfglComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MfglComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
