import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InteresovanjeFormComponent } from './interesovanje-form.component';

describe('InteresovanjeFormComponent', () => {
  let component: InteresovanjeFormComponent;
  let fixture: ComponentFixture<InteresovanjeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InteresovanjeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InteresovanjeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
