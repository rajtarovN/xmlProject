import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SluzbenikHomepageComponent } from './sluzbenik-homepage.component';

describe('SluzbenikHomepageComponent', () => {
  let component: SluzbenikHomepageComponent;
  let fixture: ComponentFixture<SluzbenikHomepageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SluzbenikHomepageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SluzbenikHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
