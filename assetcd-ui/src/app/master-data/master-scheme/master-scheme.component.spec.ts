import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterSchemeComponent } from './master-scheme.component';

describe('MasterSchemeComponent', () => {
  let component: MasterSchemeComponent;
  let fixture: ComponentFixture<MasterSchemeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterSchemeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterSchemeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
