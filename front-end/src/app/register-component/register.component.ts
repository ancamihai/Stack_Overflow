import {Component, Input, Output , EventEmitter} from "@angular/core";
import {Router} from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {

  user={username: '', email: '', lastName: '', firstName:'', password:'', position: 0, banned: 0, phone: ''}

  constructor(private router: Router, private userService: UserService) { }

  redirect()
  {
   this.router.navigate(['']);
  }

 async register(): Promise<void> {
   try {
     const response = await this.userService.register(this.user);
     alert(response);
   } catch (error:any) {
     alert(error.error.text);
   } finally {
     this.user = {username: '', email: '', lastName: '', firstName:'', password:'', position: 0, banned: 0, phone: ''};
     this.router.navigate(['']);
   }
 }


}
