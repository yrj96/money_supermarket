import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { analysisRoutes } from './analysis.routes';
import { AnalysisComponent } from './analysis.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(analysisRoutes)
  ],
  declarations: [
    AnalysisComponent
  ]
})
export class AnalysisModule { }
