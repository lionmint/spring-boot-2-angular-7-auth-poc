import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {Observable, throwError } from 'rxjs';
import {CustomAuthService} from '../../services/custom-auth.service';
import {SignupService} from '../../services/signup.service';
import {User} from '../../models/user.model';
import {MediaChange, ObservableMedia} from '@angular/flex-layout';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;
  validMessage='';
  observableMedia;

  constructor(private customAuthService: CustomAuthService, private signupService: SignupService, public router: Router) { }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required)
    });
  }

  submitSignUp(): void
  {
    if(this.signupForm.valid)
    {
      this.validMessage='Your registration has been send!';
      this.signupService.signUp(this.signupForm.value).subscribe((success) => {
        if(success)
        {
          this.router.navigate(['/signup/success']);
        }else
        {
          this.signupForm.reset();
        }
      });
    }else{
      this.validMessage='Please fill out all entries.'
    }
  }
}
