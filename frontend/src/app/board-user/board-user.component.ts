import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { UserInfo } from '../profile/user.model';

@Component({
  selector: 'pm-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content?: UserInfo;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserBoard().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {console.log(err)
        if (err.error) {
          this.content = JSON.parse(err.error).message;
        } else {
          this.content = undefined;
        }
      }
    });
  }

}
