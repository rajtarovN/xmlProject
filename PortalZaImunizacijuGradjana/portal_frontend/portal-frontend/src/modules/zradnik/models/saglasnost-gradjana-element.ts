export interface SaglasnostGradjanaElement{
    brojSaglasnosti: string;    
    ime: string;
    prezime: string;    
    datum_termina: string;
    email: string;
    odabranaVakcina: string;
    primioDozu?: boolean;
    dobioPotvrdu?: boolean;
}