import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzvestajComponent } from './izvestaj.component';

describe('IzvestajComponent', () => {
  let component: IzvestajComponent;
  let fixture: ComponentFixture<IzvestajComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IzvestajComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IzvestajComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
