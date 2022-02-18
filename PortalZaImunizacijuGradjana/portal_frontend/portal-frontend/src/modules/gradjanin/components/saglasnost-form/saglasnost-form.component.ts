import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Select } from 'src/modules/shared/models/select';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
import {
  radniStatusSelect,
  zanimanjeZaposlenogSelect,
  drzavljanstvoSelect,
} from './saglasnost-constants';

@Component({
  selector: 'app-saglasnost-form',
  templateUrl: './saglasnost-form.component.html',
  styleUrls: ['./saglasnost-form.component.scss'],
})
export class SaglasnostFormComponent implements OnInit {
  addSaglasnostForm!: FormGroup;
  parser = new DOMParser();
  id: any = '-1';
  datum: any = null;
  vreme: any = null;
  odabraneVakcineSelect: Select[] = [];

  radniStatusSelect = radniStatusSelect;
  zanimanjeZaposlenogSelect = zanimanjeZaposlenogSelect;
  drzavljanstvoSelect = drzavljanstvoSelect;

  odabranoDrzavljanstvo = '1';

  constructor(
    private fb: FormBuilder,
    private service: SaglasnostService,
    private snackBar: MatSnackBar
  ) {
    this.initialize();
  }

  ngOnInit(): void {}

  vakcineIzSaglasnosti: any = '';

  initialize() {
    const email = localStorage.getItem('email');
    if (email !== null) {
      this.service.getSaglasnostGradjanin(email).subscribe({
        next: (success) => {
          let xmlDoc = this.parser.parseFromString(success, 'text/xml');

          if (xmlDoc.getElementsByTagName('ns2:Ime_roditelja')[0] != null)
            return;
          this.id = xmlDoc
            .getElementsByTagName('ns2:Saglasnost')[0]
            .getAttribute('about')
            ?.split('/')[5];

          this.vakcineIzSaglasnosti = xmlDoc
            .getElementsByTagName('ns2:Saglasnost')[0]
            .getAttribute('Odabrane_vakcine')
            ?.split(',');
          if (this.vakcineIzSaglasnosti)
            for (let vakcina of this.vakcineIzSaglasnosti) {
              this.odabraneVakcineSelect.push({
                value: vakcina,
                viewValue: vakcina,
              });
            }

          this.datum = xmlDoc.getElementsByTagName('ns2:Datum')[0].textContent;
          this.vreme = xmlDoc
            .getElementsByTagName('ns2:Saglasnost')[0]
            .getAttribute('Vreme_termina')
            ?.slice(0, 5);

          this.addSaglasnostForm = this.fb.group({
            odabranaVakcina: [null, Validators.required],
            jmbg: [
              null,
              [
                Validators.required,
                Validators.minLength(13),
                Validators.maxLength(13),
                Validators.pattern('[0-9]{13}'),
              ],
            ],
            stranoDrzavljanstvo: [null],
            stranoDrzavljanstvoId: [null],
            prezime: [
              { value: localStorage.getItem('prezime'), disabled: true },
              Validators.required,
            ],
            ime: [
              { value: localStorage.getItem('ime'), disabled: true },
              Validators.required,
            ],
            imeRoditelja: [null, Validators.required],
            pol: [
              { value: localStorage.getItem('pol'), disabled: true },
              Validators.required,
            ],
            datumRodjenja: [
              { value: localStorage.getItem('rodjendan'), disabled: true },
              Validators.required,
            ],
            mestoRodjenja: [null, Validators.required],
            adresa: [null, Validators.required],
            broj: [null, Validators.required],
            mesto: [null, Validators.required],
            grad: [null, Validators.required],
            mobilni: [null, Validators.required],
            fiksni: [null, Validators.required],
            email: [
              { value: localStorage.getItem('email'), disabled: true },
              [Validators.required, Validators.email],
            ],
            radniStatus: [null, Validators.required],
            zanimanjeZaposlenog: [{ value: null }],
            socijalnaZastita: [null, Validators.required],
            nazivSedista: [null, Validators.required],
            opstinaSedista: [null, Validators.required],
            saglasan: [null, Validators.required],
            lekar: [null, Validators.required],
          });
        },
        error: () => {
          this.openSnackBar('Тренутно нема сагласности за попунјаванје.');
        },
      });
    }
  }

  setFormValidators(): void {
    if (this.odabranoDrzavljanstvo == '1') {
      this.addSaglasnostForm.get('jmbg')?.setValidators(null);
      this.addSaglasnostForm
        .get('jmbg')
        ?.setValidators([
          Validators.required,
          Validators.minLength(13),
          Validators.maxLength(13),
          Validators.pattern('[0-9]{13}'),
        ]);
      this.addSaglasnostForm.get('stranoDrzavljanstvo')?.setValidators(null);
      this.addSaglasnostForm.get('stranoDrzavljanstvoId')?.setValidators(null);
    } else {
      this.addSaglasnostForm.get('jmbg')?.setValidators(null);
      this.addSaglasnostForm
        .get('stranoDrzavljanstvo')
        ?.setValidators([Validators.required]);
      this.addSaglasnostForm
        .get('stranoDrzavljanstvoId')
        ?.setValidators([Validators.required]);
    }
    this.addSaglasnostForm.get('jmbg')?.updateValueAndValidity();
    this.addSaglasnostForm.get('stranoDrzavljanstvo')?.updateValueAndValidity();
    this.addSaglasnostForm
      .get('stranoDrzavljanstvoId')
      ?.updateValueAndValidity();
  }

