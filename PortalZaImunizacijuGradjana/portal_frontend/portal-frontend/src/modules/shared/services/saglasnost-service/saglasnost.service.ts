import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SaglasnostGradjanaElement } from 'src/modules/zradnik/models/saglasnost-gradjana-element';

@Injectable({
  providedIn: 'root'
})
export class SaglasnostService {
  constructor(private http: HttpClient, private route: Router) {}

  searchTermine(datumTermina: Date, imePrezime: string): Observable<Array<SaglasnostGradjanaElement>> {
    return this.http.get<Array<SaglasnostGradjanaElement>>(
      `${environment.baseUrl}/${environment.searchTermine}/${imePrezime}/${datumTermina}`
    );
  }
}
