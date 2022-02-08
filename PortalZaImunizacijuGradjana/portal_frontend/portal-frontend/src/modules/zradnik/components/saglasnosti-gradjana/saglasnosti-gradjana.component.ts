import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SaglasnostService } from 'src/modules/shared/services/saglasnost-service/saglasnost.service';

@Component({
  selector: 'app-saglasnosti-gradjana',
  templateUrl: './saglasnosti-gradjana.component.html',
  styleUrls: ['./saglasnosti-gradjana.component.scss']
})
export class SaglasnostiGradjanaComponent implements OnInit {
  minDate: Date;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  searchForm: FormGroup;
  searchString: string;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  displayedColumns: string[] = ['Email', 'Ime', 'Prezime', 'Datum'];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private saglasnostService: SaglasnostService
  ) {
    //const currentYear = new Date().getFullYear();
    this.minDate = new Date();
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
      filter: [null],
    });
    this.searchString = "";
   }

  ngOnInit(): void {
    this.saglasnostService.searchTermine(new Date(), '').subscribe((response) => {
      this.setData(response);
    });
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
}
