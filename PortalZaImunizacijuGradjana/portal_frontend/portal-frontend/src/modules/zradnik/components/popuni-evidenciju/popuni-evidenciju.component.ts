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
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
import { EvidentiraneVakcine } from '../../models/evidentirane-vakcine';

@Component({
  selector: 'app-popuni-evidenciju',
  templateUrl: './popuni-evidenciju.component.html',
  styleUrls: ['./popuni-evidenciju.component.scss'],
})
export class PopuniEvidencijuComponent implements OnInit {
  @Input() email = "";
  @Input() brojSaglasnosti = "";
  vakcForm: FormGroup;
  @Output() onEvidencijaClose = new EventEmitter();
  tabelaPopunjena: boolean;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  showVakcinaPodatke: boolean;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
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
  }

  ngOnInit(): void {
    this.getTableElements();
  }

  getTableElements(){
    this.saglasnostService.getEvidentiranevakcine(this.email).subscribe((response) => {
      let obj: any = txml.parse(response);
      let list: Array<EvidentiraneVakcine> = [];
      obj[0].children.forEach((element:any )=> {
        const temp : EvidentiraneVakcine = {
          nazivVakcine: String(element.children[0].children[0]),
          datumDavanja: String(element.children[1].children[0]),
          nacinDavanja: String(element.children[2].children[0]),
          ekstremitet: String(element.children[3].children[0]),
          serijaVakcine: String(element.children[4].children[0]),
          proizvodjac: String(element.children[5].children[0]),
          nezeljenaReakcija: String(element.children[6].children[0]),
        };
        list.push(temp);
      });
      this.data = list;
      this.setData(list);
    });
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

  cancel(){
    this.onEvidencijaClose.emit(true);
  }

  confirm() {
    if (
      this.vakcForm.value.zustanova === null ||
      this.vakcForm.value.vpunkt === null ||
      this.vakcForm.value.details === null ||
      this.tabelaPopunjena == false
    ) {
      this.toastr.error('Sva polja moraju biti popunjena!');
    } else {
      //TODO olja
    }
  }

  onTabelaVakcina(){
    this.showVakcinaPodatke = true;
  }

  onPodaciOVakciniCloseClicked(item: boolean){
    this.showVakcinaPodatke = false;
  }

  onSacuvajVakcinaPodatkeClicked(item: EvidentiraneVakcine){
    this.showVakcinaPodatke = false;
    this.data.push(item);
    this.setData(this.data);
  }
}
