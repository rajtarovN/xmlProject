import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaglasnostiGradjanaComponent } from './saglasnosti-gradjana.component';

describe('SaglasnostiGradjanaComponent', () => {
  let component: SaglasnostiGradjanaComponent;
  let fixture: ComponentFixture<SaglasnostiGradjanaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaglasnostiGradjanaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaglasnostiGradjanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
