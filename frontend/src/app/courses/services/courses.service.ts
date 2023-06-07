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

  save(courseRecord: Partial<Course>){
    if (courseRecord._id) {
      return this.update(courseRecord);
    }
    return this.create(courseRecord);
  }

  loadById(id: string){
    return this.htttpClient.get<Course>(`${this.API}/${id}`);
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
