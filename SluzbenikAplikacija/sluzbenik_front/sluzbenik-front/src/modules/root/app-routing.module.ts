import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PotvrdaViewComponent } from '../sluzbenik/components/potvrda-view/potvrda-view.component';
import { SaglasnostViewComponent } from '../sluzbenik/components/saglasnost-view/saglasnost-view.component';
import { SertifikatViewComponent } from '../sluzbenik/components/sertifikat-view/sertifikat-view.component';
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
      {
        path: 'obrazac_saglasnosti_za_imunizaciju/:id',
        component: SaglasnostViewComponent,
      },
      {
        path: 'digitalni_zeleni_sertifikat/:id',
        component: SertifikatViewComponent,
      },
      {
        path: 'potvrda_o_vakcinaciji/:id',
        component: PotvrdaViewComponent,
      },
    ],
  },
  {
    path: '',
    redirectTo: '/sluzbenik/auth/login',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
