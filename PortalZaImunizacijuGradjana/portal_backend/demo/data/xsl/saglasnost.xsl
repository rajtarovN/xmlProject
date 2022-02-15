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
                    .left1{
                    text-align: left;
                    font-size: 18px;
                    }
                    .left2{
                    text-align: left;
                    font-size: 14px;
                    }
                </style>
            </head>

            <body>
                <div class="left1"> <b> САГЛАСНОСТ ЗА СПРОВОЂЕЊЕ
                    ПРЕПОРУЧЕНЕ ИМУНИЗАЦИЈЕ  </b> </div>
                <div class="left2">(попуњава пацијент)</div><br/><br/>
                <div>
                    <label>
                       <b> Држављанство:</b> PITAJ ONOG KO JE OVO RADIOOOOOOOO
                    </label><br/>
                    <label>
                        <b> Презиме: </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Prezime"/>
                        <b>| Име: </b> <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Ime"/> <b> | Име родитеља: </b>
                        <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Ime_roditelja"/>
                    </label><br/>

                    <label>
                        <b> Пол: </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Pol"/>
                        <b> | Датум рођења: </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Datum_rodjenja"/>
                        <b> | Место: </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Mesto_rodjenja"/>

                    </label><br/>

                    <label>
                        <b> Адреса (улица и број):</b> <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Ulica"/>
                        <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Broj"/>
                        <b> Место/Насеље </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Mesto"/>
                    </label><br/>

                    <label>
                        <b> Општина/Град </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Grad"/>
                        <b> Тел. фиксни </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Fiksni_telefon"/>
                    </label><br/>

                    <label>
                        <b> Тел. мобилни </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Mobilni_telefon"/>
                        <b> | имејл</b> <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Email"/>
                    </label><br/>

                    <label>
                        <b> Занимање запосленог:</b> <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Zanimanje_zaposlenog"/>
                    </label><br/>

                    <label>
                        <b> Корисник установе соц. зашт </b><xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Radni_status"/>
                    </label><br/>

                    <label>
                        <b> Назив и општина седишта</b>
                    </label><br/>

                    <label>
                        <b> Изјављујем да: ПРОЦИТАЈЈЈЈЈЈЈЈ</b>
                    </label><br/>

                    <label>
                        Лекар ми је објаснио предности и ризике од спровођења активне/пасивне имунизације наведеним имунолошким
                        леком ПРОВЕРИ ПОТПИССССССССС и датум
                    </label><br/>

                </div>
                |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                <div>
                    <h2>ЕВИДЕНЦИЈА О ВАКЦИНАЦИЈИ ПРОТИВ COVID-19</h2>
                    <h4>(попуњава здравствени радник)</h4>
                    Здравствена установа Вакцинацијски пункт <br/>
                    Име, презиме, факсимил и бр. телефона лекара:<br/>
                    Пре давања вакцине прегледати особу и упознати је са користима и о могућим нежељеним реакцијама после
                    вакцинације. Обавезно уписати сваку дату вакцину и све тражене податке у овај образац и податке унети у лични
                    картон о извршеним имунизацијама и здравствени картон.<br/> TABELLAA
                </div>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>