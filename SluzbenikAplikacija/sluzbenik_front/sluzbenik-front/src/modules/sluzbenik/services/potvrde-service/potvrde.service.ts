import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PotvrdeService {
  constructor(private http: HttpClient, private route: Router) {}

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getPotvrde}/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.potvrdaPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }
}
