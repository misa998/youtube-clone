import { Component, OnInit, SecurityContext } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { Video } from '../Video';
import { DomSanitizer } from '@angular/platform-browser';
// import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css'],
})
export class SaveVideoDetailsComponent implements OnInit {
  saveVideoForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  videoStatus: FormControl = new FormControl('');

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];

  selectedFile!: File;
  selectedFileName: string = '';
  videoId: string = '';
  fileSelected = false;
  videoUrl!: string;
  thumbnailUrl!: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private videoService: VideoService, // private snackBar: MatSnackBar
    private sanitizer: DomSanitizer
  ) {
    // take id from the link
    this.videoId = this.activatedRoute.snapshot.params.videoId;
    // get video from the database
    this.videoService.getVideo(this.videoId).subscribe((data) => {
      // sanitize the url
      this.videoUrl = this.sanitizer.sanitize(
        SecurityContext.RESOURCE_URL,
        this.sanitizer.bypassSecurityTrustResourceUrl(data.videoUrl)
      ) as string;
      // this.videoUrl = data.videoUrl;
      console.log('Video url sanitized.');

      this.thumbnailUrl = data.thumbnailUrl;
      this.title.setValue(data.title);
      this.description.setValue(data.description);
      this.videoStatus.setValue(data.videoStatus);
      if (data.tags !== null) this.tags = data.tags;
    });

    this.saveVideoForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus,
    });
  }

  ngOnInit(): void {}

  add(event: MatChipInputEvent): void {
    console.log('Adding chip.');
    const value = (event.value || '').trim();

    if (value) {
      this.tags.push(value);
      console.log('Added chip.');
    } else {
      console.log('Value empty.');
    }

    event.chipInput!.clear();
  }

  remove(value: string): void {
    const index = this.tags.indexOf(value);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onFileSelected($event: Event): void {
    // @ts-ignore
    this.selectedFile = $event.target.files[0];
    this.selectedFileName = this.selectedFile.name;
    this.fileSelected = true;
    console.log('File selected.');
  }

  onUpload(): void {
    this.videoService
      .uploadThumnail(this.selectedFile, this.videoId)
      .subscribe((data) => {
        console.log('data: ' + data + ' Thumbnail uploaded.');

        // this.snackBar.open('Thumbnail upload successful', 'OK');
      });
  }

  saveVideo() {
    const videoMetadata: Video = {
      id: this.videoId,
      title: this.saveVideoForm.get('title')?.value,
      description: this.saveVideoForm.get('description')?.value,
      tags: this.tags,
      videoStatus: this.saveVideoForm.get('videoStatus')?.value,
      videoUrl: this.videoUrl,
      thumbnailUrl: this.thumbnailUrl,
    };
    this.videoService.saveVideo(videoMetadata).subscribe((data) => {
      console.log('Video metadata updated.');
    });
  }
}
