import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LoginFormComponent } from './pages/login/login-form.component';
import { AuthRoutes } from './auth.routes';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../root/app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from '../root/material-module';
import { RegisterComponent } from './pages/register/register.component';



@NgModule({
  declarations: [LoginFormComponent, RegisterComponent],
  imports: [
    
    
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(AuthRoutes),
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
  ],
  exports:[LoginFormComponent]
})
export class AuthModule { }
