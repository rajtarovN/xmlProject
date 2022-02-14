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
                    <label>Држављанин:</label><br/>
                    <label>
                        <xsl:if test="/Interesovanje/Licne_informacije/Drzavljanstvo='Drzavljanin_republike_srbije'">
                            <u>Državljanin Republike Srbije</u>
                        </xsl:if>
                        <xsl:if test="/Interesovanje/Licne_informacije/Drzavljanstvo='Strani_drzavljanin_sa_boravkom_u_rs'">
                            <u>Strani državljanin sa boravkom u RS</u>
                        </xsl:if>
                        <xsl:if test="/Interesovanje/Licne_informacije/Drzavljanstvo='Strani_drzavljanin_bez_boravka_u_rs'">
                            <u>Strani drzavljanin bez boravka u RS</u>
                        </xsl:if>
                    </label><br/>

                    <label>
                        ЈМБГ:
                    </label><br/>
                    <label> <u><xsl:value-of select="/Interesovanje/Licne_informacije/Jmbg"/></u></label><br/>

                    <label>
                        Име:&#xa; <xsl:value-of select="/Interesovanje/Licne_informacije/Ime"/>
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Licne_informacije/Ime"/> </u></label><br/>

                    <label>
                        Презиме:&#xa;
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Licne_informacije/Prezime"/> </u></label><br/>

                    <label>
                        Адреса електронске поште:
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Email"/></u></label><br/>

                    <label>
                        Број мобилног телефона (навести број у формату 06X..... без размака и цртица):
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Broj_mobilnog"/></u></label><br/>

                    <label>
                        Број фиксног телефона (навести број у формату нпр. 011..... без размака и цртица):
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Licne_informacije/Kontakt/Broj_fiksnog"/> </u></label><br/>

                    <label>
                        Odabrana локацију где желите да примите вакцину (унесите општину):
                    </label><br/>
                    <label> <u> <xsl:value-of select="/Interesovanje/Lokacija_primanja_vakcine"/> </u></label><br/><br/>

                    <label>
                        Исказујем интересовање да примим искључиво вакцину следећих произвођача за
                        који Агенција за лекове и медицинска средства потврди безбедност, ефикасност и
                        квалитет и изда дозволу за употребу лека: &#xa; <xsl:value-of select="/Interesovanje/Proizvodjaci"/>
                    </label><br/><br/>

                    <label>
                        Да ли сте добровољни давалац крви? <xsl:value-of select="/Interesovanje/Licne_informacije/Davalac_krvi"/>
                    </label><br/><br/>
                    <label>
                        Дана   <xsl:value-of select="/Interesovanje/Datum_podnosenja_interesovanja"/>  године
                    </label><br/>

                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>