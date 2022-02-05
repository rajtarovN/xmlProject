import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Login } from 'src/modules/shared/models/login';
import { UserTokenState } from 'src/modules/shared/models/user-token-state';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient, private route: Router) {}

  login(user: Login): Observable<UserTokenState> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
      Accept: 'application/xml',
    });
    return this.http.post<UserTokenState>(
      `${environment.baseUrl}/${environment.login}`,
      user,
      { headers: headers }
    );
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem("email")) {
      return false;
    }
    return true;
  }
}
