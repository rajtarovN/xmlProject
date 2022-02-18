import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopuniVakcinaPodatkeComponent } from './popuni-vakcina-podatke.component';

describe('PopuniVakcinaPodatkeComponent', () => {
  let component: PopuniVakcinaPodatkeComponent;
  let fixture: ComponentFixture<PopuniVakcinaPodatkeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopuniVakcinaPodatkeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopuniVakcinaPodatkeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
