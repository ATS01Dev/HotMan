import { TestBed, inject } from '@angular/core/testing';

import { BilanService } from './bilan.service';

describe('BilanService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BilanService]
    });
  });

  it('should be created', inject([BilanService], (service: BilanService) => {
    expect(service).toBeTruthy();
  }));
});