  checkIfZaposlen() {
    if (this.addSaglasnostForm.value.radniStatus !== 'Zaposlen') {
      this.addSaglasnostForm.get('zanimanjeZaposlenog')?.reset();
    }
  }

  cancel(): void {
    this.addSaglasnostForm.reset();
    this.setFormValidators();
  }

  send(): void {
    var drzavljanstvoTxt = '';

    if (this.odabranoDrzavljanstvo === '1') {
      drzavljanstvoTxt = `<Drzavljanin_srbije>
                            \t<Jmbg property="pred:jmbg">${this.addSaglasnostForm.value.jmbg}</Jmbg>
                          \t\t</Drzavljanin_srbije>`;
    } else {
      drzavljanstvoTxt = `<Strani_drzavljanin>
                            \t\t<Drzavljanstvo>${this.addSaglasnostForm.value.stranoDrzavljanstvo}</Drzavljanstvo>
                            \t\t<Identifikacija property="pred:identifikacija">${this.addSaglasnostForm.value.stranoDrzavljanstvoId}</Identifikacija>
                          \t</Strani_drzavljanin>`;
    }

    var zanimanjeTxt = '';
    if (this.addSaglasnostForm.value.radniStatus !== 'Zaposlen') {
      zanimanjeTxt = `<Zanimanje_zaposlenog xsi:nil="true"/>`;
    } else {
      zanimanjeTxt = `<Zanimanje_zaposlenog>${this.addSaglasnostForm.value.zanimanjeZaposlenog}</Zanimanje_zaposlenog>`;
    }

    var saglasnost = `<?xml version="1.0" encoding="UTF-8"?>    
                      <Saglasnost xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju"
                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                          xmlns:pred="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/rdf/obrazac_saglasnosti_za_imunizaciju/predicate/"
                          xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju ../schema/saglasnost.xsd"
                          about="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/${
                            this.id
                          }"
                          Broj_saglasnosti="${this.id}"
                          Vreme_termina="${this.vreme + ':00'}"
                          Odabrane_vakcine="${
                            this.addSaglasnostForm.value.odabranaVakcina
                          }">
                          <Pacijent>
                              ${drzavljanstvoTxt}
                              <Licni_podaci>
                                  <Prezime property="pred:prezime">${localStorage.getItem(
                                    'prezime'
                                  )}</Prezime>
                                  <Ime property="pred:ime">${localStorage.getItem(
                                    'ime'
                                  )}</Ime>
                                  <Ime_roditelja>${
                                    this.addSaglasnostForm.value.imeRoditelja
                                  }</Ime_roditelja>
                                  <Pol>${
                                    localStorage.getItem('pol') === 'M'
                                      ? 'Muski'
                                      : 'Zenski'
                                  }</Pol>
                                  <Datum_rodjenja>${localStorage.getItem(
                                    'rodjendan'
                                  )}</Datum_rodjenja>
                                  <Mesto_rodjenja>${
                                    this.addSaglasnostForm.value.mestoRodjenja
                                  }</Mesto_rodjenja>
                                  <Adresa>
                                      <Ulica>${
                                        this.addSaglasnostForm.value.adresa
                                      }</Ulica>
                                      <Broj>${
                                        this.addSaglasnostForm.value.broj
                                      }</Broj>
                                      <Mesto>${
                                        this.addSaglasnostForm.value.mesto
                                      }</Mesto>
                                      <Grad>${
                                        this.addSaglasnostForm.value.grad
                                      }</Grad>
                                  </Adresa>
                                  <Kontakt_informacije>
                                      <Fiksni_telefon>${
                                        this.addSaglasnostForm.value.fiksni
                                      }</Fiksni_telefon>
                                      <Mobilni_telefon>${
                                        this.addSaglasnostForm.value.mobilni
                                      }</Mobilni_telefon>
                                      <Email>${localStorage.getItem(
                                        'email'
                                      )}</Email>
                                  </Kontakt_informacije>
                                  <Radni_status>${
                                    this.addSaglasnostForm.value.radniStatus
                                  }</Radni_status>
                                  ${zanimanjeTxt}
                                  <Socijalna_zastita Korisnik="${
                                    this.addSaglasnostForm.value
                                      .socijalnaZastita
                                  }">
                                      <Naziv_sedista>${
                                        this.addSaglasnostForm.value
                                          .nazivSedista
                                      }</Naziv_sedista>
                                      <Opstina_sedista>${
                                        this.addSaglasnostForm.value
                                          .opstinaSedista
                                      }</Opstina_sedista>
                                  </Socijalna_zastita>
                              </Licni_podaci>
                              <Saglasnost_pacijenta Saglasan="${
                                this.addSaglasnostForm.value.saglasan
                              }">
                                <Naziv_imunoloskog_lekara>
                                  ${this.addSaglasnostForm.value.lekar}
                                </Naziv_imunoloskog_lekara>
                              </Saglasnost_pacijenta>
                              <Datum property="pred:datum_termina">${
                                this.datum
                              }</Datum>
                          </Pacijent>
                          <Evidencija_o_vakcinaciji xsi:nil="true"/>
                      </Saglasnost>`;

    console.log(saglasnost, this.vakcineIzSaglasnosti);

    this.service
      .saveSaglasnostGradjanin(saglasnost, this.id, this.vakcineIzSaglasnosti)
      .subscribe({
        next: () => {
          this.openSnackBar('Сагласност је успешно креирана.');
          this.id = -1;
          this.initialize();
        },
        error: () => {
          this.openSnackBar('Сагласност није успешно креирана.');
        },
      });
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Ok', {
      duration: 4000,
    });
  }
}
