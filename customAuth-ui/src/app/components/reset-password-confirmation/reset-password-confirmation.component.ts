import { Component, OnInit } from '@angular/core';
import {CustomAuthService} from '../../services/custom-auth.service';

@Component({
  selector: 'app-reset-password-confirmation',
  templateUrl: './reset-password-confirmation.component.html',
  styleUrls: ['./reset-password-confirmation.component.css']
})
export class ResetPasswordConfirmationComponent implements OnInit {

      constructor(private customAuthService: CustomAuthService) { }

      message;

      ngOnInit() {
        this.message = "Your password has been changed. You can login now.";
        this.customAuthService.clearStorage();
      }
    }
