import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InteresovanjeService {
  constructor(private http: HttpClient, private route: Router) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/xml',
      Accept: 'application/xml',
    }),
  };

  addInteresovanje(
    interesovanjeXML: string,
    jmbg: string,
    datum: string
  ): Observable<any> {
    return this.http.post<string>(
      `${environment.baseUrl}` + '/api/interesovanje/' + jmbg + '/' + datum,
      interesovanjeXML,
      this.httpOptions
    );
  }
}
