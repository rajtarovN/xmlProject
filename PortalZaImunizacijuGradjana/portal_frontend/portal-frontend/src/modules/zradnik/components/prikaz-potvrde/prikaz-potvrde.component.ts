import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { PotvrdaService } from 'src/modules/shared/services/potvrda-vakcinacije-service/potvrda.service';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';
import * as txml from 'txml';
import * as JsonToXML from 'js2xmlparser';
import { EvidentiraneVakcine } from '../../models/evidentirane-vakcine';
import { MatRadioChange } from '@angular/material/radio';
import { PotvrdaVakcinacije } from '../../models/potvrda-vakcinacije';

@Component({
  selector: 'app-prikaz-potvrde',
  templateUrl: './prikaz-potvrde.component.html',
  styleUrls: ['./prikaz-potvrde.component.scss']
})
export class PrikazPotvrdeComponent implements OnInit {
  parser = new DOMParser();
  @Input() email = '';
  @Input() brojSaglasnosti = '';
  vakcForm: FormGroup;
  @Output() onPotvrdaClose = new EventEmitter();
  @Output() onPotvrdaSaved = new EventEmitter();
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  minDate: Date;
  data: any[];
  srb: boolean;
  displayedColumns: string[] = [
    'Doza vakcine',
    'Naziv vakcine',
    'Datum davanja vakcine',
    'Serija vakcine',
  ];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService,
    private potvrdaService: PotvrdaService
  ) {
    this.data = [];
    this.vakcForm = this.fb.group({
      ime: [null, Validators.required],
      prezime: [null, Validators.required],
      datumRodjenja: [null, Validators.required],
      jmbg: [null],
      ebs: [null],
      zustanova: [null, Validators.required],
      pol: [null, Validators.required],
    });
    this.minDate = new Date();
    this.srb = true;
  }

  ngOnInit(): void {
    this.getPotvrda();
    this.getTableElements();
  }

  getPotvrda(){
    this.potvrdaService.createPotvrdu(this.brojSaglasnosti).subscribe(
      (response) => {
        let xmlDoc = this.parser.parseFromString(response, 'text/xml');   
        const ime =  String(xmlDoc.getElementsByTagName('ime')[0].childNodes[0].nodeValue);
        const prezime =  String(xmlDoc.getElementsByTagName('prezime')[0].childNodes[0].nodeValue);
        const datumRodjenja =  String(xmlDoc.getElementsByTagName('datumRodjenja')[0].childNodes[0].nodeValue);
        const drz =  String(xmlDoc.getElementsByTagName('drz')[0].childNodes[0].nodeValue);
        const zustanova =  String(xmlDoc.getElementsByTagName('zUstanova')[0].childNodes[0].nodeValue);
        const pol =  String(xmlDoc.getElementsByTagName('pol')[0].childNodes[0].nodeValue);
        if(drz === "str"){
          this.srb = false;
          const ebs =  String(xmlDoc.getElementsByTagName('ebs')[0].childNodes[0].nodeValue);
          this.vakcForm = this.fb.group({          
            ime: [ime, Validators.required],
            prezime: [prezime, Validators.required],
            datumRodjenja: [datumRodjenja, Validators.required],
            ebs: [ebs, Validators.required],
            zustanova: [zustanova, Validators.required],
            pol: [pol, Validators.required],
          });
        }
        else{      
          this.srb = true;  
          const jmbg =  String(xmlDoc.getElementsByTagName('jmbg')[0].childNodes[0].nodeValue);
          this.vakcForm = this.fb.group({          
            ime: [ime, Validators.required],
            prezime: [prezime, Validators.required],
            datumRodjenja: [datumRodjenja, Validators.required],
            jmbg: [jmbg, Validators.required],
            zustanova: [zustanova, Validators.required],
            pol: [pol, Validators.required],
          });
        }
      }
    )
  }

  getTableElements() {
    this.saglasnostService.getEvidentiranevakcine(this.email).subscribe(
      (response) => {
        if (response != 'Nema prethodno evidentiranih vakcina.') {
          let obj: any = txml.parse(response);
          let list: Array<EvidentiraneVakcine> = [];
          if (obj[0].children != []) {
            obj[0].children.forEach((element: any, index: number) => {
              const temp: EvidentiraneVakcine = {
                nazivVakcine: String(element.children[0].children[0]),
                datumDavanja: String(element.children[1].children[0]),
                serijaVakcine: String(element.children[4].children[0]),
                doza: index+1,
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
    this.onPotvrdaClose.emit(true);
  }

  confirm() {
    if (
      this.vakcForm.value.zustanova != null &&
      this.vakcForm.value.pol != null &&
      this.vakcForm.value.ime != null &&
      this.vakcForm.value.prezime != null &&
      this.vakcForm.value.datumRodjenja != null &&
      (this.vakcForm.value.jmbg != null ||
        this.vakcForm.value.ebs != null)
    ) {
      this.data.sort((n1, n2) => n1.datumDavanja - n2.datumDavanja);
      const options = {
        format: {
          doubleQuotes: true,
        },
        declaration: {
          include: false,
        },
      };

      //set vakcine
      let listaVakcinaStringCombined = '\n';
      this.data.forEach((value) => {
        let vakcina: any = JsonToXML.parse(
          'evidentirana_vakcina',
          value,
          options
        );
        listaVakcinaStringCombined += vakcina;
      });
      var altstr = listaVakcinaStringCombined.replace(
        /<[/]evidentirana_vakcina>/gi,
        '</evidentirana_vakcina>\n'
      );
      const listaVakcinaXml: any = JsonToXML.parse(
        'lista_evidentiranih_vakcina',
        altstr,
        options
      );
      var tryal01 = listaVakcinaXml.replace(/&lt;[/]/gi, '</');
      var tryal02 = tryal01.replace(/&lt;/gi, '\t<');
      var tryal03 = tryal02.replace(
        /<[/]evidentirana_vakcina>/gi,
        '\t</evidentirana_vakcina>'
      );

      //set potvrdu
      let temp: PotvrdaVakcinacije = {
        brojSaglasnosti: this.brojSaglasnosti,
        ime: this.vakcForm.value.ime,
        prezime: this.vakcForm.value.prezime,  
        datumRodjenja: this.vakcForm.value.datumRodjenja, 
        pol: this.vakcForm.value.pol, 
        zUstanova: this.vakcForm.value.zustanova, 
        datumIzdavanja: String(new Date()), 
        drz: this.srb ? "srb" : "str",
      };
      if(temp.drz === "srb") temp.jmbg = this.vakcForm.value.jmbg;
      else temp.ebs = this.vakcForm.value.ebs;

      let data: any = JsonToXML.parse(
        'potvrdaVakcinacijeDTO',
        temp,
        options
      );

      //send potvrdu then vakcine
      this.potvrdaService
        .savePotvrdu(data)
        .subscribe(
          (response) => {
            var t = String(response);
            this.potvrdaService
              .saveDoze(tryal03, t, this.email)
              .subscribe(
                (result) => {
                  this.toastr.success('Uspesno izdata potvrda o vakcinaciji!');
                  this.toastr.success('Uspesno kreiran sledeci termin vakcinacije!');
                  this.onPotvrdaSaved.emit(this.brojSaglasnosti);
                },
                (error) => {
                  this.toastr.error(error.error);
                }
              );
            
          },
          (error) => {
            this.toastr.error(error.error);
          }
        );
    }else{
      this.toastr.error('Sva polja moraju biti popunjena!');
    }
  }

}
