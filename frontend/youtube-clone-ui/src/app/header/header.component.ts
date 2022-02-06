import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(
    private oidcSecurityService: OidcSecurityService,
    private router: Router
  ) {}

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

  public uploadRedirect(): void {
    this.router.navigate(['./upload-video']);
  }
}
