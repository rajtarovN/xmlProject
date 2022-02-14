import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Login } from 'src/modules/shared/models/login';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient, private route: Router) {}

  login(user: Login): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
      Accept: 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.login}`,
      user,
      { headers: headers,
      responseType: "text" }
    );
  }

  logout(): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.logout}`,
      { responseType: "text" }
    );
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem("email")) {
      return false;
    }
    return true;
  }

  getKorisnike(searchTerm: string): Observable<string>{
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getKorisnike}/${searchTerm}`,
      { headers: headers,
        responseType: "text" }
    );
  }
  
}
