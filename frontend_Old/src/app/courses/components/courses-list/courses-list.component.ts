import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Course } from '../../model/course';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss']
})
export class CoursesListComponent {

  @Input() courses: Course[] = [];
  @Output() add: EventEmitter<boolean>= new EventEmitter(false);
  @Output() edit: EventEmitter<Course> = new EventEmitter(false);
  @Output() remove: EventEmitter<Course> = new EventEmitter(false);
  @Output() report:EventEmitter<Course>  = new EventEmitter(false);

  @Output() details: EventEmitter<Course> = new EventEmitter(false);
  readonly displayedColumns = ['name', 'category', 'actions'];

  constructor(
  ){ }

  onAdd(){
    this.add.emit(true);
  }

  onEdit(record: Course){
    this.edit.emit(record);
  }

  onRemove(record: Course){
    this.remove.emit(record);
  }
  onReport(record: Course){
    this.report.emit(record);
  }

  onDetails(record: Course) {
    this.details.emit(record);
  }
}
