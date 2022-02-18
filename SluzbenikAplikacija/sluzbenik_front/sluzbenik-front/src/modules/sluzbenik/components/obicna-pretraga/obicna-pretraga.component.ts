import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatSelect } from '@angular/material/select';
import { ToastrService } from 'ngx-toastr';
import { Information } from 'src/modules/shared/models/information';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';
import { SertifikatService } from '../../services/sertifikat-service/sertifikat.service';

@Component({
  selector: 'app-obicna-pretraga',
  templateUrl: './obicna-pretraga.component.html',
  styleUrls: ['./obicna-pretraga.component.scss'],
})
export class ObicnaPretragaComponent implements OnInit {
  @ViewChild('selektor') matSelect!: MatSelect;
  pretragaForm: FormGroup;
  parser = new DOMParser();
  selected: string = 'zahtev';

  dokument: String = '1';
  documents: Array<Information> = [];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private saglasnostService: SaglasnostService,
    private sertifikatService: SertifikatService,
    private potvrdaService: PotvrdeService
  ) {}

  ngOnInit(): void {
    this.pretragaForm = this.fb.group({
      searchTerm: new FormControl(''),
    });

    this.getAllSaglasnosti();
  }

  ngAfterViewInit() {
    this.matSelect.valueChange.subscribe((value) => {
      this.dokument = value;
      if (this.dokument === '1') {
        this.getAllSaglasnosti();
      } else if (this.dokument === '2') {
        this.getAllSertifikati();
      } else if (this.dokument === '3') {
        this.getAllPotvrde();
      }
    });
  }

  getAllSaglasnosti(): void {
    this.documents = [];
    this.saglasnostService.getAll().subscribe({
      next: (success) => {
        this.parseIdXml(success, 'saglasnost');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getAllSertifikati(): void {
    this.documents = [];
    this.sertifikatService.getAll().subscribe({
      next: (success) => {
        this.parseIdXml(success, 'sertifikat');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getAllPotvrde(): void {
    this.documents = [];
    this.potvrdaService.getAll().subscribe({
      next: (success) => {
        this.parseIdXml(success, 'potvrda');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // Parse IdentificationDTO
  parseIdXml(doc: string, tip: string) {
    let xmlDoc = this.parser.parseFromString(doc, 'text/xml');
    let saglasnosti = xmlDoc.getElementsByTagName('ids')[0].childNodes;
    for (let i = 0; i < saglasnosti.length; i++) {
      console.log(saglasnosti[i].textContent);
      this.documents.push({
        url: saglasnosti[i].childNodes[0].textContent,
        open: false,
        type: tip,
        referencedBy: [],
      });
      this.getReference(saglasnosti[i].childNodes[0].textContent, i);
    }
  }

  getReference(url: String | null, index: number) {
    let references: (string | null)[] = [];
    if (url === null) return;
    var lista = url.split('/');
    var id = lista[lista.length - 1];

    if (this.dokument === '1') {
      this.saglasnostService.getAllRefs(id).subscribe({
        next: (success) => {
          let xmlDoc = this.parser.parseFromString(success, 'text/xml');
          let refs = xmlDoc.getElementsByTagName('ids')[0].childNodes;
          console.log(refs);
          for (let i = 0; i < refs.length; i++) {
            console.log(refs[i].textContent);
            this.documents[index].referencedBy.push(refs[i].textContent);
          }
          return references;
        },
        error: (error) => {
          console.log(error);
        },
      });
    } else if (this.dokument === '2') {
      this.sertifikatService.getAllRefs(id).subscribe({
        next: (success) => {
          let xmlDoc = this.parser.parseFromString(success, 'text/xml');
          let refs = xmlDoc.getElementsByTagName('ids')[0].childNodes;

          for (let i = 0; i < refs.length; i++) {
            console.log(refs[i].textContent);
            this.documents[index].referencedBy.push(refs[i].textContent);
          }
          return references;
        },
        error: (error) => {
          console.log(error);
        },
      });
    } else {
      this.potvrdaService.getAllRefs(id).subscribe({
        next: (success) => {
          let xmlDoc = this.parser.parseFromString(success, 'text/xml');
          let refs = xmlDoc.getElementsByTagName('ids')[0].childNodes;

          for (let i = 0; i < refs.length; i++) {
            console.log(refs[i].textContent);
            this.documents[index].referencedBy.push(refs[i].textContent);
          }
          return references;
        },
        error: (error) => {
          console.log(error);
        },
      });
    }
  }

  close(index: number) {
    this.documents[index].open = false;
  }

  pretrazi() {
    if (this.dokument === '1') {
      this.pretraziSaglasnosti();
    } else if (this.dokument === '2') {
      this.pretraziSertifikate();
    } else if (this.dokument === '3') {
      this.pretraziPotvrde();
    }
  }

  pretraziSaglasnosti() {
    this.documents = [];
    if (
      this.pretragaForm.value.searchTerm != null &&
      this.pretragaForm.value.searchTerm != ''
    ) {
      this.saglasnostService
        .obicnaPretraga(this.pretragaForm.value.searchTerm)
        .subscribe({
          next: (success) => {
            this.parseIdXml(success, 'saglasnost');
          },
          error: (error) => {
            console.log(error);
            this.toastr.error(error.error);
          },
        });
    }
  }

  pretraziSertifikate() {
    this.documents = [];
    if (
      this.pretragaForm.value.searchTerm != null &&
      this.pretragaForm.value.searchTerm != ''
    ) {
      this.sertifikatService
        .obicnaPretraga(this.pretragaForm.value.searchTerm)
        .subscribe({
          next: (success) => {
            this.parseIdXml(success, 'sertifikat');
          },
          error: (error) => {
            console.log(error);
            this.toastr.error(error.error);
          },
        });
    }
  }

  pretraziPotvrde() {
    this.documents = [];
    if (
      this.pretragaForm.value.searchTerm != null &&
      this.pretragaForm.value.searchTerm != ''
    ) {
      this.potvrdaService
        .obicnaPretraga(this.pretragaForm.value.searchTerm)
        .subscribe({
          next: (success) => {
            this.parseIdXml(success, 'potvrda');
          },
          error: (error) => {
            console.log(error);
            this.toastr.error(error.error);
          },
        });
    }
  }

  preuzmiPDF(url: String | null) {
    if (url === null) return;
    var lista = url.split('/');
    var id = lista[lista.length - 1];

    if (this.dokument === '1') {
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
    } else if (this.dokument === '2') {
      this.sertifikatService.getPdf(id).subscribe(
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
    } else {
      this.potvrdaService.getPdf(id).subscribe(
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

  // URL npr: http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/12345
  preuzmiXML(url: String | null) {
    if (url === null) return;
    var lista = url.split('/');
    var id = lista[lista.length - 1]; // ID DOKUMENTA npr: 12345

    console.log(url);
    console.log(id);

    if (this.dokument === '1') {
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
    } else if (this.dokument === '2') {
      this.sertifikatService.getXHtml(id).subscribe(
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
    } else {
      this.potvrdaService.getXHtml(id).subscribe(
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
}
