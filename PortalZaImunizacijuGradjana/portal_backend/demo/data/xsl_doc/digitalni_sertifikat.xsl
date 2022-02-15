<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom"
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
                    <fo:block font-family="Times New Roman" width="60%"
                              margin-top="10pt" text-align="center" border="none"  text-decoration="underline">
                    </fo:block>


                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        ЗАХТЕВ
                    </fo:block>
                    <fo:block  text-align="center" font-size="12pt" font-weight="bold" font-family="Times New Roman">
                        за издавање дигиталног зеленог сертификата
                    </fo:block>
                    <fo:block>&#160;</fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        &#160;&#160;&#160;&#160;У складу са одредбом Републике Србије о издавању дигиталног зеленог
                        сертификата као потврде о извршеној вакцинацији против COVID-19, резултатима
                        тестирања на заразну болест SARS-CoV-2 или опоравку од болести COVID-19,
                        подносим захтев за издавање дигиталног зеленог сертификата.
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Подносилац захтева:
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Име и презиме:
                        <fo:inline> <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Ime"/></fo:inline> &#160;
                        <fo:inline> <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Prezime"/></fo:inline>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Датум рођења: <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Datum_rodjenja"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Пол: <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Pol"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Јединствени матични број грађанина: <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Jmbg"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Број пасоша: <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Broj_pasosa"/>
                    </fo:block><br/>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Разлог за подношење захтева: <fo:block>&#160;</fo:block> <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Podnosilac_zahteva/ns2:Razlog_podnosenja_zahteva"/>
                    </fo:block>
                    <fo:block  text-align="center" font-size="10pt" font-family="Times New Roman">
                        (навести што прецизнији разлога за подношење захтева за издавање дигиталног пасоша)
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        У <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Zaglavlje/ns2:Mesto_podnosenja_zahteva"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Дана <xsl:value-of select="/ns2:Zahtev_za_zeleni_sertifikat/ns2:Zaglavlje/ns2:Dan_podnosenja_zahteva"/>   године
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>