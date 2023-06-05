import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';


@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {

  courses$: Observable<Course[]>;
  displayedColumns = ['name', 'category', 'actions'];

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
    ) {
    this.courses$ = this.coursesService.list()
    .pipe(
      catchError( error => {
        this.onError('Erro de carregamento de cursos!');
        console.log(error);
        return of ([])
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

  ngOnInit(): void {
    //throw new Error('Method not implemented.');
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo:this.route});
  }

}