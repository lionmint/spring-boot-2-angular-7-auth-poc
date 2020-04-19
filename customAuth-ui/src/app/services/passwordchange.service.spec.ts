import { TestBed, inject } from '@angular/core/testing';

import { PasswordchangeService } from './passwordchange.service';

describe('PasswordchangeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PasswordchangeService]
    });
  });

  it('should be created', inject([PasswordchangeService], (service: PasswordchangeService) => {
    expect(service).toBeTruthy();
  }));
});
