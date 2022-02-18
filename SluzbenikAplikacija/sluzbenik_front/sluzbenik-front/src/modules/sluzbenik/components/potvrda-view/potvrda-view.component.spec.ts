import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PotvrdaViewComponent } from './potvrda-view.component';

describe('PotvrdaViewComponent', () => {
  let component: PotvrdaViewComponent;
  let fixture: ComponentFixture<PotvrdaViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PotvrdaViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PotvrdaViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
