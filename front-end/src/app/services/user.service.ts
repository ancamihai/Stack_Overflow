import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/users';

    constructor(private http: HttpClient) { }

    getUsers(): Observable<any[]> {
       return this.http.get<any[]>(this.apiUrl+'/getAll');
    }

    getUsersWithScores(): Observable<any[]> {
           return this.http.get<any[]>(this.apiUrl+'/getUsersWithScores');
    }

    register(user: any): Promise<any> {
     let userToAdd = { userId: null, ...user };
     return this.http.post(this.apiUrl + '/addUser', userToAdd).toPromise();
    }

    banUser(userId: number): Observable<any> {
       const url = `${this.apiUrl}/banUser?userId=${userId}`;
       return this.http.put<string>(url, null);
    }

    unbanUser(userId: number): Observable<any> {
           const url = `${this.apiUrl}/unbanUser?userId=${userId}`;
           return this.http.put<string>(url, null);
    }

    getUser(username: string, password: string): Observable<any> {
        const params = new HttpParams()
          .set('username', username)
          .set('password', password);
        return this.http.get<any>(`${this.apiUrl}/findUser`, { params });
    }

    logout(): void {
      sessionStorage.removeItem('currentUser');
      localStorage.clear();
      sessionStorage.clear();
    }

    getCurrentUser(): any {
      const userJson = sessionStorage.getItem('currentUser');
      return userJson ? JSON.parse(userJson) : null;
    }

     deleteUser(userId: number): Observable<string> {
        const params = new HttpParams().set('userId', userId.toString());
        return this.http.delete<string>(this.apiUrl + '/deleteById', { params });
     }

     updateUser(user: any): Observable<any> {
         return this.http.put(this.apiUrl + '/updateUser', user);
     }

}
