import { Component, OnInit } from '@angular/core';
import {CustomAuthService} from '../../services/custom-auth.service';
import { AuthService } from 'angularx-social-login';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private customAuthService: CustomAuthService, private authService: AuthService) { }

  ngOnInit() {
  }

  public logout()
  {
    this.customAuthService.logout();
    this.authService.signOut();
  }
}
