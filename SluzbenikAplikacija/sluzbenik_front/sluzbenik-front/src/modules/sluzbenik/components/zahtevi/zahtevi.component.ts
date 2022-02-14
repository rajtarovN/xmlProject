import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Zahtev } from 'src/modules/shared/models/zahtev';
import * as txml from 'txml';
import { ZahtevService } from '../../services/zahtev-service/zahtev.service';

@Component({
  selector: 'app-zahtevi',
  templateUrl: './zahtevi.component.html',
  styleUrls: ['./zahtevi.component.scss']
})
export class ZahteviComponent implements OnInit {
  data: any[];
  dataSource!: MatTableDataSource<any>;
  searchForm: FormGroup;
  searchString: string;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  parser = new DOMParser();
  showEvidenciju: boolean;
  showPotvrdu: boolean;
  selectedEmail: string;
  selectedBrojSaglasnosti: string;

  displayedColumns: string[] = [
    'JMBG/EBS',
    'Ime',
    'Prezime',
    'Datum',
    'Status',
    'Razlog podnosenja',
    'Odbij',
    'Prihvati',
  ];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private zahtevService: ZahtevService
  ) {
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.searchString = '';
    this.showEvidenciju = false;
    this.showPotvrdu = false;
    this.selectedEmail = '';
    this.selectedBrojSaglasnosti = '';
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.zahtevService.getZahteve('all').subscribe(
      (response) => {
        
          let obj: any = txml.parse(response);
          let list: Array<Zahtev> = [];
          obj[0].children.forEach((element: any) => {
            const temp: Zahtev = {
              brojZahteva: String(element.children[0].children[0]),
              ime: String(element.children[1].children[0]),
              prezime: String(element.children[2].children[0]),
              jmbgEbs: String(element.children[3].children[0]),
              datumPodnosenja: String(element.children[4].children[0]),
              status: String(element.children[5].children[0]),
              razlog: String(element.children[6].children[0]),
            };
            list.push(temp);
            
          });
          this.setData(list);
        
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  search() {
    this.searchString =
      this.searchForm.value.search != null && this.searchForm.value.search != ""
        ? this.searchForm.value.search
        : 'all';
    this.zahtevService
      .getZahteve(this.searchString)
      .subscribe(
        (response) => {
            let obj: any = txml.parse(response);
            let list: Array<Zahtev> = [];
            obj[0].children.forEach((element: any) => {
              const temp: Zahtev = {
                brojZahteva: String(element.children[0].children[0]),
                ime: String(element.children[1].children[0]),
                prezime: String(element.children[2].children[0]),
                jmbgEbs: String(element.children[3].children[0]),
                datumPodnosenja: String(element.children[4].children[0]),
                status: String(element.children[5].children[0]),
                razlog: String(element.children[6].children[0]),
              };
              list.push(temp);
              this.data.push(temp);
            });
            this.setData(list);
          
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
  }

  setData(data: any[]) {
    this.data = data;
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

  odbijZahtev(brojZahteva: string){}

  prihvatiZahtev(brojZahteva: string){}

  openRazlog(razloh: string){}
}

