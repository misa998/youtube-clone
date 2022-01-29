import { Component, Input, OnInit, SecurityContext } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css'],
})
export class VideoPlayerComponent implements OnInit {
  @Input()
  videoUrl!: string | '';

  constructor(private sanitizer: DomSanitizer) {
    console.log(this.videoUrl);
    let url = this.videoUrl;
    this.videoUrl = this.sanitizer.sanitize(
      SecurityContext.URL,
      this.sanitizer.bypassSecurityTrustUrl(url)
    ) as string;
    console.log(this.videoUrl);
  }

  ngOnInit(): void {}
}
