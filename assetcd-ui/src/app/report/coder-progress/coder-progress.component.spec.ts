import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoderProgressComponent } from './coder-progress.component';

describe('CoderProgressComponent', () => {
  let component: CoderProgressComponent;
  let fixture: ComponentFixture<CoderProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoderProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoderProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
