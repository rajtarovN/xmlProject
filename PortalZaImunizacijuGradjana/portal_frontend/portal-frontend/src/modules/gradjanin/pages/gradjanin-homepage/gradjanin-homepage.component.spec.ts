import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradjaninHomepageComponent } from './gradjanin-homepage.component';

describe('GradjaninHomepageComponent', () => {
  let component: GradjaninHomepageComponent;
  let fixture: ComponentFixture<GradjaninHomepageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GradjaninHomepageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GradjaninHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
