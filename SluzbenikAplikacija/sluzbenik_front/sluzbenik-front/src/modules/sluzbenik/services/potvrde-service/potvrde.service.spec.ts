import { TestBed } from '@angular/core/testing';

import { PotvrdeService } from './potvrde.service';

describe('PotvrdeService', () => {
  let service: PotvrdeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PotvrdeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
