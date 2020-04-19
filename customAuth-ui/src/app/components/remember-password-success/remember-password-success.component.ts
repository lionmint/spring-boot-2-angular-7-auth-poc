import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-remember-password-success',
  templateUrl: './remember-password-success.component.html',
  styleUrls: ['./remember-password-success.component.css']
})
export class RememberPasswordSuccessComponent implements OnInit {

    constructor() { }

    message;

    ngOnInit() {
      this.message = "We have sent you a mail to change your password.";
    }
  }
