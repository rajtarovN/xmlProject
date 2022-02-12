import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Select } from 'src/modules/shared/models/select';
import { InteresovanjeService } from 'src/modules/shared/services/interesovanje-service/interesovanje.service';
import { UUID } from 'angular2-uuid';
import { ChildActivationStart } from '@angular/router';

@Component({
  selector: 'app-interesovanje-form',
  templateUrl: './interesovanje-form.component.html',
  styleUrls: ['./interesovanje-form.component.scss'],
})
export class InteresovanjeFormComponent implements OnInit {
  addInteresovanjeForm!: FormGroup;
  parser = new DOMParser();
  id: string = '-1';

  drzavljanstva: Select[] = [
    {
      value: 'Drzavljanin_republike_srbije',
      viewValue: 'Држављанин Републике Србије',
    },
    {
      value: 'Strani_drzavljanin_sa_boravkom_u_rs',
      viewValue: 'Страни држављанин са боравком у РС',
    },
    {
      value: 'Strani_drzavljanin_bez_boravka_u_rs',
      viewValue: 'Страни држављанин без боравка у РС',
    },
  ];

  vakcine: Select[] = [
    {
      value: 'Pfizer-BioNTech',
      viewValue: 'Pfizer-BioNTech',
    },
    {
      value: 'Sputnik',
      viewValue: 'Sputnik',
    },
    {
      value: 'Sinopharm',
      viewValue: 'Sinopharm',
    },
    {
      value: 'AstraZeneca',
      viewValue: 'AstraZeneca',
    },
    {
      value: 'Moderna',
      viewValue: 'Moderna',
    },
  ];

  constructor(
    private fb: FormBuilder,
    private service: InteresovanjeService,
    private snackBar: MatSnackBar
  ) {
    this.initialize();
  }

  ngOnInit(): void {}

  initialize() {
    const email = localStorage.getItem('email');
    if (email == null) return;
    this.service.getInteresovanje(email).subscribe({
      next: (success) => {
        let xmlDoc = this.parser.parseFromString(success, 'text/xml');
        let Drzavljanstvo = xmlDoc.getElementsByTagName('drzavljanstvo');
        let Jmbg = xmlDoc.getElementsByTagName('jmbg');
        let Ime = xmlDoc.getElementsByTagName('ime');
        let Prezime = xmlDoc.getElementsByTagName('prezime');
        let Email = xmlDoc.getElementsByTagName('email');
        let Broj_mobilnog = xmlDoc.getElementsByTagName('brojMobilnog');
        let Broj_fixsnog = xmlDoc.getElementsByTagName('brojFikcnog');
        let Davalac_krvi = xmlDoc.getElementsByTagName('davalac');
        let Lokacija_primanja_vakcine = xmlDoc.getElementsByTagName(
          'lokacijaPrimanjaVakcine'
        );
        let Proizvodjaci = xmlDoc.getElementsByTagName('proizvodjac');
        let Datum_podnosenja_interesovanja = xmlDoc.getElementsByTagName(
          'datumPodnosenjaInteresovanja'
        );
        console.log(Proizvodjaci[0].children[0].textContent);
        console.log(Proizvodjaci[2].children[0].textContent);
        var lista = [];
        for (let index = 0; index < Proizvodjaci.length - 1; index++) {
          let element = Proizvodjaci[index].children[0].textContent;
          console.log(element);
          console.log(index);
          lista.push(element);
        }

        this.addInteresovanjeForm = this.fb.group({
          drzavljanstvo: [
            { value: Drzavljanstvo, disabled: true },
            Validators.required,
          ],
          JMBG: [{ value: Jmbg, disabled: true }, [Validators.required]],
          ime: [{ value: Ime, disabled: true }, Validators.required],
          prezime: [{ value: Prezime, disabled: true }, Validators.required],
          email: [
            { value: Email, disabled: true },
            [Validators.required, Validators.email],
          ],
          mobilni: [
            { value: Broj_mobilnog, disabled: true },
            [Validators.required, Validators.pattern('[0][6][0-9]{8}')],
          ],
          fiksni: [
            { value: Broj_fixsnog, disabled: true },
            [Validators.required, Validators.pattern('[0][1][1][0-9]{7}')],
          ],
          opstina: [
            { value: Lokacija_primanja_vakcine, disabled: true },
            Validators.required,
          ],
          izabraneVakcine: [
            { value: lista, disabled: true },
            Validators.required,
          ],
          davalacKrvi: [
            { value: Boolean(Davalac_krvi), disabled: true },
            Validators.required,
          ],
        });
      },
      error: (error) => {
        this.addInteresovanjeForm = this.fb.group({
          drzavljanstvo: [null, Validators.required],
          JMBG: [
            null,
            [
              Validators.required,
              Validators.minLength(13),
              Validators.maxLength(13),
              Validators.pattern('[0-9]{13}'),
            ],
          ],
          ime: [localStorage.getItem('ime'), Validators.required],
          prezime: [localStorage.getItem('prezime'), Validators.required],
          email: [
            localStorage.getItem('email'),
            [Validators.required, Validators.email],
          ],
          mobilni: [
            null,
            [Validators.required, Validators.pattern('[0][6][0-9]{8}')],
          ],
          fiksni: [
            null,
            [Validators.required, Validators.pattern('[0][1][1][0-9]{7}')],
          ],
          opstina: [null, Validators.required],
          izabraneVakcine: [null, Validators.required],
          davalacKrvi: [null, Validators.required],
        });
      },
    });
  }

