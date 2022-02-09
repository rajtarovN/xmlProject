import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZradnikHomepageComponent } from './zradnik-homepage.component';

describe('ZradnikHomepageComponent', () => {
  let component: ZradnikHomepageComponent;
  let fixture: ComponentFixture<ZradnikHomepageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZradnikHomepageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZradnikHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
