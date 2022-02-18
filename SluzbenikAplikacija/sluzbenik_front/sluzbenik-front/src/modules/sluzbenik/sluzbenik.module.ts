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
import { ZahteviComponent } from './components/zahtevi/zahtevi.component';
import { PrikazDokumenataComponent } from './components/prikaz-dokumenata/prikaz-dokumenata.component';
import { ArhivaDokumenataComponent } from './components/arhiva-dokumenata/arhiva-dokumenata.component';
import { RazlogZahtevaComponent } from './components/razlog-zahteva/razlog-zahteva.component';
import { OdbijZahtevComponent } from './components/odbij-zahtev/odbij-zahtev.component';
import { SaglasnostViewComponent } from './components/saglasnost-view/saglasnost-view.component';
import { SertifikatViewComponent } from './components/sertifikat-view/sertifikat-view.component';
import { PotvrdaViewComponent } from './components/potvrda-view/potvrda-view.component';
import { ObicnaPretragaComponent } from './components/obicna-pretraga/obicna-pretraga.component';
import { IzvestajComponent } from './components/izvestaj/izvestaj.component';

@NgModule({
  declarations: [
    SluzbenikHomepageComponent,
    LogoutComponent,
    InventoryFormComponent,
    RegistarGradjanaComponent,
    ZahteviComponent,
    PrikazDokumenataComponent,
    ArhivaDokumenataComponent,
    RazlogZahtevaComponent,
    OdbijZahtevComponent,
    SaglasnostViewComponent,
    SertifikatViewComponent,
    PotvrdaViewComponent,
    ObicnaPretragaComponent,
    IzvestajComponent
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
  ],
})
export class SluzbenikModule {}
