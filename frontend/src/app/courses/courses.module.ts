import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CoursesRoutingModule } from './courses-routing.module';
import { CoursesComponent } from './containers/courses/courses.component';
import { CourseFormComponent } from './containers/course-form/course-form.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CoursesListComponent } from './components/courses-list/courses-list.component';
import { CourseViewComponent } from './components/course-view/course-view.component';


@NgModule({
  declarations: [
    CoursesComponent,
    CourseFormComponent,
    CoursesListComponent,
    CourseViewComponent
  ],
  imports: [
    CommonModule,
    CoursesRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CoursesModule { }
