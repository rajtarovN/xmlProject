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
                <h2 class="add-space-after"> <u> САГЛАСНОСТ ЗА СПРОВОЂЕЊЕ
                    ПРЕПОРУЧЕНЕ ИМУНИЗАЦИЈЕ  </u> </h2>
                <div>(попуњава пацијент)</div><br/><br/>
                <div>Држављанство: PITAJ ONOG KO JE OVO RADIOOOOOOOO

                    <label>
                        Презиме: <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Prezime"></xsl:value-of>
                         | Име: <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Ime"></xsl:value-of> | Име родитеља:
                        <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Ime_roditelja"></xsl:value-of>
                    </label><br/>

                    <label>
                        Пол: <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Pol"></xsl:value-of>
                       | Датум рођења:<xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Datum_rodjenja"></xsl:value-of>
                       | Место:<xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Mesto_rodjenja"></xsl:value-of>

                    </label><br/>

                    <label>
                        Адреса (улица и број): <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Ulica"></xsl:value-of>
                        <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Broj"></xsl:value-of>
                    </label><br/>

                    <label>
                        Место/Насеље <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Mesto"></xsl:value-of>
                    </label><br/>

                    <label>
                        Општина/Град <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Adresa/Grad"></xsl:value-of>
                    </label><br/>

                    <label>
                        Тел. фиксни <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Fiksni_telefon"></xsl:value-of>
                    </label><br/>

                    <label>
                        Тел. мобилни <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Mobilni_telefon"></xsl:value-of>
                    </label><br/>

                    <label>
                        имејл <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Kontakt_informacije/Email"></xsl:value-of>
                    </label><br/>

                    <label>
                        Занимање запосленог: <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Zanimanje_zaposlenog"></xsl:value-of>
                    </label><br/>

                    <label>
                        Корисник установе соц. зашт <xsl:value-of select="/Saglasnost/Pacijent/Licni_podaci/Radni_status"></xsl:value-of>
                    </label><br/>

                    <label>
                        Назив и општина седишта
                    </label><br/>

                    <label>
                        Изјављујем да: ПРОЦИТАЈЈЈЈЈЈЈЈ
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