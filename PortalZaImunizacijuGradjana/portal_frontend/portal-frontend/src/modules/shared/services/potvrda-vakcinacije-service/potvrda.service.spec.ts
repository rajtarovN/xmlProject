import { TestBed } from '@angular/core/testing';

import { PotvrdaService } from './potvrda.service';

describe('PotvrdaService', () => {
  let service: PotvrdaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PotvrdaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
