import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeMappingProgressComponent } from './code-mapping-progress.component';

describe('CodeMappingProgressComponent', () => {
  let component: CodeMappingProgressComponent;
  let fixture: ComponentFixture<CodeMappingProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeMappingProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeMappingProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
