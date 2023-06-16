import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  NonNullableFormBuilder,
  UntypedFormArray,
  Validators,
  FormGroup
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { Course } from '../../model/course';
import { Lesson } from '../../model/lesson';
import { CoursesService } from '../../services/courses.service';
import { ErrorDialogComponent } from './../../../shared/components/error-dialog/error-dialog.component';
import { FormUtilsService } from 'src/app/shared/service/form-utils.service';



@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent  implements OnInit{

  msg: string = '';
  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog,
    private location: Location,
    private route: ActivatedRoute,

    public formUtils: FormUtilsService
  ){
    //this.form
  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course'];

    this.form= this.formBuilder.group({
      _id: [course._id],
      name: [course.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100)
        ]],
      category: [course.category,
      [
        Validators.required,
      ]],
      lessons: this.formBuilder.array(this.retriveLessons(course), Validators.required)
    });
  }

  private retriveLessons(course: Course) {
    const lessons = [];
    if (course?.lessons){
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)));
    }else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  private createLesson(lesson: Lesson = { _id: '', name: '', youtubeUrl: '' }) {
    return this.formBuilder.group({
      _id: [lesson._id],
      name: [
        lesson.name,
        [Validators.required, Validators.minLength(5), Validators.maxLength(100)]
      ],
      youtubeUrl: [
        lesson.youtubeUrl,
        [Validators.required, Validators.minLength(10), Validators.maxLength(11)]
      ]
    });
  }

  getLessonFormArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  getErrorMessage(fieldName: string): string {
    return this.formUtils.getFieldErrorMessage(this.form, fieldName);
  }

  getLessonErrorMessage(fieldName: string, index: number) {
    return this.formUtils.getFieldFormArrayErrorMessage(
      this.form,
      'lessons',
      fieldName,
      index
    );
  }

  addLesson(): void {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  removeLesson(index: number) {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }


  onSubmit(){
    this.msg = 'O curso: '+this.form.value.name+', foi salvo com sucesso!';
    if (this.form.valid) {
      this.service.save(this.form.value as Course).subscribe({
        next: () => this.onSuccess(this.msg),
        error: () => this.onError()
      });
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }
  }

  private onSuccess(msg: string){
    this.snackBar.open(
      msg,
       '',
       {
        duration: 4000
      }
    );
    this.onCancel();
  }
  onCancel(){
    this.location.back();
  }

  private onError(){
    this.dialog.open(ErrorDialogComponent, {
      data: 'Erro ao salvar curso!'
    });
  }

}
