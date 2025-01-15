import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TagService {
  private apiUrl = 'http://localhost:8080/tags';

  constructor(private http: HttpClient) { }

  getAllTags(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl+'/getAll');
  }

  addTag(tag: any): Observable<any> {
      return this.http.post<any>(this.apiUrl + '/addTag', tag);
  }

}
