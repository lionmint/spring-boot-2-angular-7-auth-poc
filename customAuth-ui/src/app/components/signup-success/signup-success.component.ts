import { Component, OnInit } from '@angular/core';
import {SignupService} from '../../services/signup.service';
import { ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-signup-success',
  templateUrl: './signup-success.component.html',
  styleUrls: ['./signup-success.component.css']
})
export class SignupSuccessComponent implements OnInit {

  constructor() { }

  message;

  ngOnInit() {
    this.message = "We have sent you a confirmation mail. Please confirm your registration and login.";
  }
}
