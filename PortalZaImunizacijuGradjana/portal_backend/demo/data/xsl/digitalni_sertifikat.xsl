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
                    .center-title{
                    position:absolute;
                    top:10;
                    right:50%;
                    text-align: center;
                    font-size: 16
                    }
                    .center-title2{
                    position:absolute;
                    top:26;
                    right:50%;
                    text-align: center;
                    font-size: 14
                    }
                    .right-down{
                    width: 50%;
                    float: right;
                    text-align: left;
                    padding: 20px;
                    }
                    .left-down{
                    width: 50%;
                    float: left;
                    text-align: left;
                    padding: 20px;
                    }
                    .center-title3{
                    position:absolute;
                    top:55;
                    right:50%;
                    text-align: center;
                    font-size: 16
                    }
                    .center-title4{
                    position:absolute;
                    top:70;
                    right:50%;
                    text-align: center;
                    font-size: 14
                    }
                    .with-border{

                    border: 1px solid black;
                    width="50%";
                    }
                </style>
            </head>

            <body>


                <div class="center-title">
                    <b>?????????????????? ???????????? ????????????????????</b>
                </div>
                <div class="center-title2">Potvrda o izvr??eoj vakcinaciji protiv<br/>COVID-19 i rezultatima testiranja
                </div>
                <div class="center-title3">
                    <b>DIGITAL GREEN CERTIFICATE</b>
                </div>
                <div class="center-title4">Certificate of vaccination against COVID-19
                    <br/>
                    and test results
                </div>

                <div width="50%">
                    <b>???????? ?????????????????????? /
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_sertifikatu/Broj_sertifikata"/><br/>
                        Certificate id:
                    </b>
                </div>
                <div width="50%">
                    <b>?????????? ?? ?????????? ???????????????? ?????????????????????? /
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_sertifikatu/Datum_i_vreme_izdavanja"/>
                        Certificate issuing date and time
                    </b>
                </div>
                <div>
                    <b>?????? ?? ?????????????? / Name and surename:
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Ime"/>
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Prezime"/>
                    </b>
                </div>
                <div>
                    <b>?????? / Gender:
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Pol"/>
                    </b>
                </div>
                <div>
                    <b>?????????? ???????????? / Date of birth:
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Datum_rodjenja"/>
                    </b>
                </div>
                <div>
                    <b>???????? / Personal No. / EBS:
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Jmbg"/>
                    </b>
                </div>
                <div>
                    <b>???????? ???????????? / Passport No.:
                        <xsl:value-of select="/Digitalni_zeleni_sertifikat/Podaci_o_osobi/Broj_pasosa"/>
                    </b>
                </div>

                <xsl:for-each select="Digitalni_zeleni_sertifikat/Podaci_o_vakcinaciji/Vakcinacija">
                    <div class="with-border">

                        <div>
                            ???????? / Dose :
                            <xsl:value-of select="@br_doze"/>
                        </div>
                        <div>
                            ?????? / Type :
                            <br/>
                            <xsl:value-of select="Tip"/>
                        </div>
                        <div>
                            ???????????????????? ?? ???????????? / Manufacturer and batch number :
                            <br/>
                            <xsl:value-of select="Proizvodjac"/>
                            <xsl:value-of select="Serija"/>
                        </div>
                        <div>
                            ?????????? / Date :
                            <xsl:value-of select="Datum"/>
                        </div>
                        <div>
                            ?????????????????????? ???????????????? / Health care institution :
                            <br/>
                            <xsl:value-of select="Zdravstvena_ustanova"/>
                        </div>
                    </div>
                </xsl:for-each>
                <div class="left-down">
                    <b>???????????????????? ????????????:</b>
                    ???????????????? ???? ?????????? ?????????????? ????????????
                    ,,???? ?????????? ?????????????????? ??????????"
                    <b>Certificate issued by</b>
                    Institute of Public Health of Serbia
                    "Dr Milan Jovanovi?? Batut"
                </div>

                <div class="right-down">
                    <b>?????????????????? ???????????? / Digitally signed by: </b>
                    ?????????????????? ???????????? <br/>?????????? ?????????????????? ???????????? <br/>?????????????????????? ???? ???????????????????????? <br/>
                    ?????????????????????? ?? ?????????????????????? ????????????,<br/> ???????????????? 11, ??????????????
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>