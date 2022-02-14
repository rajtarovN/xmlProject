<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje"
        xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master
                        master-name="zahtev-page">
                    <fo:region-body margin-top="0.75in"
                                    margin-bottom="0.75in" margin-left="80pt" margin-right="80pt" />
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="zahtev-page">
                <fo:flow flow-name="xsl-region-body">

                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        Исказивање интересовања за вакцинисање против COVID-19
                    </fo:block>
                    <fo:block  text-align="center" font-size="12pt"  font-family="Times New Roman">
                       Држављанин:
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        &#160;&#160;&#160;&#160; <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Drzavljanstvo"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                       ЈМБГ:&#xa; <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Jmbg"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Име:&#xa; <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Ime"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Презиме:&#xa; <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Prezime"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Адреса електронске поште:
                        &#xa; <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Kontakt/ns2:Email"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Број мобилног телефона (навести број у формату 06X..... без размака и цртица):&#xa;
                        <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Kontakt/ns2:Broj_mobilnog"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Број фиксног телефона (навести број у формату нпр. 011..... без размака и цртица):&#xa;
                        <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Kontakt/ns2:Broj_fiksnog"/>
                    </fo:block>
                    <fo:block  text-align="center" font-size="12pt" font-family="Times New Roman">
                        Одаберите локацију где желите да примите вакцину (унесите општину):&#xa;
                        <xsl:value-of select="/ns2:Interesovanje/ns2:Lokacija_primanja_vakcine"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Исказујем интересовање да примим искључиво вакцину следећих произвођача за
                        који Агенција за лекове и медицинска средства потврди безбедност, ефикасност и
                        квалитет и изда дозволу за употребу лека: &#xa; <xsl:value-of select="/ns2:Interesovanje/ns2:Proizvodjaci"/>
                    </fo:block>
                    <fo:block  text-align="center" font-size="12pt" font-family="Times New Roman">
                        Да ли сте добровољни давалац крви? <xsl:value-of select="/ns2:Interesovanje/ns2:Licne_informacije/ns2:Davalac_krvi"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Дана   <xsl:value-of select="/ns2:Interesovanje/ns2:Datum_podnosenja_interesovanja"/>  године
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>