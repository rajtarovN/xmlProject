import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { ToastrService } from 'ngx-toastr';
import { EvidentiraneVakcine } from '../../models/evidentirane-vakcine';

@Component({
  selector: 'app-popuni-vakcina-podatke',
  templateUrl: './popuni-vakcina-podatke.component.html',
  styleUrls: ['./popuni-vakcina-podatke.component.scss'],
})
export class PopuniVakcinaPodatkeComponent implements OnInit {
  @Output() onPodaciOVakciniClose = new EventEmitter();
  @Output() onPodaciOVakciniSacuvaj = new EventEmitter();
  @Input() brojSaglasnosti = '';
  vakcPodaciForm: FormGroup;
  trajneKontraindikacije = false;
  minDate: Date;
  ekstremitet: string;

  constructor(private fb: FormBuilder, private toastr: ToastrService, public datepipe: DatePipe) {
    this.vakcPodaciForm = this.fb.group({
      nazivVakcine: [null, Validators.required],
      serijaVakcine: [null, Validators.required],
      proizvodjac: [null, Validators.required],
      reakcija: [null],
      dijagnoza: [null],
      datum: [new Date()],
    });
    this.minDate = new Date();
    this.ekstremitet = '';
  }

  ngOnInit(): void {}

  cancel() {
    this.onPodaciOVakciniClose.emit(true);
  }

  radioChange(event: MatRadioChange) {
    this.ekstremitet = event.value;
  }

  confirm() {
    if (this.ekstremitet != '') {
      const temp: EvidentiraneVakcine = {
        nazivVakcine: this.vakcPodaciForm.value.nazivVakcine,
        serijaVakcine: this.vakcPodaciForm.value.serijaVakcine,
        proizvodjac: this.vakcPodaciForm.value.proizvodjac,
        nezeljenaReakcija: this.vakcPodaciForm.value.reakcija,
        nacinDavanja: 'IM',
        datumDavanja: String(this.datepipe.transform(this.minDate, 'yyyy-MM-dd')),
        ekstremitet: this.ekstremitet,
        odlukaKomisije: (this.trajneKontraindikacije ? "Da": ""),
        datumUtvrdjivanja: String(this.datepipe.transform(this.vakcPodaciForm.value.datum, 'yyyy-MM-dd')) ,
        dijagnoza: this.vakcPodaciForm.value.dijagnoza,
      };
      this.onPodaciOVakciniSacuvaj.emit(temp);
    }else{
      this.toastr.error("Sva obavezna polja moraju biti popunjena!");
    }
  }
}
