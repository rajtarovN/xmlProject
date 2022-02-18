import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InventoryService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/xml',
    }),
  };

  getInventory(): Observable<string> {
    return this.http.get(`${environment.baseUrl}` + `/zalihe`, {
      headers: this.httpOptions.headers,
      responseType: 'text',
    });
  }

  updateInventory(zaliheXML: string): Observable<any> {
    return this.http.post<string>(
      `${environment.baseUrl}` + `/zalihe/saveWithPending`,
      zaliheXML,
      this.httpOptions
    );
  }
}
