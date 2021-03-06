import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';
import { Video } from './Video';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  constructor(private httpClient: HttpClient) {}

  public uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {
    console.log('Uploading with service.');
    const formData = new FormData();
    formData.append('file', fileEntry, fileEntry.name);

    return this.httpClient.post<UploadVideoResponse>(
      'http://localhost:8080/api/video/',
      formData
    );
  }

  public uploadThumnail(fileEntry: File, videoId: string): Observable<string> {
    console.log('Uploading with service.');
    const formData = new FormData();
    formData.append('file', fileEntry, fileEntry.name);
    formData.append('videoId', videoId);

    return this.httpClient.post(
      'http://localhost:8080/api/video/thumbnail',
      formData,
      {
        responseType: 'text',
      }
    );
  }

  public getVideo(videoId: string): Observable<Video> {
    return this.httpClient.get<Video>(
      'http://localhost:8080/api/video/' + videoId
    );
  }

  public saveVideo(videoMetadata: Video): Observable<Video> {
    return this.httpClient.put<Video>(
      'http://localhost:8080/api/video/',
      videoMetadata
    );
  }
}
