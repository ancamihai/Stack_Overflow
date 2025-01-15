import {Component, Input, Output , EventEmitter} from "@angular/core";
import {Router} from '@angular/router';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-updateUser',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})

export class UpdateUserComponent {

  user: any;

  constructor(private router: Router, private userService: UserService) {
  this.user = this.userService.getCurrentUser();
  this.user.password='';
  }

  redirect()
  {
   this.router.navigate(['/users/getAll']);
  }

   update(): void {
     this.userService.updateUser(this.user).subscribe(
       (response: any) => {
       if(response==="There is already another user with this username!" || response==="There is already another user with this email!" || response==="Make sure that no required field is empty!")
       { alert(response); }
       else{
           sessionStorage.setItem('currentUser', JSON.stringify(this.user));
       }
       this.router.navigate(['/users/getAll']);
       },
       (error: any) => {
         if(error.error.text ==="There is already another user with this username!" || error.error.text==="There is already another user with this email!" || error.error.text==="Make sure that no required field is empty!")
         { alert(error.error.text);
         }
         else{
           sessionStorage.setItem('currentUser', JSON.stringify(this.user));
         }
         this.router.navigate(['/users/getAll']);
       }
     );
   }

}
