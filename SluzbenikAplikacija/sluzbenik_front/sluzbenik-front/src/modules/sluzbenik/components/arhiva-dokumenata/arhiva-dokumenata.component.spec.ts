import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArhivaDokumenataComponent } from './arhiva-dokumenata.component';

describe('ArhivaDokumenataComponent', () => {
  let component: ArhivaDokumenataComponent;
  let fixture: ComponentFixture<ArhivaDokumenataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArhivaDokumenataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArhivaDokumenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
