import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RememberPasswordSuccessComponent } from './remember-password-success.component';

describe('RememberPasswordSuccessComponent', () => {
  let component: RememberPasswordSuccessComponent;
  let fixture: ComponentFixture<RememberPasswordSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RememberPasswordSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RememberPasswordSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
