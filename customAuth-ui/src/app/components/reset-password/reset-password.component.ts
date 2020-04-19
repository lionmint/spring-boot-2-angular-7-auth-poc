import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {PasswordchangeService} from '../../services/passwordchange.service';
import { ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  changePasswordForm: FormGroup;
  validMessage='';
  email;

  constructor(private passwordService: PasswordchangeService, private route: ActivatedRoute, public router: Router) { }

  ngOnInit() {
    this.changePasswordForm = new FormGroup({
      password: new FormControl('', Validators.required),
      passwordConfirm: new FormControl('', Validators.required)
    });

    this.email = this.route.snapshot.paramMap.get("email");
    // Take email from URL
  }

  submitChangePassword(): void
  {
    if(this.changePasswordForm.valid)
    {
      if(this.changePasswordForm.value.password.localeCompare(this.changePasswordForm.value.passwordConfirm)==0)
      {
        this.passwordService.changePassword(this.changePasswordForm.value, this.email).subscribe();
        this.validMessage='Your request has been send.';
        this.changePasswordForm.reset();
        this.router.navigate(['/passwordconfirmation']);
      }else
      {
        this.validMessage='Please ensure that both entries are the same.';
      }
    }else{
      this.validMessage='Please fill out all entries.'
    }
  }
}
