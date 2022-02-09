import { TestBed } from '@angular/core/testing';

import { InteresovanjeService } from './interesovanje.service';

describe('InteresovanjeService', () => {
  let service: InteresovanjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InteresovanjeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
