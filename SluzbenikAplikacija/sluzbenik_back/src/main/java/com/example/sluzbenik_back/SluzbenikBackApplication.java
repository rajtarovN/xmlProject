package com.example.sluzbenik_back;

import com.example.sluzbenik_back.util.InitXmlAndRdfDb;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@SpringBootApplication
@ImportResource({ "classpath*:cxf-servlet.xml" })
public class SluzbenikBackApplication {

	public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, JAXBException, XMLDBException, ClassNotFoundException {
		SpringApplication.run(SluzbenikBackApplication.class, args);
		System.out.println( "Hello World!" );
		//InitXmlAndRdfDb.inicijalizujXMLBazu();
	}

	@Bean
	public ServletRegistrationBean<CXFServlet> cxfServlet() {
		CXFServlet cxfServlet = new CXFServlet();
		ServletRegistrationBean<CXFServlet> servletReg = new ServletRegistrationBean<CXFServlet>(cxfServlet, "/api/*");
		servletReg.setLoadOnStartup(1);
		return servletReg;
	}
}
