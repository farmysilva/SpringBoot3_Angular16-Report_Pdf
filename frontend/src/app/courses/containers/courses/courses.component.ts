import { AsyncPipe, NgIf } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, of, tap } from 'rxjs';

import { ConfirmationDialogComponent } from '../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { CoursesListComponent } from '../../components/courses-list/courses-list.component';
import { Course } from '../../model/course';
import { CoursePage } from '../../model/course-page';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
  standalone: true,
  imports: [
    MatCardModule,
    MatToolbarModule,
    NgIf,
    CoursesListComponent,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatIconModule,
    MatPaginatorModule,
    AsyncPipe
  ]
})
export class CoursesComponent implements OnInit {
  courses$: Observable<CoursePage> | null = null;

  pageIndex = 0;
  pageSize = 10;

  msg: string = "";

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.courses$ = this.coursesService
      .list(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(() => {
          this.onError('Error loading courses.');
          return of({ courses: [], totalElements: 0 } as unknown as CoursePage);
        })
      );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
      width: '350px',
      enterAnimationDuration: '1200ms',
      exitAnimationDuration: '1200ms'
    });
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onEdit(course: Course) {
    this.router.navigate(['edit', course._id], { relativeTo: this.route });
  }

  onView(course: Course) {
    this.router.navigate(['view', course._id], { relativeTo: this.route });
  }

  onReport(course: Course){
    //this.coursesService.reportPdf(course._id);
    this.router.navigate(['report', course._id], {relativeTo: this.route});
  }

  onRemove(course: Course) {
    this.msg = 'Tem certeza que deseja remover o curso ' + course.name + '?'
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data:  this.msg,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.remove(course._id).subscribe({
          next: () => {
            this.msg = 'O curso ' + course.name + ', foi removido com sucesso!'
            this.snackBar.open(this.msg, 'X', {
              duration: 4000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            })
          },
          error: () => {
            this.msg = 'Erro ao tentar remover o curso ' + course.name + '!'
            this.onError(this.msg)
          }
        });
      }
    });
    this.refresh();
  }
}
