import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, first, map, of, tap } from 'rxjs';

import { Course } from '../model/course';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  //private readonly API = '/assets/courses.json';
  private readonly API = 'api/courses';
  private cache: Course[] = [];

  constructor(private htttpClient: HttpClient) { }

  list() {
    return this.htttpClient.get<CoursePage>(this.API).
      pipe(
        first(),
        delay(2000),
        map(data => data.courses),
        tap(data => (this.cache = data))
      );
  }

  save(record: Partial<Course>) {
    if (record._id) {
      return this.update(record);
    }
    return this.create(record);
  }

  loadById(id: string) {
    if (this.cache.length > 0) {
      const record = this.cache.find(course => `${course._id}` === `${id}`);
      return record != null ? of(record) : this.getById(id);
    }
    return this.getById(id);
  }

  private getById(id: string) {
    return this.htttpClient.get<Course>(`${this.API}/${id}`).pipe(first());
  }

  private create(courseRecord: Partial<Course>){
    return this.htttpClient.post<Course[]>(this.API, courseRecord).
    pipe(
      first()
    );
  }


  private update (courseRecord: Partial<Course>){
    return this.htttpClient.put<Course[]>(`${this.API}/${courseRecord._id}`, courseRecord).
    pipe(
      first()
    );
  }

  remove(id: string){
    return this.htttpClient.delete<Course>(`${this.API}/${id}`).
    pipe(
      first()
    );
  }

}
