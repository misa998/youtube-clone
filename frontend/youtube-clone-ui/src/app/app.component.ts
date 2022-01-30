import { Component, OnInit } from '@angular/core';
import {
  OidcConfigService,
  OidcSecurityService,
} from 'angular-auth-oidc-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'youtube-clone-ui';

  constructor(private oidcSecurityService: OidcSecurityService) {}

  ngOnInit(): void {
    this.oidcSecurityService
      .checkAuth()
      .subscribe(({ isAuthenticated, userData, accessToken, idToken }) => {
        console.log(
          'Auth: ' +
            isAuthenticated +
            ' userData: ' +
            userData +
            ' accessToken: ' +
            accessToken +
            ' idToken: ' +
            idToken
        );
      });
  }
}
