import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { QuestionService } from '../services/question.service';
import { AnswerService } from '../services/answer.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.scss']
})

export class AnswerComponent implements OnInit {

   @Input() answer: any = {
       question: '',
       user: '',
       text: '',
       picture: ''
   };

   @Input() isAddAnswer: boolean = true;

   constructor(private router: Router, private userService: UserService, private questionService: QuestionService, private answerService: AnswerService) {}

   ngOnInit(): void {
      this.answer.question=this.questionService.getCurrentQuestion();
   }

   addAnswer() {
    this.answer.user=this.userService.getCurrentUser();
    this.answerService.addAnswer(this.answer).subscribe(
        (response) => {
        alert(response);
          this.router.navigate(['/answers/getAll']);
        },
          (error) => {
          alert(error.error.text);
          this.router.navigate(['/answers/getAll']);
        }
    );
   }

   redirect() {
    this.router.navigate(['/questions/getAll']);
   }

   redirectUpdateAnswer() {
       this.router.navigate(['/answers/getAll']);
   }

   onFileSelected(event: any) {
       const file = event.target.files[0];
       if (file) {
         const lastSlashIndex = file.name.lastIndexOf('/');
         const fileName = file.name.substring(lastSlashIndex + 1);
         console.log('File name split by /:', fileName);
         this.answer.picture = fileName;
         }
   }

   updateAnswer(answerId: number, userId:number, updatedAnswer: any) {
     if (confirm('Are you sure you want to update this answer?')) {
       if(userId==this.userService.getCurrentUser().userId || this.userService.getCurrentUser().position ==1)
       {
          this.answerService.updateAnswer(answerId, updatedAnswer, userId).subscribe(
              (response: any) => {
                alert(response);
                this.router.navigate(['/answers/getAll']);
              },
              (error: any) => {
                alert(error.error.text);
                this.router.navigate(['/answers/getAll']);
              }
          );
       }
      else{
          alert("You are not allowed to update this answer");
      }
     }
   }

}
