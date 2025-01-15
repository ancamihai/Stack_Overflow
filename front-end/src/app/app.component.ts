import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'firstangapp';
   username: string = '';
    password: string = '';

    onSubmit() {
      console.log('Username:', this.username);
      console.log('Password:', this.password);
    }
}
