import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Izvestaj } from 'src/modules/shared/models/izvestaj';

@Injectable({
  providedIn: 'root',
})
export class IzvestajService {
  constructor(private http: HttpClient, private route: Router) {}

  getIzvestaj(dateFrom: string, dateTo: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.getIzvestaj}/${dateFrom}/${dateTo}`,
      { responseType: 'arraybuffer' }
    );
  }

  getPdf(dateFrom: string, dateTo: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.pdfIzvestaj}/${dateFrom}/${dateTo}`,
      { responseType: 'arraybuffer' }
    );
  }


  getHtml(dateFrom: string, dateTo: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.htmlIzvestaj}/${dateFrom}/${dateTo}`,
      { responseType: 'arraybuffer' }
    );
  }
}
