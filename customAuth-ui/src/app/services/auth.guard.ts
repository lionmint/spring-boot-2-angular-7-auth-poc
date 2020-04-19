import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import {CustomAuthService} from '../services/custom-auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private customAuthService: CustomAuthService){
    }

  canActivate(){
    if(this.customAuthService.isAuthenticated()){
      return true;
    }else
    {
      this.customAuthService.clearStorage();
    }
  }
}
