import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';
import { AppComponent } from './app.component';

const routes: Routes = [
  
  {
    path: "portal",
    component: AppComponent,
    children: [
      {
        path: "gradjanin",
        loadChildren: () =>
          import("./../gradjanin/gradjanin.module").then((m) => m.GradjaninModule),
      },
      {
        path: "auth",
        loadChildren: () =>
          import("./../auth/auth.module").then((m) => m.AuthModule),
      },
    ],
  },
  {
    path: "",
    redirectTo: "/portal/auth/login",
    pathMatch: "full",
  },
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
