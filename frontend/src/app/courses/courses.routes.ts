import { Routes } from '@angular/router';

import { CourseViewComponent } from './components/course-view/course-view.component';
import { CourseFormComponent } from './containers/course-form/course-form.component';
import { CoursesComponent } from './containers/courses/courses.component';
import { CourseResolver } from './resolver/course.resolver';
import { PdfViewerComponent } from './components/pdf-viewer/pdf-viewer.component';

export const COURSES_ROUTES: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'new', component: CourseFormComponent, resolve: { course: CourseResolver } },
  {
    path: 'edit/:id',
    component: CourseFormComponent,
    resolve: { course: CourseResolver }
  },
  {
    path: 'view/:id',
    component: CourseViewComponent,
    resolve: { course: CourseResolver }
  },
  {
    path: 'report/:id',
    component: PdfViewerComponent,
    resolve: { course: CourseResolver }
  }

];
