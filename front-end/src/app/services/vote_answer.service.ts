import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class VoteAnswerService {

   private apiUrl = 'http://localhost:8080/voteAnswers';

   constructor(private http: HttpClient) { }

    getAllScores(questionId:number):  Observable<any[]>  {
    const url = `${this.apiUrl}/getScores?questionId=${questionId}`;
         return this.http.get<any[]>(url);
    }

    getAllVotes():  Observable<any[]>  {
             return this.http.get<any[]>(this.apiUrl+'/getAll');
    }

    upvoteAnswer(answerId: number, userId: number): Observable<string> {
        const url = `${this.apiUrl}/upvoteAnswer?answerId=${answerId}&userId=${userId}`;
        return this.http.post<string>(url, null);
    }

    downvoteAnswer(answerId: number, userId: number): Observable<string> {
            const url = `${this.apiUrl}/downvoteAnswer?answerId=${answerId}&userId=${userId}`;
            return this.http.post<string>(url, null);
    }

}
