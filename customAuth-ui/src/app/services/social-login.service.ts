import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorizationEntity} from '../models/authorizationEntity.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SocialLoginService {

  constructor(private http:HttpClient) { }

  baseUrl = environment.baseUrl;

  loginGoogle(idToken) {
      let token = localStorage.getItem('access_token');
      let body = JSON.stringify(idToken);
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
      return this.http.post<AuthorizationEntity>(this.baseUrl + '/social/google', body, httpOptions);
  }

  loginFacebook(access_token) {
      let token = localStorage.getItem('access_token');
      let body = JSON.stringify(access_token);
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
      return this.http.post<AuthorizationEntity>(this.baseUrl + '/social/facebook', body, httpOptions);
  }
}
