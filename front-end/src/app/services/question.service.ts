import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = 'http://localhost:8080/questions';
  private currentQuestion: number = 0;

  constructor(private http: HttpClient) { }

  getAllQuestions():  Observable<any[]>  {
    return this.http.get<any[]>(this.apiUrl+'/getAll');
  }

  addQuestion(question: any): Observable<any> {
      return this.http.post<any>(`${this.apiUrl}/addQuestion`, question);
  }

  deleteQuestionById(questionId: number, userId: number): Observable<string> {
      const params = new HttpParams()
        .set('questionId', questionId.toString())
        .set('userId', userId.toString());

      return this.http.delete<string>(`${this.apiUrl}/deleteById`, { params });
  }

  getQuestionById(questionId: number, questions: any[]): any {
      return questions.find(question => question.questionId == questionId);
  }

  getCurrentQuestion(): any {
      const questionJson = sessionStorage.getItem('currentQuestion');
      return questionJson ? JSON.parse(questionJson) : null;
  }

  setCurrentQuestion(question: number, questions: any[]): void {
      this.currentQuestion = this.getQuestionById(question, questions);
      sessionStorage.setItem('currentQuestion', JSON.stringify(this.currentQuestion));
  }

  updateQuestion(id: number, question: any, userId: number): Observable<any> {
      let questionWithId = {questionId: id, ...question}
      return this.http.put<string>(`${this.apiUrl}/updateQuestion?userId=${userId}`, questionWithId);
  }

  getQuestionsByTitle(title: string): Observable<any[]> {
      const params = new HttpParams().set('title', title);
      return this.http.get<any[]>(`${this.apiUrl}/getByTitle`, { params });
  }

  getQuestionsByTag(tagName: string): Observable<any[]> {
      const params = new HttpParams().set('tagName', tagName);
      return this.http.get<any[]>(`${this.apiUrl}/getByTag`, { params });
  }

  getQuestionsByUser(username: string): Observable<any[]> {
        const params = new HttpParams().set('username', username);
        return this.http.get<any[]>(`${this.apiUrl}/getByUser`, { params });
    }
}
