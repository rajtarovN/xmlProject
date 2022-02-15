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
                    <fo:block  font-size="16pt" font-family="Times New Roman" text-align="left">
                        (попуњава пацијент)
                    </fo:block>

                    <fo:block>&#160;</fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Држављанство:</fo:block> <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent[0]"/>
                    </fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Презиме: </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Prezime"/>
                        <fo:inline font-weight="bold"> | Име: </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Ime"/>
                        <fo:inline font-weight="bold"> | Име родитеља:</fo:inline> <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Ime_roditelja"/>

                    </fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold"> Пол:</fo:inline> <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Pol"/>
                        <fo:inline font-weight="bold"> | Датум рођења:</fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Datum_rodjenja"/>
                        <fo:inline font-weight="bold"> | Место:</fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Mesto_rodjenja"/>
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Адреса (улица и број): </fo:inline>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Ulica"/>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Broj"/>
                        <fo:inline font-weight="bold">| Место/Насеље </fo:inline>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Mesto"/>
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Општина/Град </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Adresa/ns2:Grad"/>
                        <fo:inline font-weight="bold">| Тел. фиксни </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Fiksni_telefon"/>
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Тел. мобилни </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Mobilni_telefon"/>
                        <fo:inline font-weight="bold">| имејл </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Kontakt_informacije/ns2:Email"/>
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Радни статус </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Radni_status"/>
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Занимање запосленог: </fo:inline><xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Zanimanje_zaposlenog"/>
                    </fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        <fo:inline font-weight="bold">Корисник установе соц. зашт </fo:inline>
                        <xsl:if test="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Socijalna_zastita/ns2:Naziv_sedista!=''">
                            Da
                        </xsl:if>
                        <xsl:if test="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Socijalna_zastita/ns2:Naziv_sedista=''">
                            Ne
                        </xsl:if>

                        <fo:inline font-weight="bold">| Назив и општина седишта</fo:inline>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Socijalna_zastita/ns2:Naziv_sedista"/>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Licni_podaci/ns2:Socijalna_zastita/ns2:Opstina_sedista"/>
                        <!--                    </xsl:if>-->
                    </fo:block>

                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        Изјављујем да: <xsl:value_of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Saglasnost_pacijenta"/>
                        <xsl:value_of select="/ns2:Saglasnost/ns2:Pacijent/ns2:Saglasnost_pacijenta/@Saglasan='false'">NEEE</xsl:value_of>
                    </fo:block>
                    <fo:block  text-align="justify" font-size="12pt" font-family="Times New Roman">
                        Лекар ми је објаснио предности и ризике од спровођења активне/пасивне имунизације наведеним имунолошким
                        леком ПРОВЕРИ ПОТПИССССССССС и датум
                    </fo:block>
                    <fo:block  text-align="justify" font-size="8pt" font-family="Times New Roman">
                        |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                    </fo:block>
                    <fo:block  text-align="center" font-size="18pt" font-family="Times New Roman">
                        ЕВИДЕНЦИЈА О ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                    </fo:block>
                    <fo:block  text-align="center" font-size="10pt" font-family="Times New Roman">
                        (попуњава здравствени радник)
                    </fo:block><fo:block>&#160;</fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Здравствена установа <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Zdravstvena_ustanova"/>
                        Вакцинацијски пункт <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Vakcinacijski_punkt"/>
                    </fo:block><fo:block>&#160;</fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Име, презиме, факсимил и бр. телефона лекара:
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Lekar/ns2:Ime"/>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Lekar/ns2:Prezime"/>
                        <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Lekar/ns2:Telefon"/>
                    </fo:block><fo:block>&#160;</fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Пре давања вакцине прегледати особу и упознати је са користима и о могућим нежељеним реакцијама после
                        вакцинације. Обавезно уписати сваку дату вакцину и све тражене податке у овај образац и податке унети у лични
                        картон о извршеним имунизацијама и здравствени картон.</fo:block>


                    <fo:block>
                        <fo:table font-family="serif" margin="5px auto 50px auto" border="1px">
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid">

                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Назив
                                            вакцине
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Датум давања
                                            вакцине
                                            (V1 i V2)
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Начин
                                            давања
                                            вакцине
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Екстремитет
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Серија
                                            вакцине
                                            (лот)
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Произвођач
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt" font-weight="bold">
                                            Нежељена
                                            реакција
                                        </fo:block>
                                    </fo:table-cell>

                                </fo:table-row>


                                <xsl:for-each select="ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Vakcine/ns2:Vakcina">
                                    <fo:table-row border="1px solid">
                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                <xsl:value-of select="ns2:Naziv"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                <xsl:value-of select="ns2:Datum_davanja"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                <xsl:value-of select="ns2:Nacin_davanja"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                <xsl:value-of select="ns2:Ekstremiter"/>
                                            </fo:block>
                                        </fo:table-cell><fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                            <xsl:value-of select="ns2:Serija"/>
                                        </fo:block>
                                    </fo:table-cell><fo:table-cell padding="10px">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                            <xsl:value-of select="ns2:Proizvodjac"/>
                                        </fo:block>
                                    </fo:table-cell>
                                        <fo:table-cell padding="10px">
                                            <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                                <xsl:value-of select="ns2:Nezeljena_reakcija"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>

                                <fo:table-row border="1px solid">
                                    <fo:table-cell padding="10px" number-columns-spanned="6">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                            Привремене контраиндикације
                                            (датум утврђивања и дијагноза):
                                            <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Vakcine/ns2:Privremene_kontraindikacije/ns2:Datum_utvrdjivanja"/> |
                                            <xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Vakcine/ns2:Privremene_kontraindikacije/ns2:Dijagnoza"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid">
                                    <fo:table-cell padding="10px" number-columns-spanned="6">
                                        <fo:block font-family="Times New Roman" text-align="center"	font-size="12pt">
                                            Одлука комисије за трајне контраиндикације<xsl:value-of select="/ns2:Saglasnost/ns2:Evidencija_o_vakcinaciji/ns2:Vakcine/ns2:Odluka_komisije_za_trajne_kontraindikacije"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                            </fo:table-body>
                        </fo:table>
                    </fo:block>


                    Напомена: Образац се чува као део медицинске документације пацијента
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>