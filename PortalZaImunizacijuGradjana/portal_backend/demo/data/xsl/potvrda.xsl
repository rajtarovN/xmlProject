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

                <h2 class="add-space-after"> <u> ИНСТИТУТ ЗАЈАВНО ЗДРАВЛЈЕ СРБИЈЕ &#xa; „Др Милан Јовановић Батут"  </u> </h2>
                <h2 class="add-space-after"> INSTITUT ZA JAVNO ZDRAVLJE SRBIJE &#xa; „Dr Milan Jovanović Batut"</h2>
                <h2 class="add-space-after"> INSTITUTE OF PUBLIC HEALTH OF SERBIA ZA &#xa; „Dr Milan Jovanovic Batut"</h2>
                <div>   ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                    POTVRDA O IZVRŠENOJ VAKCINACIJI PROTIV COVID-19
                    CONFIRMATION OF COVID-19 VACCINATION</div><br/><br/>
                <div>


                    <label>
                        Име и презиме: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:ime"></xsl:value-of>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:prezime"></xsl:value-of>
                        &#xa;Ime i prezime / First and Last Name
                    </label><br/>

                    <label>
                        Датум рођења: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:datum_rodjenja"></xsl:value-of>
                        &#xa;Datum rođenja / Date of Birth
                    </label><br/>

                    <label>
                        Пол: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:pol"></xsl:value-of>
                        &#xa;Pol / Gender: TODOOOOO
                    </label><br/>

                    <label>
                        ЈMБГ: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:jmbg"></xsl:value-of>
                        &#xa;JMBG / Personal No.
                    </label><br/>

                    <label>
                        Датум даванја и број серије прве дозе вакцине:  серија:
                        Datum vakcinacije / Vaccination day
                    </label><br/>

                    <label>
                        Датум даванја и број серије друге дозе вакцине:  серија:
                        Datum vakcinacije / Vaccination day
                    </label><br/>
                    <label>
                        Здравствена установа која вакцинише:
                        Zdravstvena ustanova koja vakciniše / Health care institution of vaccination
                    </label>
                    <label>
                        Datum TOODDDOOO
                    </label>
                    <label>
                        Дана    године
                    </label>
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>