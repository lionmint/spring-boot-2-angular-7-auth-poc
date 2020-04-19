import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { SocialLoginService } from './social-login.service';
import {AuthorizationEntity} from '../models/authorizationEntity.model';

@Injectable({
  providedIn: 'root'
})
export class CustomAuthService {

  constructor(public router: Router, private loginService: LoginService, private socialLoginService: SocialLoginService) { }

  login(user): void {
    this.loginService.logIn(user).subscribe(
      data => {
        console.log("Response: " + JSON.stringify(data));
        this.setAuthroization(data);
      },
      err => {console.log(err)}
    );
  }

  socialLoginGoogle(idToken): void {
    this.socialLoginService.loginGoogle(idToken).subscribe(
      data => {
        console.log("Response: " + JSON.stringify(data));
        this.setAuthroization(data);
      },
      err => {console.log(err)}
    );
  }

  socialLoginFacebook(user): void {
    this.socialLoginService.loginFacebook(user).subscribe(
      data => {
        console.log("Response: " + JSON.stringify(data));
        this.setAuthroization(data);
      },
      err => {console.log(err)}
    );
  }

  setAuthroization(data)
  {
    console.log("TEST");
    let authResult: AuthorizationEntity;
    authResult = data;
    authResult.email = data.email;
    authResult.firstName = data.firstName;
    authResult.lastName = data.lastName;
    authResult.accessToken = data.accessToken;
    authResult.authorized = data.authorized;
    authResult.admin = data.admin;
    authResult.expiresIn = data.expiresIn;
    authResult.newUser = data.newUser;

    if(authResult.authorized)
    {
      let username = authResult.firstName + ' ' + authResult.lastName;
      const expiresAt = JSON.stringify((authResult.expiresIn) + new Date().getTime());
      localStorage.setItem('access_token', authResult.accessToken);
      localStorage.setItem('user', username);
      localStorage.setItem('expires_at', expiresAt);
      localStorage.setItem('isAdmin', JSON.stringify(authResult.admin));
      localStorage.setItem('email', authResult.email);

      // Redirect to USER.

      if(authResult.newUser)
      {
        this.router.navigate(['/signup/success']);
      }else
      {
        this.router.navigate(['/user']);
      }
    }
  }

  logout(): void {
    this.clearStorage();
    // remove apiKey

    // Go back to the home route
    this.router.navigate(['/']);
  }

  clearStorage(): void {
    // Remove tokens and expiry time from localStorage
    localStorage.removeItem('access_token');
    localStorage.removeItem('user');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('isAdmin');
    localStorage.removeItem('email');
  }

  public isAuthenticated(): boolean {
    // Check whether the current time is past the
    // access token's expiry time
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'));
    return new Date().getTime() < expiresAt;
  }
}
