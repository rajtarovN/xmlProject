import { LiveAnnouncer } from '@angular/cdk/a11y';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Dokument } from '../../../shared/models/dokument';
import * as txml from 'txml';
import { PotvrdaService } from '../../../shared/services/potvrda-vakcinacije-service/potvrda.service';
import { SaglasnostService } from '../../../shared/services/saglasnost-service/saglasnost.service';
import { SertifikatService } from '../../../shared/services/sertifikat/sertifikat.service';
import { InteresovanjeService } from '../../../shared/services/interesovanje-service/interesovanje.service';

@Component({
  selector: 'app-prikaz-dokumenata',
  templateUrl: './prikaz-dokumenata.component.html',
  styleUrls: ['./prikaz-dokumenata.component.scss'],
})
export class PrikazDokumenataComponent implements OnInit {
  parser = new DOMParser();
  email: string = localStorage.getItem('email')!;
  @Input() tipDokumenta = '';
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
    'JSON',
    'RDF',
  ];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService,
    private interesovanjeService: InteresovanjeService,
    private potvrdeService: PotvrdaService,
    private sertifservice: SertifikatService
  ) {
    this.data = [];
    this.title = '';
  }

  ngOnInit(): void {
    this.getTableElements();
  }

  getTableElements() {
    if (this.tipDokumenta === 'Saglasnosti') {
      this.title = 'Obrazci Saglasnosti za Imunizaciju';
      this.setSaglasnosti();
    } else if (this.tipDokumenta === 'Potvrde') {
      this.title = 'Potvrde o Vakcinaciji';
      this.setPotvrde();
    } else if (this.tipDokumenta === 'Sertifikati') {
      this.title = 'Digitalni Zeleni Sertifikati';
      this.setSertifikate();
    }
    else if (this.tipDokumenta === 'Interesovanje') {
      this.title = 'Interesovanje';
      this.setInteresovanje();
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

  setSaglasnosti() {
    this.saglasnostService.getXmlIdsByEmail(this.email).subscribe(
      (response) => {
        if (response != 'Nema izdatih saglasnosti za prisutnog gradjana.') {
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
        } else {
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  setPotvrde() {
    this.potvrdeService.getXmlByEmail(this.email).subscribe(
      (response) => {
        if (response != 'Nema izdatih potvrda za prisutnog gradjana.') {
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
        } else {
          this.toastr.info(response);
        }
      },
      (error) => {
        console.log(error.error.text);
        this.toastr.error(error.error);
      }
    );
  }

  showPdf(id: string) {
    if (this.tipDokumenta === 'Saglasnosti') {
      this.saglasnostService.getPdf(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);

          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.pdf`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Potvrde') {
      this.potvrdeService.getPdf(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);

          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.pdf`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Sertifikati') {
      this.sertifservice.getPdf(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);

          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.pdf`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }

  setSertifikate() {
    this.interesovanjeService.getXmlByEmail(this.email).subscribe(
      (response) => {
        if (response != 'Nema izdatih sertifikata za prisutnog gradjana.') {
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
        } else {
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  setInteresovanje() {
    this.interesovanjeService.getXmlByEmail(this.email).subscribe(
      (response) => {
        if (response != 'Nema interesovanja za prisutnog gradjana.') {
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
        } else {
          this.toastr.info(response);
        }
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  showXhtml(id: string) {
    if (this.tipDokumenta === 'Saglasnosti') {
      this.saglasnostService.getXHtml(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'text/html' });
          var fileURL = URL.createObjectURL(file);
          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.html`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Potvrde') {
      this.potvrdeService.getXHtml(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'text/html' });
          var fileURL = URL.createObjectURL(file);

          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.html`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Sertifikati') {
      this.sertifservice.getXHtml(id).subscribe(
        (response) => {
          let file = new Blob([response], { type: 'text/html' });
          var fileURL = URL.createObjectURL(file);

          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = fileURL;
          a.download = `${id}.html`;
          a.click();
          window.URL.revokeObjectURL(fileURL);
          a.remove();
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }

  showJSON(documentId: string) {
    if (this.tipDokumenta === 'Saglasnosti') {
      this.saglasnostService.getJSON(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `saglasnost_${documentId}.json`, 'application/json');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Potvrde') {
      this.potvrdeService.getJSON(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `potvrda_${documentId}.json`, 'application/json');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Sertifikati') {
      this.sertifservice.getJSON(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `saglasnost_${documentId}.json`, 'application/json');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }else if (this.tipDokumenta === 'Interesovanje'){
      //TODO olja
    }
  }

  showRDF(documentId: string) {
    if (this.tipDokumenta === 'Saglasnosti') {
      this.saglasnostService.getRDF(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `saglasnost_${documentId}.rdf`, 'application/pdf');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Potvrde') {
      this.potvrdeService.getRDF(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `potvrda_${documentId}.rdf`, 'application/pdf');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    } else if (this.tipDokumenta === 'Sertifikati') {
      this.sertifservice.getRDF(documentId).subscribe(
        (response) => {
          this.doJsonRdf(response, `sertifikat_${documentId}.rdf`, 'application/pdf');
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }else if (this.tipDokumenta === 'Interesovanje'){
      //TODO olja
    }
  }

  doJsonRdf(response: any, documentNameId: string, typee: string){
    let file = new Blob([response], { type: typee });
    var fileURL = URL.createObjectURL(file);
    let a = document.createElement('a');
    document.body.appendChild(a);
    a.setAttribute('style', 'display: none');
    a.href = fileURL;
    a.download = documentNameId;
    a.click();
    window.URL.revokeObjectURL(fileURL);
    a.remove();
  }
}
