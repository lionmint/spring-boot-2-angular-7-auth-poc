import { Component, OnInit } from '@angular/core';
import {SignupService} from '../../services/signup.service';
import { ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-signup-confirmation',
  templateUrl: './signup-confirmation.component.html',
  styleUrls: ['./signup-confirmation.component.css']
})
export class SignupConfirmationComponent implements OnInit {

  constructor(private signupService: SignupService, private route: ActivatedRoute) { }

  message;
  id;

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get("id");
    this.signupService.authSignUp(this.id).subscribe(
      data => {
        console.log("Success: " + data);
        if(data)
        {
            this.message = "Congratulation your account is confirmed."
        }else
        {
          this.message = "Please check your confirmation mail and try again."
        }
      },
      err => {console.error(err)}
    );
  }

}
