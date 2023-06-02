import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, first, tap } from 'rxjs';

import { Course } from '../model/course';


@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  //private readonly API = '/assets/courses.json';
  private readonly API = 'api/courses';

  constructor(private htttpClient: HttpClient) { }

  list() {
    return this.htttpClient.get<Course[]>(this.API).
      pipe(
        first(),
        delay(2000),
        tap(courses => console.log(courses))
      );
  }
}
