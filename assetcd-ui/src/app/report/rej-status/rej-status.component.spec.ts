import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RejStatusComponent } from './rej-status.component';

describe('RejStatusComponent', () => {
  let component: RejStatusComponent;
  let fixture: ComponentFixture<RejStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RejStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RejStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
