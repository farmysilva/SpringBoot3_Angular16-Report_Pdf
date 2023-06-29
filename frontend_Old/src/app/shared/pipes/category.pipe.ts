import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(value: string): string {
    switch(value) {
      case 'front-end':
        return 'co_present';
      case 'back-end':
        return 'account_tree';
    }
    return 'co_present';
  }

}
