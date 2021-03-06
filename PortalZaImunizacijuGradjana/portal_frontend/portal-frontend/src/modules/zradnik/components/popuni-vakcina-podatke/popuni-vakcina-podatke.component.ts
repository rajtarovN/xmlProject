import { DatePipe } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { MatSelect } from '@angular/material/select';
import { ToastrService } from 'ngx-toastr';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
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
  @Input() odabranaVakcina = '';
  @ViewChild('nazivvakcine') matSelect!: MatSelect;
  vakcPodaciForm: FormGroup;
  trajneKontraindikacije = false;
  minDate: Date;
  ekstremitet: string;
  naziviVakcina: any[] = [
    { index: 0, name: 'Pfizer-BioNTech' },
    { index: 1, name: 'Sputnik V' },
    { index: 2, name: 'Sinopharm' },
    { index: 3, name: 'AstraZeneca' },
    { index: 4, name: 'Moderna' },
  ];
  nazivVakcine: string;

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    public datepipe: DatePipe,
    private sagService: SaglasnostService
  ) {
    this.vakcPodaciForm = this.fb.group({
      serijaVakcine: [null, Validators.required],
      proizvodjac: [null, Validators.required],
      reakcija: [null],
      dijagnoza: [null],
      datum: [new Date()],
    });
    this.minDate = new Date();
    this.ekstremitet = '';
    this.nazivVakcine = '';
  }

  ngOnInit(): void {
    this.setOdabranaVakcina();
  }

  setOdabranaVakcina() {
    if (this.odabranaVakcina != 'all') {
      this.naziviVakcina = [{ index: 0, name: this.odabranaVakcina }];
    } else {
      this.naziviVakcina = [];
      this.sagService.getAvailableVacs().subscribe(
        (response) => {
          const vacs = String(response);
          if(vacs === "none"){
            this.toastr.error("Nema dostupnih vakcina!");
          }else{
            const trimed = vacs.substring(0, vacs.length-1);
            const splitted = trimed.split(',');
            splitted.forEach((vacx, i)=>{
              this.naziviVakcina.push({index: i, name: vacx});
            });
          }          
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }

  ngAfterViewInit() {
    this.matSelect.valueChange.subscribe((index) => {
      this.nazivVakcine = index;
    });
  }

  cancel() {
    this.onPodaciOVakciniClose.emit(true);
  }

  radioChange(event: MatRadioChange) {
    this.ekstremitet = event.value;
  }

  confirm() {
    if (this.ekstremitet != '' && this.nazivVakcine != '') {
      const temp: EvidentiraneVakcine = {
        nazivVakcine: this.nazivVakcine,
        serijaVakcine: this.vakcPodaciForm.value.serijaVakcine,
        proizvodjac: this.vakcPodaciForm.value.proizvodjac,
        nezeljenaReakcija:
          this.vakcPodaciForm.value.reakcija == null
            ? ''
            : this.vakcPodaciForm.value.reakcija,
        nacinDavanja: 'IM',
        datumDavanja: String(
          this.datepipe.transform(this.minDate, 'yyyy-MM-dd')
        ),
        ekstremitet: this.ekstremitet,
        odlukaKomisije: this.trajneKontraindikacije ? 'Da' : '',
        datumUtvrdjivanja:
          this.vakcPodaciForm.value.dijagnoza == null
            ? ''
            : String(
                this.datepipe.transform(
                  this.vakcPodaciForm.value.datum,
                  'yyyy-MM-dd'
                )
              ),
        dijagnoza:
          this.vakcPodaciForm.value.dijagnoza == null
            ? ''
            : this.vakcPodaciForm.value.dijagnoza,
      };
      this.onPodaciOVakciniSacuvaj.emit(temp);
    } else {
      this.toastr.error('Sva obavezna polja moraju biti popunjena!');
    }
  }
}
