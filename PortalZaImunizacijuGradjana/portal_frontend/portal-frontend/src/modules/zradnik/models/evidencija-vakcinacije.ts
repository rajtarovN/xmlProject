import { EvidentiraneVakcine } from "./evidentirane-vakcine";

export interface EvidencijaVakcinacije{
    zdravstvenaUstanova: string;    
    vakcinacijskiPunkkt: string;
    nacinDavanja: string;    
    imeLekara: string;
    prezimeLekara: string;
    telefonLekara: string;
    odlukaKomisije: string;
    datumUtvrdjivanja: string;
    dijagnoza: string;
    vakcineDTOS: Array<EvidentiraneVakcine>;
}