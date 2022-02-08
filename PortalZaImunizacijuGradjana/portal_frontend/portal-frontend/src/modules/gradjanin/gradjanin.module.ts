import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GradjaninHomepageComponent } from './pages/gradjanin-homepage/gradjanin-homepage.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GradjaninRoutes } from './gradjanin.routes';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../shared/shared.module';
import { SaglasnostFormComponent } from './components/saglasnost-form/saglasnost-form.component';
import { InteresovanjeFormComponent } from './components/interesovanje-form/interesovanje-form.component';

@NgModule({
  declarations: [
    GradjaninHomepageComponent,
    SaglasnostFormComponent,
    InteresovanjeFormComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(GradjaninRoutes),
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    SharedModule,
  ],
})
export class GradjaninModule {}
