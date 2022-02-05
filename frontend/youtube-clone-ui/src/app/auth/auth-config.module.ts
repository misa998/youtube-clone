import { NgModule } from '@angular/core';
import { AuthModule, LogLevel } from 'angular-auth-oidc-client';

@NgModule({
  imports: [
    AuthModule.forRoot({
      config: {
        authority: 'https://dev-i2j-73q.eu.auth0.com',
        redirectUrl: window.location.origin,
        clientId: 'TxCDDa6jzwXQDJSWpKN2gp52PpZnp5a4',
        scope: 'openid profile offline_access',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        logLevel: LogLevel.Debug,
        secureRoutes: ['http://localhost:8080'],
        customParamsAuthRequest: {
          audience: 'http://localhost:8008',
        },
      },
    }),
  ],
  providers: [],
  exports: [AuthModule],
})
export class AuthConfigModule {}
