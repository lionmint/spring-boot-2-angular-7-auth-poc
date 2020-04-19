import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {CustomAuthService} from '../../services/custom-auth.service';
import {User} from '../../models/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  validMessage='';

  constructor(private customAuthService: CustomAuthService) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  submitLogin() {
    if(this.loginForm.valid)
    {
      this.validMessage='Login Send!';
      this.customAuthService.login(this.loginForm.value);
      this.loginForm.reset();
    }else{
      this.validMessage= 'Please fill out all fields.'
    }
  }
}
