import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { SluzbenikHomepageComponent } from './pages/sluzbenik-homepage/sluzbenik-homepage.component';
import { SluzbenikRoutes } from './sluzbenik.routes';
import { LogoutComponent } from './components/logout/logout.component';
import { CommonModule } from '@angular/common';
import { InventoryFormComponent } from './components/inventory-form/inventory-form.component';
import { RegistarGradjanaComponent } from './components/registar-gradjana/registar-gradjana.component';



@NgModule({
  declarations: [
    SluzbenikHomepageComponent,
    LogoutComponent,
    InventoryFormComponent,
    RegistarGradjanaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(SluzbenikRoutes),
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
  ]
})
export class SluzbenikModule { }
