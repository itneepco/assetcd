import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUpdateProjComponent } from './add-update-proj.component';

describe('AddUpdateProjComponent', () => {
  let component: AddUpdateProjComponent;
  let fixture: ComponentFixture<AddUpdateProjComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUpdateProjComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUpdateProjComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
