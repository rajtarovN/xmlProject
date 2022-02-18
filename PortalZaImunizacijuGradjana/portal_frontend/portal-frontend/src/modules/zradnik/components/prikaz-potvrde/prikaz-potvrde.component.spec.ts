import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikazPotvrdeComponent } from './prikaz-potvrde.component';

describe('PrikazPotvrdeComponent', () => {
  let component: PrikazPotvrdeComponent;
  let fixture: ComponentFixture<PrikazPotvrdeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrikazPotvrdeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrikazPotvrdeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
