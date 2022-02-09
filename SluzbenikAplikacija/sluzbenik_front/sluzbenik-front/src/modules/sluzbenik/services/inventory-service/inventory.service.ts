import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InventoryService {
  constructor(private http: HttpClient) {}

  url = 'http://localhost:8082/api/zalihe';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/xml',
    }),
  };

  getInventory(): Observable<string> {
    return this.http.get(this.url, {
      headers: this.httpOptions.headers,
      responseType: 'text',
    });
  }

  updateInventory(zaliheXML: string): Observable<any> {
    return this.http.post<string>(this.url, zaliheXML, this.httpOptions);
  }
}
