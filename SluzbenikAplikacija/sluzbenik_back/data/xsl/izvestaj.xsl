<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    html, body {
                    height: 100%;
                    }

                    html {
                    display: table;
                    margin-left: 10%;
                    margin-right: 10%;
                    margin-top: 8%;
                    margin-bottom: 8%;
                    }

                    body {
                    width: 30%
                    display: table-cell;
                    vertical-align: middle;
                    align-content: center;
                    }

                    .indented {
                    text-indent: 30px;
                    }

                    .add-space-after {
                    margin-bottom: 50px;
                    align: center;
                    }

                    .half{
                    width: 40%;
                    }

                    .smallChar {
                    font-size:10;
                    }

                    h2, h3 {
                    text-align: center;
                    }

                    .text-justify{
                    text-align: justify;
                    }
                </style>
            </head>

            <body>


                <h2 class="add-space-after"> Извештај о имунизацији </h2>

                <div class="text-justify">  </div><br/><br/>
                <div>
                    Извештај се односи на период од <xsl:value-of select="/Izvestaj_o_imunizaciji/Period_izvestaja/Pocetak_perioda"/> до
                    <xsl:value-of select="/Izvestaj_o_imunizaciji/Period_izvestaja/Kraj_perioda"/><br/><br/>
                    У напоменутом временском интервалу је:
                    - поднето <xsl:value-of select="/Izvestaj_o_imunizaciji/Podaci_o_zahtevima/Broj_dokumenata_o_interesovanju"/> докумената о интересовању за имунизацију;
                    - примљено <xsl:value-of select="/Izvestaj_o_imunizaciji/Podaci_o_zahtevima/Broj_zahteva/Primljeno"/> захтева за дигитални зелени сертификат, од којих је
                    <xsl:value-of select="/Izvestaj_o_imunizaciji/Podaci_o_zahtevima/Broj_zahteva/Izdato"/>
                    издато.

                    <br/><br/>
                    <label>Дато је 3225 доза вакцине против COVID-19 вируса у следећој количини: AAAAAAAAAAAA Nije ok </label> <br></br>

                    <table>
                        <tr>
                            <th><b>Редни број дозе</b>
                            </th>
                            <th><b>Број датих доза</b>
                            </th>
                        </tr>
                        <xsl:for-each select="/Izvestaj_o_imunizaciji/Lista_vakcina/Vakcina">
                            <tr border="1px solid">
                                <td padding="10px">
                                    <div class="cell" >
                                        <xsl:value-of select="Redni_broj_doze"/>
                                    </div>
                                </td>
                                <td padding="10px">
                                    <div class="cell" >
                                        <xsl:value-of select="Broj_datih_doza"/>
                                    </div>
                                </td>


                            </tr>
                        </xsl:for-each>
                        <tr><td colspan="7">
                            Привремене контраиндикације
                            (датум утврђивања и дијагноза):
                            <xsl:value-of select="/Saglasnost/Evidencija_o_vakcinaciji/Vakcine/Privremene_kontraindikacije/Datum_utvrdjivanja"/> |
                            <xsl:value-of select="/Saglasnost/Evidencija_o_vakcinaciji/Vakcine/Privremene_kontraindikacije/Dijagnoza"/>
                        </td></tr>
                        <tr><td colspan="7">Одлука комисије за трајне контраиндикације<xsl:value-of select="/Saglasnost/Evidencija_o_vakcinaciji/Vakcine/Odluka_komisije_za_trajne_kontraindikacije"/>
                        </td></tr>
                        <tr>

                        </tr>

                    </table>
                </div>
                <div>
                    Расподела по произвођачима је:
                    <ul>
                        <xsl:for-each select="/Izvestaj_o_imunizaciji/Raspodela_po_proizvodjacima/Proizvodjac">
                            <li>
                                <b><xsl:value-of select="Ime_proizvodjaca"/> </b>- <b> <xsl:value-of select="Broj_doza"/> </b> доза;
                            </li>
                        </xsl:for-each>
                    </ul>

                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>