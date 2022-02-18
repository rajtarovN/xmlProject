import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-razlog-zahteva',
  templateUrl: './razlog-zahteva.component.html',
  styleUrls: ['./razlog-zahteva.component.scss']
})
export class RazlogZahtevaComponent implements OnInit {
  @Output() onRazlogClose = new EventEmitter();
  @ViewChild('notes') notes: ElementRef;
  @Input() currentNotes: string;

  constructor(public router: Router) { }

  ngOnInit(): void {
  }

  cancel(){
    this.onRazlogClose.emit(true);
  }
}
