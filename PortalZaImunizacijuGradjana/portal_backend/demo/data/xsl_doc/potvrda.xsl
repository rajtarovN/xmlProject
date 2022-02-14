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
                                    margin-bottom="0.75in" margin-left="80pt" margin-right="80pt" />
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="zahtev-page">
                <fo:flow flow-name="xsl-region-body">

                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        ИНСТИТУТ ЗАЈАВНО ЗДРАВЛЈЕ СРБИЈЕ &#xa; „Др Милан Јовановић Батут"
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt"  text-align="center" margin-top="48pt">
                        INSTITUT ZA JAVNO ZDRAVLJE SRBIJE &#xa; „Dr Milan Jovanović Batut"
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt"  text-align="center" margin-top="48pt">
                        INSTITUTE OF PUBLIC HEALTH OF SERBIA ZA &#xa; „Dr Milan Jovanovic Batut"
                    </fo:block>

                    <fo:block font-family="Times New Roman" font-size="14pt" font-weight="bold" text-align="center" margin-top="48pt">
                        ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt"  text-align="center" margin-top="48pt">
                        POTVRDA O IZVRŠENOJ VAKCINACIJI PROTIV COVID-19
                    </fo:block>
                    <fo:block font-family="Times New Roman" font-size="14pt"  text-align="center" margin-top="48pt">
                        CONFIRMATION OF COVID-19 VACCINATION
                    </fo:block>


                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Име и презиме: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:ime"></xsl:value-of>
                        <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:prezime"></xsl:value-of>
                        &#xa;Ime i prezime / First and Last Name
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Датум рођења: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:datum_rodjenja"></xsl:value-of>
                        &#xa;Datum rođenja / Date of Birth
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Пол: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:pol"></xsl:value-of>
                        &#xa;Pol / Gender: TODOOOOO
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        ЈMБГ: <xsl:value-of select="/ns2:potvrda_o_vakcinaciji/ns2:licni_podaci/ns2:jmbg"></xsl:value-of>
                        &#xa;JMBG / Personal No.
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Датум даванја и број серије прве дозе вакцине:  серија:
                        Datum vakcinacije / Vaccination day
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Датум даванја и број серије друге дозе вакцине:  серија:
                        Datum vakcinacije / Vaccination day
                    </fo:block>
                    <fo:block  text-align="center" font-size="12pt" font-family="Times New Roman">
                        Здравствена установа која вакцинише:
                        Zdravstvena ustanova koja vakciniše / Health care institution of vaccination
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Datum TOODDDOOO
                    </fo:block>
                    <fo:block  text-align="left" font-size="12pt" font-family="Times New Roman">
                        Дана    године
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>