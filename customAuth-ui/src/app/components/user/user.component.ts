import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';
import {User} from "../../models/user.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  public users;
  displayedColumns: string[] = ['email', 'firstName', 'lastName'];
  dataSource;

  constructor(private loginService: LoginService) { }

  ngOnInit() {
    console.log("Open User Page");
    this.getUser();
  }

  getUser() {
    let email = localStorage.getItem('email');
    this.loginService.getUserData(email).subscribe(
        data => {this.dataSource = data},
        err => console.error(err),
        () => console.log('domains loaded')
      );
  }

}
