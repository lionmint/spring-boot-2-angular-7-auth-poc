import { Component, OnInit } from '@angular/core';
import {CustomAuthService} from '../../services/custom-auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private customAuthService: CustomAuthService) { }

  ngOnInit() {
  }

  public logout()
  {
    this.customAuthService.logout();
  }
}
