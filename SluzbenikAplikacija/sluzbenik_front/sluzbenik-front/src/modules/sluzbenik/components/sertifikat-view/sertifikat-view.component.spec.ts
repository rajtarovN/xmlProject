import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SertifikatViewComponent } from './sertifikat-view.component';

describe('SertifikatViewComponent', () => {
  let component: SertifikatViewComponent;
  let fixture: ComponentFixture<SertifikatViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SertifikatViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SertifikatViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
