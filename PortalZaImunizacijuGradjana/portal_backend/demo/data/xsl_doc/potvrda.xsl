<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ns2="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji"
        xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master
                        master-name="zahtev-page">
                    <fo:region-body margin-top="0.75in"
                                    margin-bottom="0.75in" margin-left="80pt" margin-right="80pt"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="zahtev-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="right">
                        <fo:block font-family="Times New Roman" font-size="10pt" font-weight="bold" text-align="center">
                            ИНСТИТУТ ЗАЈАВНО ЗДРАВЛЈЕ СРБИЈЕ &#xa; „Др Милан Јовановић Батут"
                        </fo:block>
                        <fo:block font-family="Times New Roman" font-size="10pt" text-align="center">
                            INSTITUT ZA JAVNO ZDRAVLJE SRBIJE &#xa; „Dr Milan Jovanović Batut"
                        </fo:block>
                        <fo:block font-family="Times New Roman" font-size="10pt" text-align="center">
                            INSTITUTE OF PUBLIC HEALTH OF SERBIA ZA &#xa; „Dr Milan Jovanovic Batut"
                        </fo:block>
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center">
                        ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="10pt" text-align="center">
                        POTVRDA O IZVRŠENOJ VAKCINACIJI PROTIV COVID-19
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="10pt" text-align="center">
                        CONFIRMATION OF COVID-19 VACCINATION
                    </fo:block>


                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Име и презиме:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:ime"/>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:prezime"/>
                        <fo:block font-size="10pt">Ime i prezime / First and Last Name</fo:block>
                    </fo:block>

                    <fo:block font-size="5pt">&#160;</fo:block>

                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Датум рођења:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:datum_rodjenja"/>
                        <fo:block font-size="10pt">Datum rođenja / Date of Birth</fo:block>
                    </fo:block>

                    <fo:block font-size="5pt">&#160;</fo:block>

                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Пол:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:pol"/>
                        <fo:block font-size="10pt">Pol / Gender</fo:block>
                    </fo:block>

                    <fo:block font-size="5pt">&#160;</fo:block>

                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">ЈMБГ:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:jmbg"/>
                        <fo:block font-size="10pt">JMBG / Personal No.</fo:block>
                    </fo:block>

                    <fo:block font-size="5pt">&#160;</fo:block>


                    <xsl:for-each select="/ns2:potvrda_o_vakcinaciji/ns2:vakcinacija/ns2:doze/ns2:doza">
                        <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                            <fo:block font-weight="bold">Датум даванја и број серије вакцине:</fo:block>
                            <xsl:value-of select="ns2:datum_davanja"/>
                            <fo:block font-weight="bold">серија:</fo:block>
                            <xsl:value-of select="ns2:broj_serije"/> <xsl:value-of select="ns2:naziv_vakcine"/>
                            <fo:block font-size="10pt">Datum vakcinacije Vaccination day</fo:block>
                        </fo:block>

                    </xsl:for-each>


                    <fo:block font-size="5pt">&#160;</fo:block>

                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Здравствена установа која вакцинише:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:zdravstvena_ustanova"/>
                        <fo:block font-size="10pt">Zdravstvena ustanova koja vakciniše Health care institution of
                            vaccination
                        </fo:block>
                    </fo:block>

                    <fo:block font-size="5pt">&#160;</fo:block>

                    <fo:block text-align="left" font-size="12pt" font-family="Times New Roman">
                        <fo:block font-weight="bold">Датум издаванја потврде:</fo:block>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:datum_izdavanja"/>
                        <fo:block font-size="10pt">Datum izdavanja potvrde / Confirmation Release Date</fo:block>

                    </fo:block>
<!--                    TODO isidora qr kod-->
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>