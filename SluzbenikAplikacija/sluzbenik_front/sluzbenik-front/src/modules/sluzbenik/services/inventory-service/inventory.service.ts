import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { HelperService } from '../helper-service/helper.service';

@Injectable({
  providedIn: 'root',
})
export class InventoryService {
  constructor(private http: HttpClient, private helper: HelperService) {}

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
