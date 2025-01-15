import {Component, OnInit} from '@angular/core';
import { QuestionService } from '../../services/question.service';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.scss']
})

export class UpdateQuestionComponent implements OnInit{

constructor(private questionService: QuestionService) {}

  questionToUpdate: any = {
         user: '',
         title: '',
         text: '',
         picture: '',
         tags: []
  };

  ngOnInit(): void {
   this.questionToUpdate=this.questionService.getCurrentQuestion();
   this.questionToUpdate.picture='';
  }



}
