import { TestBed } from '@angular/core/testing';

import { SertifikatService } from './sertifikat.service';

describe('SertifikatService', () => {
  let service: SertifikatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SertifikatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
