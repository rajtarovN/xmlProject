<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/izvestaj_o_imunizaciji"
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
                        Извештај о имунизацији
                    </fo:block>
                    <fo:block>&#160;</fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        Извештај се односи на период од <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Period_izvestaja/ns2:Pocetak_perioda"/>  до
                        <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Period_izvestaja/ns2:Kraj_perioda"/>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        У напоменутом временском интервалу је:
                        <fo:block>
                            -поднето <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Podaci_o_zahtevima/ns2:Broj_dokumenata_o_interesovanju"/> докумената о интересовању за имунизацију;</fo:block>
                    <fo:block>
                        -примљено <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Podaci_o_zahtevima/ns2:Broj_zahteva/ns2:Primljeno"/> захтева за дигитални зелени сертификат, од којих је
                        <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Podaci_o_zahtevima/ns2:Broj_zahteva/ns2:Izdato"/>
                        издато.
                    </fo:block>
                    <fo:block>
                        Дато је <xsl:value-of select="/ns2:Izvestaj_o_imunizaciji/ns2:Podaci_o_zahtevima/ns2:Broj_dokumenata_o_interesovanju"/> доза вакцине против COVID-19 вируса у следећој количини:
                    </fo:block>
                        <fo:block>

                            <fo:table font-family="serif" margin="5px auto 50px auto" border="1px">
                                <fo:table-column column-width="50%"/>
                                <fo:table-column column-width="50%"/>

                                <fo:table-body>
                                    <fo:table-row border="1px solid">

                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                                Редни број дозе
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                                Број датих доза
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>


                                    <xsl:for-each select="/ns2:Izvestaj_o_imunizaciji/ns2:Lista_vakcina/ns2:Vakcina">
                                        <fo:table-row border="1px solid">
                                            <fo:table-cell padding="10px">
                                                <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                    <xsl:value-of select="ns2:Redni_broj_doze"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="10px">
                                                <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                    <xsl:value-of select="ns2:Broj_datih_doza"/>
                                                </fo:block>
                                            </fo:table-cell>

                                        </fo:table-row>
                                    </xsl:for-each>


                                </fo:table-body>
                            </fo:table>

                            <fo:block>
                                Расподела по произвођачима је:
                                <fo:list-block>
                                    <xsl:for-each select="/ns2:Izvestaj_o_imunizaciji/ns2:Raspodela_po_proizvodjacima/ns2:Proizvodjac">
                                    <fo:list-item>
                                        <xsl:value-of select="ns2:Ime_proizvodjaca"/> - <xsl:value-of select="ns2:Ime_proizvodjaca"/>
                                    </fo:list-item>
                                    </xsl:for-each>
                                </fo:list-block>
                            </fo:block>
                        </fo:block>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>