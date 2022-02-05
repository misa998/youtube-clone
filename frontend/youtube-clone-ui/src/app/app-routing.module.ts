import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthConfigModule } from './auth/auth-config.module';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { UploadVideoComponent } from './upload-video/upload-video.component';

export const routes: Routes = [
  {
    path: 'upload-video',
    component: UploadVideoComponent,
  },
  {
    path: 'save-video-details/:videoId',
    component: SaveVideoDetailsComponent,
  },
  { path: '', redirectTo: '/upload-video', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
