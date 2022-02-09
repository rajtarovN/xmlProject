import { Injectable } from '@angular/core';
//import { xml2json } from 'xml-js';

@Injectable({
  providedIn: 'root',
})
export class HelperService {
  constructor() {}

  async parseXmlToJson(xml: any) {
    //var x = xml2json(xml, { compact: true, spaces: 4 });
    //console.log(x);
    //return x;
  }
}
