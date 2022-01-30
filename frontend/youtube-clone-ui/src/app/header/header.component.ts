import { Component, OnInit } from '@angular/core';
import {
  OidcConfigService,
  OidcSecurityService,
} from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService) {}

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        this.authenticated = isAuthenticated;
      }
    );
  }

  public login(): void {
    this.oidcSecurityService.authorize();
  }

  public logoff(): void {
    this.oidcSecurityService.logoffAndRevokeTokens();
  }
}
