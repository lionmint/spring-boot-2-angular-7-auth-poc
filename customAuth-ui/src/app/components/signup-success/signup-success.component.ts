import { Component, OnInit } from '@angular/core';
import {SignupService} from '../../services/signup.service';
import { ActivatedRoute} from '@angular/router';
import { AuthService } from "angularx-social-login";

@Component({
  selector: 'app-signup-success',
  templateUrl: './signup-success.component.html',
  styleUrls: ['./signup-success.component.css']
})
export class SignupSuccessComponent implements OnInit {

  constructor(private authService: AuthService) { }

  message;

  ngOnInit() {
    this.authService.authState.subscribe((user) => {

      let loggedIn = (user != null);

      if(loggedIn)
      {
        this.message = "We have sent you a confirmation mail. Welcome to our page.";
      }else
      {
        this.message = "We have sent you a confirmation mail. Please confirm your registration and login.";
      }
    });
  }
}
