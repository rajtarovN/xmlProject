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

                    table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                    }
                </style>
            </head>

            <body>

                <h2 class="add-space-after"> <u> Исказивање интересовања за вакцинисање против COVID-19  </u> </h2>

                <div>
                    <label>Држављанин:</label>
                    <label>
                        &#09;&#09; <xsl:value-of select="/Interesovanje/Licne_informacije/Drzavljanstvo"></xsl:value-of>
                    </label><br/>

                    <label>
                        ЈМБГ:&#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Jmbg"></xsl:value-of>
                    </label><br/>

                    <label>Пол:</label>
                    <label>
                        <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Pol"></xsl:value-of>
                    </label><br/>

                    <label>
                        ЈМБГ:&#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Jmbg"></xsl:value-of>
                    </label><br/>

                    <label>
                        Име:&#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Ime"></xsl:value-of>
                    </label><br/>
                    <label>
                        Презиме:&#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Prezime"></xsl:value-of>
                    </label><br/>
                    <label>
                        Адреса електронске поште:
                        &#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Email"></xsl:value-of>
                    </label><br/>

                    <label>
                        Број мобилног телефона (навести број у формату 06X..... без размака и цртица):&#xa;
                        <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Broj_mobilnog"></xsl:value-of>
                    </label><br/>
                    <label>
                        Број фиксног телефона (навести број у формату нпр. 011..... без размака и цртица):&#xa;
                        <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Broj_fiksnog"></xsl:value-of>
                    </label><br/>

                    <label>
                        Одаберите локацију где желите да примите вакцину (унесите општину):&#xa;
                        <xsl:value-of select="/Interesovanje/Lokacija_primanja_vakcine"></xsl:value-of>
                    </label><br/>

                    <label>
                        Исказујем интересовање да примим искључиво вакцину следећих произвођача за
                        који Агенција за лекове и медицинска средства потврди безбедност, ефикасност и
                        квалитет и изда дозволу за употребу лека: &#xa; <xsl:value-of select="/Interesovanje/Proizvodjaci"></xsl:value-of>
                    </label><br/>

                    <label>
                        Да ли сте добровољни давалац крви? <xsl:value-of select="/Interesovanje/Licne_informacije/Davalac_krvi"></xsl:value-of>
                    </label><br/>
                    <label>
                        Дана   <xsl:value-of select="/Interesovanje/Datum_podnosenja_interesovanja"></xsl:value-of>  године
                    </label><br/>

                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>