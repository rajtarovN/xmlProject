import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import { Information } from 'src/modules/shared/models/information';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';
import * as JsonToXML from 'js2xmlparser';
import { SertifikatService } from '../../services/sertifikat-service/sertifikat.service';
import { ToastrService } from 'ngx-toastr';
declare const Xonomy: any;

@Component({
  selector: 'app-arhiva-dokumenata',
  templateUrl: './arhiva-dokumenata.component.html',
  styleUrls: ['./arhiva-dokumenata.component.scss'],
})
export class ArhivaDokumenataComponent implements OnInit {
  pretragaSaglasnostiForm: FormGroup;
  pretragaSertifikataForm: FormGroup;
  parser = new DOMParser();
  selected: string = 'zahtev';

  selectedOperator: string = 'and';

  dokument: String = '1';
  documents: Array<Information> = [];

  constructor(
    private toastr: ToastrService,
    private fb: FormBuilder,
    private saglasnostService: SaglasnostService,
    private sertifikatService: SertifikatService,
    private potvrdaService: PotvrdeService
  ) {}

  ngOnInit(): void {
    this.pretragaSaglasnostiForm = this.fb.group({
      datumTermina: new FormControl(''),
      ime: new FormControl(''),
      prezime: new FormControl(''),
      jmbg: new FormControl(''),
      email: new FormControl(''),
      operator: ['true'],
    });

    this.pretragaSertifikataForm = this.fb.group({
      datum: new FormControl(''),
      ime: new FormControl(''),
      prezime: new FormControl(''),
      jmbg: new FormControl(''),
      brPasosa: new FormControl(''),
      brSertifikata: new FormControl(''),
      operator: ['true'],
    });
    this.getAllSaglasnosti();
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

  // TODO GET potvrde

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
    }
  }

  reset(): void {
    this.documents = [];
    this.pretragaSaglasnostiForm.reset();
    this.getAllSaglasnosti();
  }

  close(index: number) {
    this.documents[index].open = false;
  }

  pretraziSaglasnosti() {
    this.documents = [];

    let pretragaDTO = `<saglasnostNaprednaDTO>
                          <ime>${this.pretragaSaglasnostiForm.value.ime}</ime>
                          <prezime>${this.pretragaSaglasnostiForm.value.prezime}</prezime>
                          <jmbg>${this.pretragaSaglasnostiForm.value.jmbg}</jmbg>
                          <datum>${this.pretragaSaglasnostiForm.value.datumTermina}</datum>
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

    let pretragaDTO = `<sertifikatNaprednaDTO>
                          <broj_pasosa>${this.pretragaSertifikataForm.value.brPasosa}</broj_pasosa>
                          <broj_sertifikata>${this.pretragaSertifikataForm.value.brSertifikata}</broj_sertifikata>
                          <datum_izdavaja>${this.pretragaSertifikataForm.value.datum}</datum_izdavaja>
                          <ime>${this.pretragaSertifikataForm.value.ime}</ime>
                          <jmbg>${this.pretragaSertifikataForm.value.jmbg}</jmbg>
                          <prezime>${this.pretragaSertifikataForm.value.prezime}</prezime>
                          <and>${this.pretragaSertifikataForm.value.operator}</and>
                      </sertifikatNaprednaDTO>`;

    this.sertifikatService.naprednaPretraga(pretragaDTO).subscribe({
      next: (success) => {
        this.parseIdXml(success, 'sertifikat');
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
      this.saglasnostService.getPdf(id).subscribe((response) =>{
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
      });
    } else if (this.dokument === '2') {
      this.sertifikatService.getPdf(id).subscribe((response) =>{
        
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
    });
    } else {
      this.potvrdaService.getPdf(id).subscribe((response) =>{
        
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
    });
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
      this.saglasnostService.getXHtml(id).subscribe((response) =>{

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
  });
    } else if (this.dokument === '2') {
      this.sertifikatService.getXHtml(id).subscribe((response) =>{
        
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
    });
    } else {
      this.potvrdaService.getXHtml(id).subscribe((response) =>{
        
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
});
    }
  }
}
