import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../services/question.service';
import { UserService } from '../services/user.service';
import { AnswerService } from '../services/answer.service';
import { Router, ActivatedRoute} from '@angular/router';
import { VoteAnswerService } from '../services/vote_answer.service';
import { VoteQuestionService } from '../services/vote_question.service';

@Component({
  selector: 'app-question-answers',
  templateUrl: './question-answers.component.html',
  styleUrls: ['./question-answers.component.scss']
})
export class QuestionWithAnswersComponent implements OnInit {

  question: any;
  answers: any[] = [];
  allAnswers: any[] = [];
  votes: any[] = [];
  scores: any[] = [];
  questionVotes: any[] = [];
  userScores: any[] = [];
  questionScore: any;

  constructor(private answerService: AnswerService, private questionService: QuestionService, private userService: UserService, private router: Router, private route: ActivatedRoute, private voteAnswerService: VoteAnswerService, private voteQuestionService: VoteQuestionService ) { }

  ngOnInit(): void {
    this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
    this.loadScores();
    this.loadVotes();
    this.loadUserScores();
    this.loadQuestionScore();
    this.loadQuestionVotes();
  }

  loadAnswersForQuestion(questionId: number): void {
  this.question = this.questionService.getCurrentQuestion();
  this.answerService.getAllAnswersByQuestionId(this.question.questionId).subscribe(answers => {
         this.answers = answers;
      });
  this.answerService.getAllAnswers().subscribe(answers => {
           this.allAnswers = answers;
      });
  }

  private loadScores(): void {
        this.voteAnswerService.getAllScores(this.question.questionId).subscribe(scores => {
           this.scores = scores;
        });
      }

  private loadQuestionScore(): void {
    this.voteQuestionService.getScoreById(this.question.questionId).subscribe(score => {
             this.questionScore = score;
    });
  }

    private loadVotes(): void {
        this.voteAnswerService.getAllVotes().subscribe(votes => {
             this.votes = votes;
      });
  }

    private loadUserScores(): void {
              this.userService.getUsersWithScores().subscribe(userScores => {
                 this.userScores = userScores;
         });
  }

  private loadQuestionVotes(): void {
        this.voteQuestionService.getAllVotes().subscribe(votes => {
             this.questionVotes = votes;
      });
  }

  getScore(index: number): string {
      return this.scores[index].toString();
  }

  getUserScore(userId: number): number {
       let userWithScore= this.userScores.filter(userWithScore => userWithScore.userId === userId);
       return userWithScore[0].score;
  }

    liked(answerId: number): boolean {
     const filteredVotes = this.votes.filter(vote => vote.answer.answerId === answerId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===1);
     return filteredVotes.length > 0;
  }

    disliked(answerId: number): boolean {
       const filteredVotes = this.votes.filter(vote => vote.answer.answerId === answerId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===-1);
       return filteredVotes.length > 0;
  }

    like(answerId:number, userId: number)
    {
       if(userId === this.userService.getCurrentUser().userId)
       {
         alert("You are not allowed to like your own answer!");
       }
       else
       {
         this.voteAnswerService.upvoteAnswer(answerId, this.userService.getCurrentUser().userId).subscribe(response => {
               this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
               this.loadQuestionScore();
               this.loadQuestionVotes();
         }, error => {
               this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
               this.loadQuestionScore();
               this.loadQuestionVotes();
         });
       }
    }

    dislike(answerId:number, userId: number)
      {
         if(userId === this.userService.getCurrentUser().userId)
         {
           alert("You are not allowed to dislike your own answer!");
         }
         else
         {
           this.voteAnswerService.downvoteAnswer(answerId, this.userService.getCurrentUser().userId).subscribe(response => {
                 this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
                 this.loadScores();
                 this.loadUserScores();
                 this.loadVotes();
                 this.loadQuestionScore();
                 this.loadQuestionVotes();
           }, error => {
                 this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
                 this.loadScores();
                 this.loadUserScores();
                 this.loadVotes();
                 this.loadQuestionScore();
                 this.loadQuestionVotes();
           });
         }
      }

  likedQuestion(questionId: number): boolean {
     const filteredVotes = this.questionVotes.filter(vote => vote.question.questionId === questionId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===1);
     return filteredVotes.length > 0;
    }

    dislikedQuestion(questionId: number): boolean {
       const filteredVotes = this.questionVotes.filter(vote => vote.question.questionId === questionId && vote.user.userId === this.userService.getCurrentUser().userId && vote.upOrDown ===-1);
       return filteredVotes.length > 0;
    }

    likeQuestion(questionId:number, userId: number)
    {
       if(userId === this.userService.getCurrentUser().userId)
       {
         alert("You are not allowed to like your own question!");
       }
       else
       {
         this.voteQuestionService.upvoteQuestion(questionId, this.userService.getCurrentUser().userId).subscribe(response => {
               this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
               this.loadQuestionScore();
               this.loadQuestionVotes();
         }, error => {
               this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
               this.loadScores();
               this.loadUserScores();
               this.loadVotes();
               this.loadQuestionScore();
               this.loadQuestionVotes();
         });
       }
    }

    dislikeQuestion(questionId:number, userId: number)
      {
         if(userId === this.userService.getCurrentUser().userId)
         {
           alert("You are not allowed to dislike your own question!");
         }
         else
         {
           this.voteQuestionService.downvoteQuestion(questionId, this.userService.getCurrentUser().userId).subscribe(response => {
                 this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
                 this.loadScores();
                 this.loadUserScores();
                 this.loadVotes();
                 this.loadQuestionScore();
                 this.loadQuestionVotes();
           }, error => {
                 this.loadAnswersForQuestion(this.questionService.getCurrentQuestion().questionId);
                 this.loadScores();
                 this.loadUserScores();
                 this.loadVotes();
                 this.loadQuestionScore();
                 this.loadQuestionVotes();
           });
         }
      }

  deleteAnswer(answerId: number, userId:number):  void {
     if (confirm('Are you sure you want to delete this question?')) {
        this.answerService.deleteAnswerById(answerId, this.userService.getCurrentUser().userId).subscribe(
          (response) => {
          this.loadAnswersForQuestion(this.question.questionId);
          this.loadScores();
          this.loadUserScores();
          this.loadVotes();
          this.loadQuestionScore();
          this.loadQuestionVotes();
          alert(response);
          },
          (error) => {
          this.loadAnswersForQuestion(this.question.questionId);
          this.loadScores();
          this.loadUserScores();
          this.loadVotes();
          this.loadQuestionScore();
          this.loadQuestionVotes();
          alert(error.error.text);
          }
        );
        }
     }

  addAnswer(questionId : number): void {
     this.router.navigate(['/answers/addAnswer']);
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['']);
  }

  viewUsers(): void {
    this.router.navigate(['/users/getAll']);
  }

  viewQuestions(): void {
    this.router.navigate(['/questions/getAll']);
  }

  updateAnswer(answerId: number): void {
  this.answerService.setCurrentAnswer(answerId, this.allAnswers);
  this.router.navigate(['/answers/updateAnswer']);
  }

}
