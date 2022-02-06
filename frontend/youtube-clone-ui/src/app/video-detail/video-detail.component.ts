import { Component, OnInit, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css'],
})
export class VideoDetailComponent implements OnInit {
  videoUrlFetched: boolean = false;
  private videoId: string;
  videoUrl!: string;
  thumbnailUrl!: string;
  title!: string;
  description!: string;
  videoStatus!: string;
  tags!: string[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private videoService: VideoService,
    private sanitizer: DomSanitizer
  ) {
    this.videoId = this.activatedRoute.snapshot.params.videoId;
    this.videoService.getVideo(this.videoId).subscribe((data) => {
      // sanitize the url
      this.videoUrl = this.sanitizer.sanitize(
        SecurityContext.RESOURCE_URL,
        this.sanitizer.bypassSecurityTrustResourceUrl(data.videoUrl)
      ) as string;
      this.videoUrlFetched = true;
      // this.videoUrl = data.videoUrl;
      console.log('Video url sanitized.');

      this.thumbnailUrl = data.thumbnailUrl;
      this.title = data.title;
      this.description = data.description;
      this.videoStatus = data.videoStatus;
      if (data.tags !== null) this.tags = data.tags;
    });
  }

  ngOnInit(): void {}
}
