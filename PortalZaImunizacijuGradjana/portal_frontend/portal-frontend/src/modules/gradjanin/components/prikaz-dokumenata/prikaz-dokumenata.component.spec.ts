import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikazDokumenataComponent } from './prikaz-dokumenata.component';

describe('PrikazDokumenataComponent', () => {
  let component: PrikazDokumenataComponent;
  let fixture: ComponentFixture<PrikazDokumenataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrikazDokumenataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrikazDokumenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
