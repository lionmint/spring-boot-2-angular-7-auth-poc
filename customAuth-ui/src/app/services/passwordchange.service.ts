import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PasswordchangeService {

  constructor(private http:HttpClient) { }

  baseUrl = environment.baseUrl;

  changePassword(pass, email: string) {
      let user = <User> { password: pass.password, email: email};
      let body = JSON.stringify(user);
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
      return this.http.post(this.baseUrl + '/changepassword', body, httpOptions);
  }

  requestPasswordChange(email: string) {
      return this.http.get(this.baseUrl + '/changepassword/request/' + email, {});
  }
}
