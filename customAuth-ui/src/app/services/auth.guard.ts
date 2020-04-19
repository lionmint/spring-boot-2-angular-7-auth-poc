import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import {CustomAuthService} from '../services/custom-auth.service';
import { AuthService } from 'angularx-social-login';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private customAuthService: CustomAuthService, private authService: AuthService){
    }

  canActivate(){
    if(this.customAuthService.isAuthenticated()){
      return true;
    }else
    {
      this.customAuthService.clearStorage();
      this.authService.signOut();
    }
  }
}
