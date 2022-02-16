import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RazlogZahtevaComponent } from './razlog-zahteva.component';

describe('RazlogZahtevaComponent', () => {
  let component: RazlogZahtevaComponent;
  let fixture: ComponentFixture<RazlogZahtevaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RazlogZahtevaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RazlogZahtevaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
