import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ZahtevService } from '../../services/zahtev-service/zahtev.service';

@Component({
  selector: 'app-odbij-zahtev',
  templateUrl: './odbij-zahtev.component.html',
  styleUrls: ['./odbij-zahtev.component.scss'],
})
export class OdbijZahtevComponent implements OnInit {
  @Output() onRazlogOdbijanjaClose = new EventEmitter();
  @Output() onRazlogOdbijanjaConfirm = new EventEmitter();
  @ViewChild('notes') notes: ElementRef;
  @Input() currentZahtevId: string;

  constructor(
    public router: Router,
    private toastr: ToastrService,
    private zahtevService: ZahtevService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onRazlogOdbijanjaClose.emit(true);
  }

  confirm() {
    const razlog = this.notes.nativeElement.value;
    if (razlog == null || razlog == '') {
      this.toastr.error('Morate uneti razlog odbijanja zahteva!');
    } else {
      this.zahtevService.odbijZahtev(this.currentZahtevId, razlog).subscribe(
        (response) => {
          this.toastr.success("Uspesno odbijen zahtev za digitalni sertifikat!");
          this.onRazlogOdbijanjaConfirm.emit(true);
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }
}
