import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <div align="center">
      <h1 mat-dialog-title style="color: red">Aconteceu um Erro!</h1>
    </div>
    <div mat-dialog-content>
      {{ data }}
    </div>
    <div mat-dialog-actions align="center">
      <button mat-raised-button color="warn" mat-dialog-close cdkFocusInitial>Close</button>
    </div>
  `
})
export class ErrorDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: string) { }
}