  cancel(): void {
    this.addInteresovanjeForm.reset();
  }

  delete(): void {
    this.service
      .deleteInteresovanje(this.addInteresovanjeForm.value.Jmbg)
      .subscribe({
        next: () => {
          this.openSnackBar('interesovanje je uspesno kreirano.');
        },
        error: () => {
          this.openSnackBar('interesovanje nije uspesno kreirano.');
        },
      });
  }

  send(): void {
    var d = new Date();
    var date = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
    var prozivodjaci = '';
    if (this.addInteresovanjeForm.value.izabraneVakcine.length == 5) {
      prozivodjaci = '<Proizvodjac>Bilo koja</Proizvodjac>';
    } else {
      this.addInteresovanjeForm.value.izabraneVakcine.forEach(
        (element: any) => {
          prozivodjaci += `<Proizvodjac>${element}</Proizvodjac>\n\t\t\t\t\t`;
        }
      );
    }

    console.log(this.addInteresovanjeForm.value.izabraneVakcine);
    this.id = UUID.UUID();
    let interesovanje = `<?xml version="1.0" encoding="UTF-8"?>
                        <Interesovanje
                          xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje"
                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                          xmlns:jxb="http://java.sun.com/xml/ns/jaxb"
                          xmlns:pred="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/rdf/interesovanje/predicate/"
                          xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje ../schema/interesovanje.xsd"
                          about="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje/${this.id}">
                          <Licne_informacije>
                            <Drzavljanstvo>${this.addInteresovanjeForm.value.drzavljanstvo}</Drzavljanstvo>
                            <Jmbg property="pred:jmbg">${this.addInteresovanjeForm.value.JMBG}</Jmbg>
                            <Ime property="pred:ime">${this.addInteresovanjeForm.value.ime}</Ime>
                            <Prezime property="pred:prezime">${this.addInteresovanjeForm.value.prezime}</Prezime>
                            <Kontakt>
                                <Email property="pred:email">${this.addInteresovanjeForm.value.email}</Email>
                                <Broj_mobilnog>${this.addInteresovanjeForm.value.mobilni}</Broj_mobilnog>
                                <Broj_fiksnog>${this.addInteresovanjeForm.value.fiksni}</Broj_fiksnog>
                            </Kontakt>
                            <Davalac_krvi Davalac="${this.addInteresovanjeForm.value.davalacKrvi}"/>
                          </Licne_informacije>
                          <Lokacija_primanja_vakcine property="pred:lokacija_primanja_vakcine">${this.addInteresovanjeForm.value.opstina}</Lokacija_primanja_vakcine>
                          <Proizvodjaci>
                            ${prozivodjaci}
                          </Proizvodjaci>
                          <Datum_podnosenja_interesovanja property="pred:datum_podnosenja_interesovanja">${date}</Datum_podnosenja_interesovanja>
                       </Interesovanje>`;

    console.log(interesovanje);

    this.service.addInteresovanje(interesovanje, this.id).subscribe({
      next: () => {
        this.openSnackBar('interesovanje je uspesno kreirano.');
      },
      error: () => {
        this.openSnackBar('interesovanje nije uspesno kreirano.');
      },
    });
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
