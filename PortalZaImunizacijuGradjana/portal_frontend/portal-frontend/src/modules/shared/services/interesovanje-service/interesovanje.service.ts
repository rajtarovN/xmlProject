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

  addInteresovanje(interesovanjeXML: string, id: string): Observable<any> {
    return this.http.post<string>(
      `${environment.baseUrl}` + '/interesovanje/' + id,
      interesovanjeXML,
      this.httpOptions
    );
  }

  deleteInteresovanje(id: string): Observable<any> {
    return this.http.delete(
      `${environment.baseUrl}` + '/interesovanje/' + id,
      this.httpOptions
    );
  }

  getInteresovanje(email: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}` + '/interesovanje/' + email, {
      headers: this.httpOptions.headers,
      responseType: 'text',
    });
  }

  getJSON(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.jsonInteresovanje}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  getRDF(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.rdfInteresovanje}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
      );
  }

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/interesovanje/getAllI/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    if(!id.startsWith("interesovanje")){
      id = "interesovanjet_" + id + ".xml";
    }
    return this.http.get(
      `${environment.baseUrl}/interesovanje/generatePDF/${id}`,
      { responseType: 'arraybuffer' }
    );
  }
  getXHtml(id: string): Observable<any> {
    if(!id.startsWith("interesovanje")){
      id = "interesovanje_" + id + ".xml";
    }
    return this.http.get(
      `${environment.baseUrl}/interesovanje/generateHTML/${id}`,
      { responseType: 'arraybuffer' });
  }
}
