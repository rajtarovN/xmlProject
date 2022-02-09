import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-popuni-vakcina-podatke',
  templateUrl: './popuni-vakcina-podatke.component.html',
  styleUrls: ['./popuni-vakcina-podatke.component.scss'],
})
export class PopuniVakcinaPodatkeComponent implements OnInit {
  @Output() onPodaciOVakciniClose = new EventEmitter();
  @Input() brojSaglasnosti = '';
  vakcPodaciForm: FormGroup;
  trajneKontraindikacije = false;
  minDate: Date;

  constructor(private fb: FormBuilder, private toastr: ToastrService) {
    this.vakcPodaciForm = this.fb.group({
      nazivVakcine: [null, Validators.required],
      serijaVakcine: [null, Validators.required],
      proizvodjac: [null, Validators.required],
      reakcija: [null],
      dijagnoza: [null],
      datum: [new Date()],
    });
    this.minDate = new Date();
  }

  ngOnInit(): void {}

  cancel() {
    this.onPodaciOVakciniClose.emit(true);
  }

  confirm(){}
}
