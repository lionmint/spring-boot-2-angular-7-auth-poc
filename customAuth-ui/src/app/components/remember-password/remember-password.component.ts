import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {PasswordchangeService} from '../../services/passwordchange.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-remember-password',
  templateUrl: './remember-password.component.html',
  styleUrls: ['./remember-password.component.css']
})
export class RememberPasswordComponent implements OnInit {


  resetPasswordForm: FormGroup;
  validMessage='';

  constructor(private passwordService: PasswordchangeService, public router: Router) { }

  ngOnInit() {
    this.resetPasswordForm = new FormGroup({
      email: new FormControl('', Validators.required)
    });
  }

  submitChangePasswordRequest(): void
  {
    if(this.resetPasswordForm.valid)
    {
      this.passwordService.requestPasswordChange(this.resetPasswordForm.value.email).subscribe();
      this.validMessage='Your request has been send. Please authorize the change via the email you will get.';
      this.resetPasswordForm.reset();
      this.router.navigate(['/auth/success']);
    }else{
      this.validMessage='Please fill out all entries.'
    }
  }
}
