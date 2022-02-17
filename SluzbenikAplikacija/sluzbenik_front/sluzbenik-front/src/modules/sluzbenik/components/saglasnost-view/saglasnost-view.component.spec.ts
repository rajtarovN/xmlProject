import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaglasnostViewComponent } from './saglasnost-view.component';

describe('SaglasnostViewComponent', () => {
  let component: SaglasnostViewComponent;
  let fixture: ComponentFixture<SaglasnostViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaglasnostViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaglasnostViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
