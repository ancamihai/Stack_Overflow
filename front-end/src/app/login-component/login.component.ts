import {Component, Input, Output , EventEmitter, OnInit} from "@angular/core";
import {Router} from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit{
  user = { username: '', password: '' };
  users: any[] = [];

  ngOnInit(): void {
        this.loadUsers();
  }

  private loadUsers(): void {
       this.userService.getUsers().subscribe(users => {
             this.users = users;
       });
  }

 constructor(private router: Router, private userService: UserService) { }

  login(): void {
  this.userService.getUser(this.user.username, this.user.password).subscribe(
            (user) => {
            if (user) {
               if(user.banned==0){
               sessionStorage.setItem('currentUser', JSON.stringify(user));
               this.router.navigate(['/users/getAll']);
               }
               else {
                alert ("It seems that you are banned :(");
               }
               this.user = { username: '', password: '' };
            } else {
               alert('Invalid username or password');
            }
            }
  );
  }

  redirect()
  {
   this.router.navigate(['/users//addUser']);
  }

}
