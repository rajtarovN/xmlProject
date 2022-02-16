package com.example.demo;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.util.InitXmlAndRdfDb;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@SpringBootApplication
@ImportResource({ "classpath*:cxf-servlet.xml" })
public class DemoApplication {


	public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, JAXBException, XMLDBException, ClassNotFoundException, TransformerException, SAXException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println( "Hello World!" );
		//InitXmlAndRdfDb.inicijalizujXMLBazu();
		//InitXmlAndRdfDb.inicijalizujRDFBazu();
	}

	@Bean
	public ServletRegistrationBean<CXFServlet> cxfServlet() {
		CXFServlet cxfServlet = new CXFServlet();
		ServletRegistrationBean<CXFServlet> servletReg = new ServletRegistrationBean<CXFServlet>(cxfServlet, "/api/*");
		servletReg.setLoadOnStartup(1);
		return servletReg;
	}
}
