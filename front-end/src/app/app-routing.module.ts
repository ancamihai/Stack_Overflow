import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {LoginComponent} from "./login-component/login.component";
import {RegisterComponent} from "./register-component/register.component";
import {UpdateUserComponent} from "./update-user-component/update-user.component";
import {UserListComponent} from "./user-list-component/user-list.component";

import {AddQuestionComponent} from "./question-component/add-question-component/add-question.component";
import {UpdateQuestionComponent} from "./question-component/update-question-component/update-question.component";
import {QuestionListComponent} from "./question-list-component/question-list.component";

import {QuestionWithAnswersComponent} from "./question-answers-component/question-answers.component";

import {AddAnswerComponent} from "./answer-component/add-answer-component/add-answer.component";
import {UpdateAnswerComponent} from "./answer-component/update-answer-component/update-answer.component";

import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  {path:'', component:LoginComponent},
  {path:'users/addUser', component:RegisterComponent},
  {path:'users/getAll', component:UserListComponent, canActivate: [AuthGuard]},
  {path:'users/updateUser', component:UpdateUserComponent, canActivate: [AuthGuard]},
  {path:'questions/getAll', component:QuestionListComponent, canActivate: [AuthGuard]},
  {path:'questions/addQuestion', component:AddQuestionComponent, canActivate: [AuthGuard]},
  {path:'questions/updateQuestion', component:UpdateQuestionComponent, canActivate: [AuthGuard]},
  {path:'answers/addAnswer', component:AddAnswerComponent, canActivate: [AuthGuard]},
  {path: 'answers/getAll', component:QuestionWithAnswersComponent, canActivate: [AuthGuard]},
  {path: 'answers/updateAnswer', component:UpdateAnswerComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
