package com.example.sluzbenik_back;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.sluzbenik_back.util.InitXmlAndRdfDb;

@SpringBootApplication
@ImportResource({ "classpath*:cxf-servlet.xml" })
public class SluzbenikBackApplication {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException, IOException, SAXException, TransformerException {

		SpringApplication.run(SluzbenikBackApplication.class, args);
		//InitXmlAndRdfDb.inicijalizujXMLBazu();
		//InitXmlAndRdfDb.inicijalizujRDFBazu();
		System.out.println( "Hello World!" );

	}

	@Bean
	public ServletRegistrationBean<CXFServlet> cxfServlet() {
		CXFServlet cxfServlet = new CXFServlet();
		ServletRegistrationBean<CXFServlet> servletReg = new ServletRegistrationBean<CXFServlet>(cxfServlet, "/api/*");
		servletReg.setLoadOnStartup(1);
		return servletReg;
	}
}
