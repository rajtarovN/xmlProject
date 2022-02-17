<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/digitalni_zeleni_sertifikat"
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

            <fo:page-sequence master-reference="sertifikat-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="Times New Roman" width="60%"
                              margin-top="10pt" text-align="center" border="none"  text-decoration="underline">
                    </fo:block>


                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        ДИГИТАЛНИ ЗЕЛЕНИ СЕРТИФИКАТ
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt" text-align="center" margin-top="48pt">
                        Potvrda o izvršeoj vakcinaciji protiv COVID-19 i rezultatima testiranja
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        DIGITAL GREEN CERTIFICATE
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt" text-align="center" margin-top="48pt">
                        Certificate of vaccination against COVID-19 and test results
                    </fo:block>

                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Број сертификата / Certificate id:
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_sertifikatu/ns2:Broj_sertifikata"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Датум и време издавања сертификата / Certificate issuing date and time
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_sertifikatu/ns2:Datum_i_vreme_izdavanja"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Име и презиме / Name and surename:
                        </fo:inline>

                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Ime"/>
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Prezime"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Пол / Gender:
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Pol"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Датум рођења / Date of birth:
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Datum_rodjenja"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            ЈМБГ / Personal No. / EBS:
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Jmbg"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:inline  text-align="left" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                            Број пасоша / Passport No.:
                        </fo:inline>
                        <fo:inline text-align="left" font-size="12pt" font-family="Times New Roman">
                            <xsl:value-of select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_osobi/ns2:Broj_pasosa"/>
                        </fo:inline>
                    </fo:block>

                    <xsl:for-each select="/ns2:Digitalni_zeleni_sertifikat/ns2:Podaci_o_vakcinaciji/ns2:Vakcinacija">
                        <fo:block border="1px solid" width="50%">

                            <fo:block>
                                Доза / Dose :
                                <xsl:value-of select="@br_doze"/>
                            </fo:block>
                            <fo:block>
                                Тип / Type :
                                <xsl:value-of select="ns2:Tip"/>
                            </fo:block>
                            <fo:block>
                                Произвођач и серија / Manufacturer and batch number :

                                <xsl:value-of select="ns2:Proizvodjac"/>
                                <xsl:value-of select="ns2:Serija"/>
                            </fo:block>
                            <fo:block>
                                Датум / Date :
                                <xsl:value-of select="ns2:Datum"/>
                            </fo:block>
                            <fo:block>
                                Здравствена установа / Health care institution :

                                <xsl:value-of select="ns2:Zdravstvena_ustanova"/>
                            </fo:block>
                        </fo:block>
                    </xsl:for-each>
                    <fo:block class="left-down">
                        Сертификат издаје:
                        Институт за јавно здравље Србије
                        ,,Др Милан Јовановић Батут"
                        Certificate issued by
                        Institute of Public Health of Serbia
                        "Dr Milan Jovanović Batut"
                    </fo:block>

                    <fo:block class="right-down">
                        Дигитални потпис / Digitally signed by:
                        РЕПУБЛИКА СРБИЈА Влада Републике Србије Канцеларија за информационе
                        технологије и електронску управу, Немаљина 11, Београд
                    </fo:block>


                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>