import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, MatCardModule, MatIconModule, RouterLink, RouterOutlet],
  template: `
    <mat-card class="example-card">
      <mat-toolbar color="primary">
        <mat-toolbar-row>
        <mat-icon>webhook</mat-icon>
        <span class="example-spacer" style="padding-right: 10px;"></span>
        <h1 [routerLink]="['/']" style="cursor: pointer; margin-top: 15px;">Restful Java - Spring - Angular - PDF </h1>
        <span class="example-spacer" style="padding-right: 10px;"></span>
        <img mat-card-image src="../assets/god.png" alt="My Faith." style="position: absolute; right: 15px; top: 5px;">
      </mat-toolbar-row>
    </mat-toolbar>
    <router-outlet></router-outlet>
  </mat-card>
  `
})
export class AppComponent { }


