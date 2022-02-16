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

declare const Xonomy: any;

@Component({
  selector: 'app-arhiva-dokumenata',
  templateUrl: './arhiva-dokumenata.component.html',
  styleUrls: ['./arhiva-dokumenata.component.scss'],
})
export class ArhivaDokumenataComponent implements OnInit {
  pretragaSaglasnostiForm: FormGroup;
  parser = new DOMParser();
  selected: string = 'zahtev';

  selectedOperator: string = 'and';

  dokument: String = '1';
  documents: Array<Information> = [];

  constructor(
    private fb: FormBuilder,
    private saglasnostService: SaglasnostService,
    //private sertifikatService: SertifiktService,
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

    this.getAllSaglasnosti();
  }

  //
  getAllSaglasnosti(): void {
    this.saglasnostService.getAll().subscribe({
      next: (success) => {
        this.parseSaglasnos(success);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  parseSaglasnos(saglasnost: string) {
    let xmlDoc = this.parser.parseFromString(saglasnost, 'text/xml');
    let saglasnosti = xmlDoc.getElementsByTagName('saglasnost')[0].childNodes;
    for (let i = 0; i < saglasnosti.length; i++) {
      console.log(saglasnosti[i].textContent);
      this.documents.push({
        url: saglasnosti[i].childNodes[0].textContent,
        open: false,
        type: 'zahtev',
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
        console.log(' caaaao');
        this.parseSaglasnos(success);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
