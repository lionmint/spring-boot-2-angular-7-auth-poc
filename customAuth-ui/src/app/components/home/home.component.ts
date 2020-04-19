import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user = 'Guest';
  validMessage='';

  constructor() { }

  ngOnInit() {
      let user = localStorage.getItem('user');
      if(user.localeCompare('')!=0)
      {
        this.user = user;
      }
  }

}
