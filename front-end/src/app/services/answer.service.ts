import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  private apiUrl = 'http://localhost:8080/answers';
  private currentAnswer: number = 0;

  constructor(private http: HttpClient) { }

  getAllAnswersByQuestionId(questionId: number): Observable<any[]> {
      const params = new HttpParams().set('questionId', questionId.toString());
      return this.http.get<any[]>(`${this.apiUrl}/getByQuestion`, { params });
  }

  getAllAnswers():  Observable<any[]>  {
      return this.http.get<any[]>(this.apiUrl+'/getAll');
  }

  addAnswer(answer: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/addAnswer`, answer);
  }

  updateAnswer(id: number, answer: any, userId: number): Observable<any> {
        let answerWithId = {answerId: id, ...answer}
        return this.http.put<string>(`${this.apiUrl}/updateAnswer?userId=${userId}`, answerWithId);
  }

  getAnswerById(answerId: number, answers: any[]): any {
        return answers.find(answer => answer.answerId == answerId);
  }

  getCurrentAnswer(): any {
        const answerJson = sessionStorage.getItem('currentAnswer');
        return answerJson ? JSON.parse(answerJson) : null;
  }

  setCurrentAnswer(answer: number, answers: any []): void {
        this.currentAnswer = this.getAnswerById(answer, answers);
        sessionStorage.setItem('currentAnswer', JSON.stringify(this.currentAnswer));
  }

  deleteAnswerById(answerId: number, userId: number): Observable<string> {
        const params = new HttpParams()
          .set('answerId', answerId.toString())
          .set('userId', userId.toString());

        return this.http.delete<string>(`${this.apiUrl}/deleteById`, { params });
  }

}
