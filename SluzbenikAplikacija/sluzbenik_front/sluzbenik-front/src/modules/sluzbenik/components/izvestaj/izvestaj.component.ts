import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InventoryService } from '../../services/inventory-service/inventory.service';
import * as JsonToXML from 'js2xmlparser';
import { IzvestajService } from '../../services/izvestaj-service/izvestaj.service';

@Component({
  selector: 'app-izvestaj',
  templateUrl: './izvestaj.component.html',
  styleUrls: ['./izvestaj.component.scss'],
})
export class IzvestajComponent implements OnInit {
  datePeriodForm: FormGroup;
  parser = new DOMParser();

  pfizerInv = 0;
  pfizerRes = 0;
  sputnikInv = 0;
  sputnikRes = 0;
  sinopharmInv = 0;
  sinopharmRes = 0;
  astraZenecaInv = 0;
  astraZenecaRes = 0;
  modernaInv = 0;
  modernaRes = 0;

  constructor(
    private fb: FormBuilder,
    private service: IzvestajService,
    private snackBar: MatSnackBar
  ) {
    this.datePeriodForm = this.fb.group({
      dateFrom: [null, Validators.required],
      dateTo: [null, Validators.required]
      
    });
  }

  ngOnInit(): void {}

  getIzvestaj() {
    var fromDate = this.datePeriodForm.value.dateFrom;
    var toDate = this.datePeriodForm.value.dateTo;
    var pocetak = fromDate.getFullYear() + "-" + fromDate.getMonth() + "-" + fromDate.getDate();
    var kraj = toDate.getFullYear() + "-" + toDate.getMonth() + "-" + toDate.getDate();
    console.log(pocetak);
    console.log(kraj);
    this.service.getIzvestaj(pocetak, kraj).subscribe({
      next: (success) => {
        console.log(success)
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Ок', {
      duration: 4000,
    });
  }
}
