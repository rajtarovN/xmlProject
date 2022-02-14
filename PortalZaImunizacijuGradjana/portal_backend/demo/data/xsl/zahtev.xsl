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


                <h2 class="add-space-after"> ЗАХТЕВ </h2>
                <h3 class="add-space-after"> за издавање дигиталног зеленог сертификата</h3>
                <div class="text-justify">   У складу са одредбом Републике Србије о издавању дигиталног зеленог
                    сертификата као потврде о извршеној вакцинацији против COVID-19, резултатима
                    тестирања на заразну болест SARS-CoV-2 или опоравку од болести COVID-19,
                    подносим захтев за издавање дигиталног зеленог сертификата.</div><br/><br/>
                <div>
                    Подносилац захтева:<br/><br/>
                    <label>Име и презиме:</label>
                    <label>
                        <xsl:value-of select="(/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Ime)"/>	&#160;
                        <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Prezime"></xsl:value-of>
                    </label><br/>

                    <label>Датум рођења:</label>
                    <label>
                         <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Datum_rodjenja"></xsl:value-of>
                    </label><br/>

                    <label>Пол:</label>
                    <label>
                         <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Pol"></xsl:value-of>
                    </label><br/>

                    <label>Јединствени матични број грађанина:</label>
                    <label>
                        <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Jmbg"></xsl:value-of>
                    </label><br/>

                    <label>Број пасоша:</label>
                    <label>
                        <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Broj_pasosa"></xsl:value-of>
                    </label><br/>

                    <label>Разлог за подношење захтева:</label><br/>
                    <label>
                        <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Podnosilac_zahteva/Razlog_podnosenja_zahteva"></xsl:value-of>
                    </label><br/>
                    <label class=" smallChar">(навести што прецизнији разлога за подношење захтева за издавање дигиталног пасоша)</label>
                    <br/><br/>
                    <label>
                        У <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Zaglavlje/Mesto_podnosenja_zahteva"></xsl:value-of>
                    </label><br/>
                    <label>
                        Дана <xsl:value-of select="/Zahtev_za_zeleni_sertifikat/Zaglavlje/Dan_podnosenja_zahteva"/>   године
                    </label>
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>