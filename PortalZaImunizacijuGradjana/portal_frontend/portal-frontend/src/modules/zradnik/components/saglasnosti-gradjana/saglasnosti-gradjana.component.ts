import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import * as txml from 'txml';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { SaglasnostGradjanaElement } from 'src/modules/zradnik/models/saglasnost-gradjana-element';

@Component({
  selector: 'app-saglasnosti-gradjana',
  templateUrl: './saglasnosti-gradjana.component.html',
  styleUrls: ['./saglasnosti-gradjana.component.scss']
})
export class SaglasnostiGradjanaComponent implements OnInit {
  minDate: Date;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  searchForm: FormGroup;
  searchString: string;
  searchDate: Date;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  parser = new DOMParser();
  showEvidenciju: boolean;
  evidencijaEmail: string;
  evidencijaBrojSaglasnosti: string;

  displayedColumns: string[] = ['Email', 'Ime', 'Prezime', 'Datum', "Popuni evidenciju", "Izdaj potvrdu"];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService
  ) {
    this.minDate = new Date();
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
      date: [null, Validators.required],
    });
    this.searchForm.value.date = this.minDate;
    this.searchString = "";
    this.searchDate = new Date();
    this.showEvidenciju = false;
    this.evidencijaEmail = "";
    this.evidencijaBrojSaglasnosti = "";
   }

  ngOnInit(): void {
    this.getData();
  }

  getData(){
    this.saglasnostService.searchTermine(new Date(), 'all').subscribe((response) => {
      if(response != "Nema termina po trazenim parametrima"){
        let obj: any = txml.parse(response);
        let list: Array<SaglasnostGradjanaElement> = [];
        obj[0].children.forEach((element:any )=> {
          const temp : SaglasnostGradjanaElement = {
            brojSaglasnosti: String(element.children[0].children[0]),
            ime: String(element.children[1].children[0]),
            prezime: String(element.children[2].children[0]),
            datum_termina: String(element.children[3].children[0]),
            email: String(element.children[4].children[0]),
            vakcinisan: false
          };
          list.push(temp);
        });
        this.setData(list);
      }else{
        this.toastr.info("Nema termina za danas.");
      }
    }, error => {
      this.toastr.error(error.error);
    });
  }

  search(){
    this.searchString =
      this.searchForm.value.search != null ? this.searchForm.value.search : 'all';
    this.saglasnostService.searchTermine(this.searchDate, this.searchString).subscribe((response) => {
      if(response != "Nema termina po trazenim parametrima."){
        let obj: any = txml.parse(response);
        let list: Array<SaglasnostGradjanaElement> = [];
        obj[0].children.forEach((element:any )=> {
          const temp : SaglasnostGradjanaElement = {
            brojSaglasnosti: String(element.children[0].children[0]),
            ime: String(element.children[1].children[0]),
            prezime: String(element.children[2].children[0]),
            datum_termina: String(element.children[3].children[0]),
            email: String(element.children[4].children[0]),
            vakcinisan: false
          };
          list.push(temp);
        });
        this.setData(list);
      }else{
        this.toastr.info(response);
      }
    }, error => {
      this.toastr.error(error.error);
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

  onDateChange( event: MatDatepickerInputEvent<Date>) {
    this.searchDate = (event.value != null ? event.value : new Date());
  }

  givePotvrdu(brojSaglasnosti: string){}

  openEvidencija(brojSaglasnosti: string, email: string){
    this.evidencijaBrojSaglasnosti = brojSaglasnosti;
    this.evidencijaEmail = email;
    this.showEvidenciju = true;
  }

  onEvidencijaCloseClicked(item: boolean){    
    this.showEvidenciju = false;
    this.evidencijaBrojSaglasnosti = "";
    this.evidencijaEmail = "";
  }

}
