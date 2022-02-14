<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju"
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

                    <fo:block font-family="Times New Roman" font-size="20pt" font-weight="bold" text-align="left" margin-top="48pt">
                        САГЛАСНОСТ ЗА СПРОВОЂЕЊЕ
                        ПРЕПОРУЧЕНЕ ИМУНИЗАЦИЈЕ
                    </fo:block>
                    <fo:block  text-align="center" font-size="16pt" font-family="Times New Roman">
                        (попуњава пацијент)
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        Државлјанство: PITAJ ONOG KO JE OVO RADIOOOOOOOO
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Презиме: <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Prezime"></xsl:value-of>
                        Име: <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Ime"></xsl:value-of> Име родитеља:
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Ime_roditelja"></xsl:value-of>

                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Пол: <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Pol"></xsl:value-of>
                        Датум рођења:<xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Datum_rodjenja"></xsl:value-of>
                        Место:<xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Mesto_rodjenja"></xsl:value-of>
                    </fo:block>

                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Адреса (улица и број): <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Ulica"></xsl:value-of>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Broj"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Место/Насеље <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Mesto"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Општина/Град <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Grad"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Тел. фиксни <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Fiksni_telefon"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Тел. мобилни <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Mobilni_telefon"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        имејл <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Email"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Радни статус <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Radni_status"></xsl:value-of>
                    </fo:block>

                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Занимање запосленог: <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Zanimanje_zaposlenog"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Корисник установе соц. зашт <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Radni_status"></xsl:value-of>
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Назив и општина седишта
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Изјављујем да: ПРОЦИТАЈЈЈЈЈЈЈЈ
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Лекар ми је објаснио предности и ризике од спровођења активне/пасивне имунизације наведеним имунолошким
                        леком ПРОВЕРИ ПОТПИССССССССС и датум
                    </fo:block>
                    <fo:block  text-align="left" font-size="8pt" font-family="Times New Roman">
                        |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                    </fo:block>
                    <fo:block  text-align="left" font-size="18pt" font-family="Times New Roman">
                        ЕВИДЕНЦИЈА О ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                    </fo:block>
                    <fo:block  text-align="left" font-size="10pt" font-family="Times New Roman">
                        (попуњава здравствени радник)
                    </fo:block>
                    <fo:block  text-align="left" font-size="10pt" font-family="Times New Roman">
                        Здравствена установа Вакцинацијски пункт
                    </fo:block>
                    <fo:block  text-align="left" font-size="10pt" font-family="Times New Roman">
                        Име, презиме, факсимил и бр. телефона лекара:
                    </fo:block>
                    Пре давања вакцине прегледати особу и упознати је са користима и о могућим нежељеним реакцијама после
                    вакцинације. Обавезно уписати сваку дату вакцину и све тражене податке у овај образац и податке унети у лични
                    картон о извршеним имунизацијама и здравствени картон.

                    TABELAAAAAA
                    Напомена: Образац се чува као део медицинске документације пацијента
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>