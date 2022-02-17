import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Information } from 'src/modules/shared/models/information';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';
import { SertifikatService } from '../../services/sertifikat-service/sertifikat.service';

@Component({
  selector: 'app-obicna-pretraga',
  templateUrl: './obicna-pretraga.component.html',
  styleUrls: ['./obicna-pretraga.component.scss']
})
export class ObicnaPretragaComponent implements OnInit {
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
      searchTerm: new FormControl('')
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
    this.pretragaForm.reset();
    this.getAllSaglasnosti();
  }

  close(index: number) {
    this.documents[index].open = false;
  }

  pretraziSaglasnosti() {
    this.documents = [];

    if(this.pretragaForm.value.searchTerm != null && this.pretragaForm.value.searchTerm != ""){
      this.saglasnostService.obicnaPretraga(this.pretragaForm.value.searchTerm).subscribe({
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
    //TODO olja
    /*
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
    });*/
  }

  preuzmiPDF(url: String | null) {
    if (url === null) return;
    // TODO NATASA

    if (this.dokument === '1') {
      // Saglasnost
    } else if (this.dokument === '2') {
      // Digitalni zeleni sertifikat
    } else {
      // Potvrda
    }
  }

  // URL npr: http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/12345
  preuzmiXML(url: String | null) {
    if (url === null) return;
    var lista = url.split('/');
    var id = lista[lista.length - 1]; // ID DOKUMENTA npr: 12345

    console.log(url);
    console.log(id);

    // TODO NATASA

    if (this.dokument === '1') {
      // Saglasnost
    } else if (this.dokument === '2') {
      // Digitalni zeleni sertifikat
    } else {
      // Potvrda
    }
  }
}
