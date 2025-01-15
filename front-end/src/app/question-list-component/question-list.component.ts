import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../services/user.service';
import { VoteQuestionService } from '../services/vote_question.service';
import { QuestionService } from '../services/question.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent implements OnInit {
  questions: any[] = [];
  scores: any[] = [];
  userScores: any[] = [];
  votes: any[] = [];
  filteredQuestions: any[] = [];
  selectedCriteria: string='none';
  tagToBeFiltered: string='';
  userToBeFiltered: string='';
  titleToBeFiltered: string='';

  constructor(private router: Router, private userService: UserService, private questionService: QuestionService, private voteQuestionService: VoteQuestionService) { }

  ngOnInit(): void {
  this.loadQuestions();
  this.loadScores();
  this.loadUserScores();
  this.loadVotes();
  }

  private loadQuestions(): void {
    this.questionService.getAllQuestions().subscribe(questions => {
       this.questions = questions;
       this.filteredQuestions = questions;
    });
  }

  private loadScores(): void {
      this.voteQuestionService.getAllScores().subscribe(scores => {
         this.scores = scores;
      });
    }

  private loadUserScores(): void {
          this.userService.getUsersWithScores().subscribe(userScores => {
             this.userScores = userScores;
     });
  }

  private loadVotes(): void {
      this.voteQuestionService.getAllVotes().subscribe(votes => {
           this.votes = votes;
    });
  }

  getScore(index: number): string {
    return this.scores[index].toString();
  }

  liked(questionId: number): boolean {
   const filteredVotes = this.votes.filter(vote => vote.question.questionId === questionId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===1);
   return filteredVotes.length > 0;
  }

  disliked(questionId: number): boolean {
     const filteredVotes = this.votes.filter(vote => vote.question.questionId === questionId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===-1);
     return filteredVotes.length > 0;
  }

  like(questionId:number, userId: number)
  {
     if(userId === this.userService.getCurrentUser().userId)
     {
       alert("You are not allowed to like your own question!");
     }
     else
     {
       this.voteQuestionService.upvoteQuestion(questionId, this.userService.getCurrentUser().userId).subscribe(response => {
             this.loadQuestions();
             this.loadScores();
             this.loadUserScores();
             this.loadVotes();
       }, error => {
             this.loadQuestions();
             this.loadScores();
             this.loadUserScores();
             this.loadVotes();
       });
     }
  }

  dislike(questionId:number, userId: number)
    {
       if(userId === this.userService.getCurrentUser().userId)
       {
         alert("You are not allowed to dislike your own question!");
       }
       else
       {
         this.voteQuestionService.downvoteQuestion(questionId, this.userService.getCurrentUser().userId).subscribe(response => {
               this.loadQuestions();
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
         }, error => {
               this.loadQuestions();
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
         });
       }
    }

  viewUsers():void {
    this.router.navigate(['/users/getAll']);
  }

  logout(): void{
   this.userService.logout();
   this.router.navigate(['']);
  }

  addQuestion(): void {
    this.router.navigate(['/questions/addQuestion']);
  }

  updateQuestion(questionId : number): void {
    this.questionService.setCurrentQuestion(questionId, this.questions);
    this.router.navigate(['/questions/updateQuestion']);
  }

  addAnswer(questionId : number): void {
      this.questionService.setCurrentQuestion(questionId, this.questions);
      this.router.navigate(['/answers/addAnswer']);
  }

  viewAnswers(questionId : number): void {
  this.questionService.setCurrentQuestion(questionId, this.questions);
  this.router.navigate(['/answers/getAll']);
  }

  getUserScore(userId: number): number
  {
     let userWithScore= this.userScores.filter(userWithScore => userWithScore.userId === userId);
     return userWithScore[0].score;
  }


   deleteQuestion(questionId: number): void {
   if (confirm('Are you sure you want to delete this question?')) {
      this.questionService.deleteQuestionById(questionId, this.userService.getCurrentUser().userId).subscribe(
        (response) => {
        this.loadQuestions();
        this.loadScores();
        this.loadUserScores();
        this.loadVotes();
        alert(response);
        },
        (error) => {
        this.loadQuestions();
        this.loadScores();
        this.loadUserScores();
        this.loadVotes();
        alert(error.error.text);
        }
      );
      }
   }

  filterQuestions(): void
  {
     switch (this.selectedCriteria) {
         case 'own':
           this.filteredQuestions = this.questions.filter(question => question.user.userId === this.userService.getCurrentUser().userId);
           break;
         case 'title':
           this.questionService.getQuestionsByTitle(this.titleToBeFiltered).subscribe(
                 questions => {
                   this.filteredQuestions = questions;
                 }
               );
           break;
         case 'tag':
           this.questionService.getQuestionsByTag(this.tagToBeFiltered).subscribe(
                     questions => {
                       this.filteredQuestions = questions;
                     }
                );
           break;
          case 'user':
                    this.questionService.getQuestionsByUser(this.userToBeFiltered).subscribe(
                              questions => {
                                this.filteredQuestions = questions;
                              }
                         );
           break;
         default:
           this.questionService.getAllQuestions().subscribe(questions => {
            this.filteredQuestions = questions;
           });
           break;
  }
  }

}
