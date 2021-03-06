import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { ZRadnikRoutes } from './zradnik.routes';
import { ZradnikHomepageComponent } from './pages/zradnik-homepage/zradnik-homepage.component';
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';
import { SaglasnostiGradjanaComponent } from './components/saglasnosti-gradjana/saglasnosti-gradjana.component';
import { PopuniEvidencijuComponent } from './components/popuni-evidenciju/popuni-evidenciju.component';
import { PopuniVakcinaPodatkeComponent } from './components/popuni-vakcina-podatke/popuni-vakcina-podatke.component';
import { PrikazPotvrdeComponent } from './components/prikaz-potvrde/prikaz-potvrde.component';



@NgModule({
  declarations: [  
    ZradnikHomepageComponent, SaglasnostiGradjanaComponent, PopuniEvidencijuComponent, PopuniVakcinaPodatkeComponent, PrikazPotvrdeComponent
  ],
  imports: [
    
    
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(ZRadnikRoutes),
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    SharedModule
  ]
})
export class ZRadnikModule { }
