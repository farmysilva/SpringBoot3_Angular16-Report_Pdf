import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';

import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';


@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  courses$: Observable<Course[]> | null = null;
  msg: string = '';
  displayedColumns = ['name', 'category', 'actions'];

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.refresh();
  }
  refresh() {
    this.courses$ = this.coursesService.list()
      .pipe(
        catchError(error => {
          this.onError('Erro de carregamento de cursos!');
          console.log(error);
          return of([])
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

  onRemove(course: Course) {
    this.msg = 'Tem certeza que deseja remover o curso ' + course.name + '!'
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: this.msg,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.remove(course._id).subscribe({
          next: (v) => {
            this.msg = 'O curso ' + course.name + ', foi removido com sucesso!'
            this.snackBar.open(this.msg, 'X', {
              duration: 4000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            })
            this.refresh();
          },
          error: (e) => {
            this.msg = 'Erro ao tentar remover o curso ' + course.name + '!'
            this.onError(this.msg)
            this.refresh()
          }
        })
      }
    });
  }


  onReport(course: Course) {
    //chamar visualisador do pdf junto com o pdf do curso.
  }

}
