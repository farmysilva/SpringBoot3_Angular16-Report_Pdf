import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../../services/courses.service';
import { Course } from '../../model/course';
import { ActivatedRoute } from '@angular/router';
import { Lesson } from '../../model/lesson';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent  implements OnInit{

  msg: string = '';
  form!: FormGroup;

  /*form= this.formBuilder.group({
    _id: [' '],
    name: [
      ' ',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(200)

      ]],
    category: [' ',
    [
      Validators.required,
    ]]

  });*/

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
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
          Validators.minLength(3),
          Validators.maxLength(200)
        ]],
      category: [course.category,
      [
        Validators.required,
      ]],
      lessons: this.formBuilder.array(this.retriveLessons(course))
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

  private createLesson(lesson: Lesson = {id: '', name: '',  urlYoutube: ''}){
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name],
      urlYoutube: [lesson.urlYoutube]
    });
  }

  onSubmit(){
    this.msg = 'O curso: '+this.form.value.name+', foi salvo com sucesso!';
    this.service.save(this.form.value)
    .subscribe(result => this.onSucess(this.msg), error => this.onError());
  }

  onSucess(msg: string){
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
    this.snackBar.open('Erro ao salvar curso!', '', {duration: 4000});
  }

  getErrorMessage(fieldName: string){
    const field = this.form.get(fieldName);
    if (field?.hasError('required')) {
      return 'Campo obrigatório!';
    }

    if (field?.hasError('minlength')) {
      const requiredLength = field.errors? field.errors['minlength']['requiredLength']: 3;
      return `O mínimo de caracteres é de: ${requiredLength} caracteres!`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength = field.errors? field.errors['maxlength']['requiredLength']: 200;
      return `O máximo de caracteres foi excedido em: ${requiredLength} caracteres!`;
    }

    return 'Campo inválido!';

  }

}
