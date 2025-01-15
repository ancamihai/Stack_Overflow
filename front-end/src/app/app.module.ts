import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule} from './app-routing.module';
import { FormsModule} from "@angular/forms";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import {HttpClientModule} from "@angular/common/http";

import {AppComponent} from './app.component';
import {LoginComponent} from "./login-component/login.component";
import {RegisterComponent} from "./register-component/register.component";
import {UpdateUserComponent} from "./update-user-component/update-user.component";
import {UserListComponent} from "./user-list-component/user-list.component";

import {AnswerComponent} from "./answer-component/answer.component";
import {AddAnswerComponent} from "./answer-component/add-answer-component/add-answer.component";
import {UpdateAnswerComponent} from "./answer-component/update-answer-component/update-answer.component";

import {UpdateQuestionComponent} from "./question-component/update-question-component/update-question.component";
import {AddQuestionComponent} from "./question-component/add-question-component/add-question.component";
import {QuestionComponent} from "./question-component/question.component";
import {QuestionListComponent} from "./question-list-component/question-list.component";

import {QuestionWithAnswersComponent} from "./question-answers-component/question-answers.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserListComponent,
    UpdateUserComponent,
    QuestionListComponent,
    QuestionComponent,
    AddQuestionComponent,
    UpdateQuestionComponent,
    AddAnswerComponent,
    AnswerComponent,
    QuestionWithAnswersComponent,
    UpdateAnswerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCardModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
