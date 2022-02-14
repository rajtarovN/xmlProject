import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Dokument } from 'src/modules/shared/models/dokument';
import * as txml from 'txml';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';

@Component({
  selector: 'app-prikaz-dokumenata',
  templateUrl: './prikaz-dokumenata.component.html',
  styleUrls: ['./prikaz-dokumenata.component.scss']
})
export class PrikazDokumenataComponent implements OnInit {
  parser = new DOMParser();
  @Input() email = '';
  @Input() tipDokumenta = '';
  vakcForm: FormGroup;
  @Output() onPrikazDokumenataClose = new EventEmitter();
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  title: string;
  data: any[];
  displayedColumns: string[] = [
    'Naziv',
    'Datum',
    'PDF',
    'XHTML',
  ];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService,
    private potvrdeService: PotvrdeService,
  ) {
    this.data = [];
    this.title = ''
  }

  ngOnInit(): void {
    this.getTableElements();
  }

  getTableElements() {
    if(this.tipDokumenta === "Saglasnosti"){
      this.title = "Obrazci Saglasnosti za Imunizaciju";
      this.setSaglasnosti();
    }
    else if(this.tipDokumenta === "Potvrde"){
      this.title = "Potvrde o Vakcinaciji";
      this.setPotvrde();
    }
    else if(this.tipDokumenta === "Sertifikati"){
      this.title = "Digitalni Sertifikati";
    }
    
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
    this.onPrikazDokumenataClose.emit(true);
  }

  setSaglasnosti(){
    this.saglasnostService.getXmlIdsByEmail(this.email).subscribe(
      (response) => {
        if(response != "Nema izdatih saglasnosti za prisutnog gradjana."){
          let obj: any = txml.parse(response);
          let list: Array<Dokument> = [];
          if (obj[0].children != []) {
            obj[0].children.forEach((element: any, index: number) => {
              console.log(element);
              const temp: Dokument = {
                id: String(element.children[0].children[0]),
                datumKreiranja: String(element.children[1].children[0]),
              };
              list.push(temp);
            });
            this.data = list;
            this.setData(list);
          }        
        }else{
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  setPotvrde(){
    this.potvrdeService.getXmlByEmail(this.email).subscribe(
      (response) => {
        if(response != "Nema izdatih potvrda za prisutnog gradjana."){
          let obj: any = txml.parse(response);
          let list: Array<Dokument> = [];
          if (obj[0].children != []) {
            obj[0].children.forEach((element: any, index: number) => {
              console.log(element);
              const temp: Dokument = {
                id: String(element.children[0].children[0]),
                datumKreiranja: String(element.children[1].children[0]),
              };
              list.push(temp);
            });
            this.data = list;
            this.setData(list);
          }        
        }else{
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  showPdf(id: string){
    if(this.tipDokumenta === "Saglasnosti"){

    }else if(this.tipDokumenta === "Potvrde"){

    }else if(this.tipDokumenta === "Sertifikati"){

    }
  }

  showXhtml(id: string){}
}
