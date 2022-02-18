import { TestBed } from '@angular/core/testing';

import { PodnosenjeZahtevaService } from './podnosenje-zahteva.service';

describe('PodnosenjeZahtevaService', () => {
  let service: PodnosenjeZahtevaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PodnosenjeZahtevaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
