import { Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import { Information } from 'src/modules/shared/models/information';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';
import { SertifikatService } from '../../services/sertifikat-service/sertifikat.service';
import { ToastrService } from 'ngx-toastr';
import { MatSelect } from '@angular/material/select';
declare const Xonomy: any;

@Component({
  selector: 'app-arhiva-dokumenata',
  templateUrl: './arhiva-dokumenata.component.html',
  styleUrls: ['./arhiva-dokumenata.component.scss'],
})
export class ArhivaDokumenataComponent implements OnInit {
  @ViewChild('selektor') matSelect!: MatSelect;
  pretragaSaglasnostiForm: FormGroup;
  pretragaSertifikataForm: FormGroup;
  pretragaPotvrdaForm: FormGroup;

  parser = new DOMParser();

  dokument: String = '1';
  documents: Array<Information> = [];

  constructor(
    private toastr: ToastrService,
    private fb: FormBuilder,
    private saglasnostService: SaglasnostService,
    private sertifikatService: SertifikatService,
    private potvrdaService: PotvrdeService
  ) {}

  today: Date = new Date();

  ngOnInit(): void {
    this.pretragaSaglasnostiForm = this.fb.group({
      datumTermina: new FormControl(''),
      ime: new FormControl(''),
      prezime: new FormControl(''),
      identifikator: new FormControl(''),
      email: new FormControl(''),
      operator: ['true'],
    });

    let dte = new Date();
    this.today.setDate(dte.getDate() - 1);
    this.pretragaSertifikataForm = this.fb.group({
      datum: new FormControl(''),
      ime: new FormControl(''),
      prezime: new FormControl(''),
      jmbg: new FormControl(''),
      brPasosa: new FormControl(''),
      brSertifikata: new FormControl(''),
      operator: ['true'],
    });

    this.pretragaPotvrdaForm = this.fb.group({
      datum: new FormControl(''),
      ime: new FormControl(''),
      prezime: new FormControl(''),
      identifikator: new FormControl(''),
      operator: ['true'],
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

  // get Saglasnosti
  getAllSaglasnosti(): void {
    this.saglasnostService.getAll().subscribe({
      next: (success) => {
        this.parseIdXml(success, 'saglasnost');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // get Sertifikati
  getAllSertifikati(): void {
    this.sertifikatService.getAll().subscribe({
      next: (success) => {
        this.parseIdXml(success, 'sertifikat');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // get Potvrde
  getAllPotvrde(): void {
    this.documents = [];
    this.potvrdaService.getAll().subscribe({
      next: (success) => {
        console.log(success);
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

  pretraziSaglasnosti() {
    this.documents = [];

    var d = this.pretragaSaglasnostiForm.value.datumTermina;
    var date = '';

    if (d != '') {
      var month = d.getMonth() + 1;
      var day = d.getDate();
      var date =
        d.getFullYear() +
        '-' +
        (month < 10 ? '0' + month : month) +
        '-' +
        (day < 10 ? '0' + day : day);
    }
    let pretragaDTO = `<saglasnostNaprednaDTO>
                          <ime>${this.pretragaSaglasnostiForm.value.ime}</ime>
                          <prezime>${this.pretragaSaglasnostiForm.value.prezime}</prezime>
                          <id>${this.pretragaSaglasnostiForm.value.identifikator}</id>
                          <datum>${date}</datum>
                          <email>${this.pretragaSaglasnostiForm.value.email}</email>
                          <and>${this.pretragaSaglasnostiForm.value.operator}</and>
                      </saglasnostNaprednaDTO>`;

    this.saglasnostService.naprednaPretraga(pretragaDTO).subscribe({
      next: (success) => {
        this.parseIdXml(success, 'saglasnost');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  pretraziSertifikate() {
    this.documents = [];
    var d = this.pretragaSertifikataForm.value.datum;
    var date = '';
    if (d != '') {
      var month = d.getMonth() + 1;
      var day = d.getDate();
      var date =
        d.getFullYear() +
        '-' +
        (month < 10 ? '0' + month : month) +
        '-' +
        (day < 10 ? '0' + day : day);
    }
    let pretragaDTO = `<sertifikatNaprednaDTO>
                          <broj_pasosa>${this.pretragaSertifikataForm.value.brPasosa}</broj_pasosa>
                          <broj_sertifikata>${this.pretragaSertifikataForm.value.brSertifikata}</broj_sertifikata>
                          <datum_izdavaja>${date}</datum_izdavaja>
                          <ime>${this.pretragaSertifikataForm.value.ime}</ime>
                          <jmbg>${this.pretragaSertifikataForm.value.jmbg}</jmbg>
                          <prezime>${this.pretragaSertifikataForm.value.prezime}</prezime>
                          <and>${this.pretragaSertifikataForm.value.operator}</and>
                      </sertifikatNaprednaDTO>`;

    this.sertifikatService.naprednaPretraga(pretragaDTO).subscribe({
      next: (success) => {
        console.log(success);
        this.parseIdXml(success, 'sertifikat');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  pretraziPotvrde() {
    this.documents = [];
    var d = this.pretragaSaglasnostiForm.value.datumTermina;
    var date = '';
    if (d != '') {
      var month = d.getMonth() + 1;
      var day = d.getDate();
      var date =
        d.getFullYear() +
        '-' +
        (month < 10 ? '0' + month : month) +
        '-' +
        (day < 10 ? '0' + day : day);
    }
    let pretragaDTO = `<potvrdaNaprednaDTO>
                          <ime>${this.pretragaPotvrdaForm.value.ime}</ime>
                          <prezime>${this.pretragaPotvrdaForm.value.prezime}</prezime>
                          <id>${this.pretragaPotvrdaForm.value.identifikator}</id>
                          <datum>${date}</datum>
                          <and>${this.pretragaPotvrdaForm.value.operator}</and>
                      </potvrdaNaprednaDTO>`;

    this.potvrdaService.naprednaPretraga(pretragaDTO).subscribe({
      next: (success) => {
        this.parseIdXml(success, 'potvrda');
      },
      error: (error) => {
        console.log(error);
      },
    });
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
