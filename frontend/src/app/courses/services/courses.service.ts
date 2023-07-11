import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, delay, of, tap } from 'rxjs';

import { Course } from '../model/course';
import { CoursePage } from '../model/course-page';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private readonly API = '/api/courses';
  private urlTemp: String = ' ';
  private cache: Course[] = [];
  private blob: any;
  constructor(private http: HttpClient) { }

  reportPdf1(id: string) {
    return this.http.get(`${this.API}/reportById/${id}`, {
      headers: {
        'Accept': 'application/pdf',
        'Content-Type': 'application/pdf',
      }, observe: 'response', responseType: 'blob'
    })
      .pipe(first())
      .subscribe(res => {
        let blob: Blob = res.body as Blob;
        console.log(blob);
        let url = window.URL.createObjectURL(blob);
        this.urlTemp = url;
        console.log(this.urlTemp);
      });
  }

  reportPdf(id: string) {
    return this.http.get(`${this.API}/reportById/${id}`, {
      headers: {
        'Accept': 'application/pdf',
        'Content-Type': 'application/pdf',
      },  observe: 'response', responseType: 'blob'
    })
      .pipe(first()).subscribe({
        next: (response: any) => {
          let fileName = 'file';
          const contentDisposition = response.headers.get('Content-Disposition');
          if (contentDisposition) {
            const fileNameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
            const matches = fileNameRegex.exec(contentDisposition);
            if (matches != null && matches[1]) {
              fileName = matches[1].replace(/['"]/g, '');
            }
          }
          const fileContent = response.body;
          this.blob = new Blob([fileContent], { type: 'application/pdf' });
          console.log(this.blob);
          saveAs(this.blob, fileName);          
          this.urlTemp = window.URL.createObjectURL(this.blob);
          //let url = window.URL.createObjectURL(this.blob);
          //window.open(url);
        },
        error: (error) => {
          console.log(error);
        }
      });
  }

  returnUrlTemp(){
    return this.urlTemp;
  }

  list(page = 0, pageSize = 10) {
    return this.http.get<CoursePage>(this.API, { params: { page, pageSize } }).pipe(
      first(),
      delay(2000),
      tap(data => (this.cache = data.courses))
    );
  }

  loadById(id: string) {
    if (this.cache.length > 0) {
      const record = this.cache.find(course => `${course._id}` === `${id}`);
      return record != null ? of(record) : this.getById(id);
    }
    return this.getById(id);
  }

  private getById(id: string) {
    return this.http.get<Course>(`${this.API}/${id}`).pipe(first());
  }

  save(record: Partial<Course>) {
    if (record._id) {
      return this.update(record);
    }
    return this.create(record);
  }

  private update(record: Partial<Course>) {
    return this.http.put<Course>(`${this.API}/${record._id}`, record).pipe(first());
  }

  private create(record: Partial<Course>) {
    return this.http.post<Course>(this.API, record).pipe(first());
  }

  remove(id: string) {
    return this.http.delete<Course>(`${this.API}/${id}`).pipe(first());
  }

}
