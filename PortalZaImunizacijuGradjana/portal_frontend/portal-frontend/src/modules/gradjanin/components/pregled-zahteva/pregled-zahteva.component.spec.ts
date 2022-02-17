import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledZahtevaComponent } from './pregled-zahteva.component';

describe('PregledZahtevaComponent', () => {
  let component: PregledZahtevaComponent;
  let fixture: ComponentFixture<PregledZahtevaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PregledZahtevaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PregledZahtevaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
