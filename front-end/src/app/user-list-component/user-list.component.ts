import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: any[] = [];

  user: any;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
      this.loadUsers();
      this.user=this.userService.getCurrentUser().username;
    }

  private loadUsers(): void {
     this.userService.getUsers().subscribe(users => {
           this.users = users;
     });
   }

  banUser(userId: number): void {
      if (confirm('Are you sure you want to ban this user?')) {
         this.userService.banUser(userId).subscribe(
             (response: any) => {
              this.loadUsers();
             },
             (error: any) => {
               this.loadUsers();
              }
             );
        }
      }

  unbanUser(userId: number): void {
        if (confirm('Are you sure you want to unban this user?')) {
           this.userService.unbanUser(userId).subscribe(
               (response: any) => {
                this.loadUsers();
               },
               (error: any) => {
                 this.loadUsers();
                }
               );
          }
        }

  isModerator(): boolean {
    if(this.userService.getCurrentUser().position==1)
    {
    return true;
    }
    else
    {
    return false;
    }
  }

  isUnbanned(userId: number): boolean
  {
     let user = this.users.filter(user => user.userId == userId)
     if(user[0].banned==0 && this.userService.getCurrentUser().userId!=userId)
     {
        return true;
     }
     else
     {
        return false;
     }
  }


  isBanned(userId: number): boolean
    {
       let user = this.users.filter(user => user.userId == userId)
       if(user[0].banned==1 && this.userService.getCurrentUser().userId!=userId)
       {
          return true;
       }
       else
       {
          return false;
       }
  }

  logout(): void{
  this.userService.logout();
  this.router.navigate(['']);
  }

  update():void {
  this.router.navigate(['/users/updateUser']);
  }

  viewQuestions():void {
  this.router.navigate(['/questions/getAll']);
  }

}
