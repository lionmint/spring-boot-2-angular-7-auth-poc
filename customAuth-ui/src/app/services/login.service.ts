import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/user.model';
import {AuthorizationEntity} from '../models/authorizationEntity.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  baseUrl = environment.baseUrl;

  logIn(user) {
      let token = localStorage.getItem('access_token');
      let body = JSON.stringify(user);
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
      return this.http.post<AuthorizationEntity>(this.baseUrl + '/login', body, httpOptions);
  }

  getUserData(email: string) {
    let token = localStorage.getItem('access_token');
    return this.http.get<User[]>(this.baseUrl + '/login/' + email,
        {headers: new HttpHeaders({'Authorization': 'Bearer ' + token})}
    );
  }
}
