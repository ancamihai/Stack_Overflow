import {Component, OnInit} from '@angular/core';
import { AnswerService } from '../../services/answer.service';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-answer.component.html',
  styleUrls: ['./update-answer.component.scss']
})

export class UpdateAnswerComponent implements OnInit{

constructor(private answerService: AnswerService) {}

  answerToUpdate: any = {
         question: '',
         user: '',
         text: '',
         picture: '',
  };

  ngOnInit(): void {
   this.answerToUpdate=this.answerService.getCurrentAnswer();
   this.answerToUpdate.picture='';
  }



}
