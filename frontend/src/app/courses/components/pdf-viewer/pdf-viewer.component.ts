import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute } from '@angular/router';

import { NgxExtendedPdfViewerModule, pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';


@Component({
  selector: 'app-pdf-viewer',
  templateUrl: './pdf-viewer.component.html',
  styleUrls: ['./pdf-viewer.component.scss'],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports:[
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    NgxExtendedPdfViewerModule
  ]
})
export class PdfViewerComponent implements OnInit {
  public blobUrl: any;

  course!: Course;

  constructor(private route: ActivatedRoute, private service: CoursesService,) {
    this.course = this.route.snapshot.data['course'];
    this.service.reportPdf(this.course._id);
    this.blobUrl = this.service.returnUrlTemp();
  }

  ngOnInit(): void {
    //
  }

}





