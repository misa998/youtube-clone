import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthHeaderInterceptor implements HttpInterceptor {
  constructor(private securityService: OidcSecurityService) {}

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    console.log('Access token: ' + this.securityService.getAccessToken());
    const request = req.clone({
      setHeaders: {
        'Content-type': 'application/json',
        Accept: 'application/json',
        Authorization: 'Bearer ' + this.securityService.getAccessToken(),
      },
    });

    return next.handle(request);
  }
}
