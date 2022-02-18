import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class InterceptorInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = localStorage.getItem("accessToken");

    if (token) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization", token),
      });

      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }
}
