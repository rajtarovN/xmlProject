import { Component, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Select } from 'src/modules/shared/models/select';
import { InteresovanjeService } from 'src/modules/shared/services/interesovanje-service/interesovanje.service';

@Component({
  selector: 'app-interesovanje-form',
  templateUrl: './interesovanje-form.component.html',
  styleUrls: ['./interesovanje-form.component.scss'],
})
export class InteresovanjeFormComponent implements OnInit {
  addInteresovanjeForm: FormGroup;

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

  idZalbe: String | null = '';
  brojResenja = '';
  datum = '';
  sadrzajObrazlozenja = '';
  ime = '';
  prezime = '';
  cena = '';

  constructor(
    private fb: FormBuilder,
    private service: InteresovanjeService,
    private snackBar: MatSnackBar
  ) {
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
      ime: [null, Validators.required],
      prezime: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
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
  }

  ngOnInit(): void {}

  cancel(): void {
    this.addInteresovanjeForm.reset();
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
    let interesovanje = `<?xml version="1.0" encoding="UTF-8"?>
                        <Interesovanje
                          xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje"
                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                          xmlns:jxb="http://java.sun.com/xml/ns/jaxb"
                          xmlns:pred="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/rdf/interesovanje/predicate/"
                          xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje ../schema/interesovanje.xsd"
                          about="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje/${this.addInteresovanjeForm.value.JMBG}/${date}">
                          <Licne_informacije>
                            <Drzavljanstvo>${this.addInteresovanjeForm.value.drzavljanstva}</Drzavljanstvo>
                            <Jmbg property="pred:jmbg>${this.addInteresovanjeForm.value.Jmbg}</Jmbg>
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

    this.service
      .addInteresovanje(
        interesovanje,
        this.addInteresovanjeForm.value.JMBG,
        this.datum
      )
      .subscribe(
        (response) => {
          this.openSnackBar('interesovanje je uspesno kreirano.');
        },
        (error) => {
          this.openSnackBar('interesovanje je nije uspesno kreirano.');
        }
      );
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
