import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistarGradjanaComponent } from './registar-gradjana.component';

describe('RegistarGradjanaComponent', () => {
  let component: RegistarGradjanaComponent;
  let fixture: ComponentFixture<RegistarGradjanaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistarGradjanaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistarGradjanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
