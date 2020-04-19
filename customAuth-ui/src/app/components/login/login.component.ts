import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {CustomAuthService} from '../../services/custom-auth.service';
import {User} from '../../models/user.model';
import { AuthService, SocialUser, FacebookLoginProvider, GoogleLoginProvider } from "angularx-social-login";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  validMessage='';
  private user: SocialUser;
  private loggedIn: boolean;

  constructor(private customAuthService: CustomAuthService, private authService: AuthService) { }

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

  signInWithGoogle(): void {
    let isCalled = false;

    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);

    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);

      if(this.loggedIn && localStorage.getItem("user") == null && !isCalled)
      {
        isCalled = true;
        console.log("User is looged in is " + this.loggedIn);
        console.log("User: " + JSON.stringify(this.user.idToken));
        this.customAuthService.socialLoginGoogle(this.user.idToken);
      }
    });
  }

  signInWithFB(): void {
    let isCalled = false;
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);

    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);

      if(this.loggedIn && localStorage.getItem("user") == null && !isCalled )
      {
        isCalled = true;
        console.log("User is looged in is " + this.loggedIn);
        console.log("User: " + JSON.stringify(this.user.authToken));
        this.customAuthService.socialLoginFacebook(this.user.authToken);
      }
    });
  }
}
