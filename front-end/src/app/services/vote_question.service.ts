import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class VoteQuestionService {

   private apiUrl = 'http://localhost:8080/voteQuestions';

   constructor(private http: HttpClient) { }

    getAllScores():  Observable<any[]>  {
         return this.http.get<any[]>(this.apiUrl+'/getScores');
    }

    getScoreById(questionId:number):  Observable<any[]>  {
       const url = `${this.apiUrl}/getScoreById?questionId=${questionId}`;
       return this.http.get<any[]>(url);
    }

    getAllVotes():  Observable<any[]>  {
             return this.http.get<any[]>(this.apiUrl+'/getAll');
    }

    upvoteQuestion(questionId: number, userId: number): Observable<string> {
        const url = `${this.apiUrl}/upvoteQuestion?questionId=${questionId}&userId=${userId}`;
        return this.http.post<string>(url, null);
    }

    downvoteQuestion(questionId: number, userId: number): Observable<string> {
            const url = `${this.apiUrl}/downvoteQuestion?questionId=${questionId}&userId=${userId}`;
            return this.http.post<string>(url, null);
    }

}
