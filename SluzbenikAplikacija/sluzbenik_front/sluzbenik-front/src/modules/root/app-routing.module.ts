import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { SaglasnostViewComponent } from '../sluzbenik/components/saglasnost-view/saglasnost-view.component';
import { AppComponent } from './app.component';

const routes: Routes = [
  {
    path: 'sluzbenik',
    component: AppComponent,
    children: [
      {
        path: 'sluzbenik',
        loadChildren: () =>
          import('./../sluzbenik/sluzbenik.module').then(
            (m) => m.SluzbenikModule
          ),
      },
      {
        path: 'auth',
        loadChildren: () =>
          import('./../auth/auth.module').then((m) => m.AuthModule),
      },
    ],
  },
  {
    path: '',
    redirectTo: '/sluzbenik/auth/login',
    pathMatch: 'full',
  },
  {
    path: 'saglasnost/:id',
    pathMatch: 'full',
    component: SaglasnostViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'S' },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
