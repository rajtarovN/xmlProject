import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaglasnostFormComponent } from './saglasnost-form.component';

describe('SaglasnostFormComponent', () => {
  let component: SaglasnostFormComponent;
  let fixture: ComponentFixture<SaglasnostFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaglasnostFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaglasnostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
