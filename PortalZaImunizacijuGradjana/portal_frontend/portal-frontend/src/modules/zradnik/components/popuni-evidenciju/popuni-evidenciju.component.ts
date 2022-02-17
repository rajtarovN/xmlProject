import { LiveAnnouncer } from '@angular/cdk/a11y';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import * as txml from 'txml';
import * as JsonToXML from 'js2xmlparser';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
import { EvidentiraneVakcine } from '../../models/evidentirane-vakcine';
import { EvidencijaVakcinacije } from '../../models/evidencija-vakcinacije';

@Component({
  selector: 'app-popuni-evidenciju',
  templateUrl: './popuni-evidenciju.component.html',
  styleUrls: ['./popuni-evidenciju.component.scss'],
})
export class PopuniEvidencijuComponent implements OnInit {
  @Input() email = '';
  @Input() brojSaglasnosti = '';
  @Input() odabranaVakcina = '';
  vakcForm: FormGroup;
  @Output() onEvidencijaClose = new EventEmitter();
  @Output() onEvidencijaSaved = new EventEmitter();
  tabelaPopunjena: boolean;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  showVakcinaPodatke: boolean;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  odlukaKomisije: string;
  datumUtvrdjivanja: string;
  dijagnoza: string;
  displayedColumns: string[] = [
    'Naziv vakcine',
    'Datum davanja vakcine',
    'Nacin davanja vakcine',
    'Ekstremitet',
    'Serija vakcine',
    'Proizvodjac',
    'Nezeljena reakcija',
  ];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService
  ) {
    this.data = [];
    this.tabelaPopunjena = false;
    this.vakcForm = this.fb.group({
      zustanova: [null, Validators.required],
      vpunkt: [null, Validators.required],
      ime: [null, Validators.required],
      prezime: [null, Validators.required],
      telefon: [null, Validators.required],
    });
    this.showVakcinaPodatke = false;
    this.odlukaKomisije = '';
    this.datumUtvrdjivanja = '';
    this.dijagnoza = '';
  }

  ngOnInit(): void {
    this.getTableElements();
  }

  getTableElements() {
    this.saglasnostService.getEvidentiranevakcine(this.email).subscribe(
      (response) => {
        if (response != 'Nema prethodno evidentiranih vakcina.') {
          let obj: any = txml.parse(response);
          let list: Array<EvidentiraneVakcine> = [];
          if (obj[0].children != []) {
            obj[0].children.forEach((element: any) => {
              const temp: EvidentiraneVakcine = {
                nazivVakcine: String(element.children[0].children[0]),
                datumDavanja: String(element.children[1].children[0]),
                nacinDavanja: String(element.children[2].children[0]),
                ekstremitet:
                  String(element.children[3].children[0]) === 'desna ruka'
                    ? 'DR'
                    : 'LR',
                serijaVakcine: String(element.children[4].children[0]),
                proizvodjac: String(element.children[5].children[0]),
                nezeljenaReakcija:
                  String(element.children[6].children[0]) ===
                  ('undefined' || '' || null)
                    ? ''
                    : String(element.children[6].children[0]),
              };
              list.push(temp);
            });
            this.data = list;
            this.setData(list);
          }
        } else {
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  setData(data: any[]) {
    this.dataSource = new MatTableDataSource<any>(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  cancel() {
    this.onEvidencijaClose.emit(true);
  }

  confirm() {
    if (
      this.vakcForm.value.zustanova === null ||
      this.vakcForm.value.vpunkt === null ||
      this.vakcForm.value.ime === null ||
      this.vakcForm.value.prezime === null ||
      this.vakcForm.value.telefon === null ||
      this.tabelaPopunjena == false
    ) {
      this.toastr.error('Sva polja moraju biti popunjena!');
    } else {
      this.data.sort((n1, n2) => n1.datumDavanja - n2.datumDavanja);
      const options = {
        format: {
          doubleQuotes: true,
        },
        declaration: {
          include: false,
        },
      };

      //set vakcine
      let listaVakcinaStringCombined = '\n';
      this.data.forEach((value) => {
        let vakcina: any = JsonToXML.parse(
          'evidentirana_vakcina',
          value,
          options
        );
        listaVakcinaStringCombined += vakcina;
      });
      var altstr = listaVakcinaStringCombined.replace(
        /<[/]evidentirana_vakcina>/gi,
        '</evidentirana_vakcina>\n'
      );
      const listaVakcinaXml: any = JsonToXML.parse(
        'lista_evidentiranih_vakcina',
        altstr,
        options
      );
      var tryal01 = listaVakcinaXml.replace(/&lt;[/]/gi, '</');
      var tryal02 = tryal01.replace(/&lt;/gi, '\t<');
      var tryal03 = tryal02.replace(
        /<[/]evidentirana_vakcina>/gi,
        '\t</evidentirana_vakcina>'
      );

      //set evidenciju
      const temp: EvidencijaVakcinacije = {
        zdravstvenaUstanova: this.vakcForm.value.zustanova,
        vakcinacijskiPunkkt: this.vakcForm.value.vpunkt,
        imeLekara: this.vakcForm.value.ime,
        prezimeLekara: this.vakcForm.value.prezime,
        telefonLekara: this.vakcForm.value.telefon,
        odlukaKomisije: this.odlukaKomisije,
        datumUtvrdjivanja: this.datumUtvrdjivanja,
        dijagnoza: this.dijagnoza,
      };
      let data: any = JsonToXML.parse(
        'evidencijaVakcinacijeDTO',
        temp,
        options
      );

      //send evidenciju then vakcine
      this.saglasnostService
        .saveEvidenciju(data, this.brojSaglasnosti)
        .subscribe(
          (response) => {
            this.toastr.success('Uspesno sacuvana evidencija o vakcinaciji!');

            this.saglasnostService
              .saveEvidentiraneVakcine(tryal03, this.brojSaglasnosti)
              .subscribe(
                (response) => {
                  this.toastr.success('Uspesno sacuvana evidentirana vakcina!');
                },
                (error) => {
                  this.toastr.error(error.error);
                }
              );
            this.onEvidencijaSaved.emit(this.brojSaglasnosti);
          },
          (error) => {
            this.toastr.error(error.error);
          }
        );
    }
  }

  onTabelaVakcina() {
    if (this.tabelaPopunjena) {
      this.toastr.error('Vec su dodati podaci o vakcini.');
    } else this.showVakcinaPodatke = true;
  }

  onPodaciOVakciniCloseClicked(item: boolean) {
    this.showVakcinaPodatke = false;
  }

  onPodaciOVakciniSacuvajClicked(item: EvidentiraneVakcine) {
    this.odlukaKomisije = String(item.odlukaKomisije);
    this.datumUtvrdjivanja = String(item.datumUtvrdjivanja);
    this.dijagnoza = String(item.dijagnoza);
    this.tabelaPopunjena = true;
    this.showVakcinaPodatke = false;
    const temp: EvidentiraneVakcine = {
      nazivVakcine: item.nazivVakcine,
      datumDavanja: item.datumDavanja,
      nacinDavanja: item.nacinDavanja,
      ekstremitet: item.ekstremitet,
      serijaVakcine: item.serijaVakcine,
      proizvodjac: item.proizvodjac,
      nezeljenaReakcija: item.nezeljenaReakcija,
    };
    this.data.push(temp);
    this.data.sort((n1, n2) => n1.datumDavanja - n2.datumDavanja);
    this.setData(this.data);
  }
}
