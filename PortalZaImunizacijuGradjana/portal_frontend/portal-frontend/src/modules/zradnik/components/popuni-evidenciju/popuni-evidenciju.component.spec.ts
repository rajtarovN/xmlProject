import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopuniEvidencijuComponent } from './popuni-evidenciju.component';

describe('PopuniEvidencijuComponent', () => {
  let component: PopuniEvidencijuComponent;
  let fixture: ComponentFixture<PopuniEvidencijuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopuniEvidencijuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopuniEvidencijuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
