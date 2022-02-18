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
                    .corner-title{
                    text-align: center;
                    text-size: 14px;
                    }
                    .corner-title2{
                    text-align: center;
                    text-size: 12px;
                    }
                    .corner{
                    position:absolute;
                    top:15;
                    right:15;
                    }
                    .centerD{
                    text-align: center;
                    }
                    .smallF {
                    font-size:16px
                    }
                    .bigF{
                    font-size:18px;}
                </style>
            </head>

            <body>
                <div class="corner">
                    <div class="corner-title"> <b>ИНСТИТУТ ЗАЈАВНО ЗДРАВЛЈЕ СРБИЈЕ <br/> „Др Милан Јовановић Батут" </b>  </div>
                    <div  class="corner-title2"> INSTITUT ZA JAVNO ZDRAVLJE SRBIJE <br/> „Dr Milan Jovanović Batut"</div>
                    <div class="corner-title2"> INSTITUTE OF PUBLIC HEALTH OF SERBIA ZA <br/> „Dr Milan Jovanovic Batut"</div>
                </div>

                <div class="centerD">   <b>ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19 </b><br/>
                    <div class="smallF">POTVRDA O IZVRŠENOJ VAKCINACIJI PROTIV COVID-19<br/>
                        CONFIRMATION OF COVID-19 VACCINATION</div></div><br/><br/>
                <div>


                    <label>
                        <div class="bigF"><b>Име и презиме:</b> <xsl:value-of select="/potvrda_o_vakcinaciji/licni_podaci/ime"/>&#160;
                        <xsl:value-of select="/potvrda_o_vakcinaciji/licni_podaci/prezime"/></div>
                        <div class="smallF">Ime i prezime / First and Last Name</div>
                    </label><br/>

                    <label>
                        <div class="bigF"><b>Датум рођења:</b> <xsl:value-of select="/potvrda_o_vakcinaciji/licni_podaci/datum_rodjenja"/></div>
                        <div class="smallF">Datum rođenja / Date of Birth</div>
                    </label><br/>

                    <label>
                        <div class="bigF"><b>Пол:</b> <xsl:value-of select="/potvrda_o_vakcinaciji/licni_podaci/pol"/></div>
                        <div class="smallF">Pol / Gender:</div>
                    </label><br/>

                    <label>
                        <div class="bigF"><b>ЈMБГ:</b> <xsl:value-of select="/potvrda_o_vakcinaciji/licni_podaci/jmbg"/></div>
                        <div class="smallF">JMBG / Personal No.</div>
                    </label><br/>

                    <xsl:for-each select="/potvrda_o_vakcinaciji/vakcinacija/doze/doza">
                        <div class="bigF"><b>Датум даванја и број серије вакцине:</b>
                        <xsl:value-of select="datum_davanja"/> &#160; серија:
                        <xsl:value-of select="broj_serije"/>  <xsl:value-of select="naziv_vakcine"/></div>
                        <div class="smallF">Datum vakcinacije Vaccination day</div>
                        <br/>
                    </xsl:for-each>


                    <label>
                        <div class="bigF"><b>Здравствена установа која вакцинише:</b>
                            <xsl:value-of select="/potvrda_o_vakcinaciji/zdravstvena_ustanova"/>
                        </div>
                        <div class="smallF">Zdravstvena ustanova koja vakciniše / Health care institution of vaccination</div>
                    </label><br/>
                    <label>
                        <div class="bigF"><b>Датум издаванја потврде:</b>
                         <xsl:value-of select="/potvrda_o_vakcinaciji/datum_izdavanja"/></div>
                        <div class="smallF">Datum izdavanja potvrde / Confirmation Release Date</div>
                    </label><br/>
<!--todo isidora qr kod-->
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>