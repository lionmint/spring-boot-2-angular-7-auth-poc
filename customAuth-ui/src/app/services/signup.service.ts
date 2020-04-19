import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorizationEntity} from '../models/authorizationEntity.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http:HttpClient) { }

  baseUrl = environment.baseUrl;

  userIsAvailable(email: string) {
    return this.http.get<boolean>(this.baseUrl + '/signup/' + email,
        {}
    );
  }

  signUp(user) {
      let body = JSON.stringify(user);
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
      return this.http.post(this.baseUrl + '/signup', body, httpOptions);
  }

  authSignUp(id) {
    return this.http.get<boolean>(this.baseUrl + '/signup/authorize/' + id,
        {}
    );
  }
}
