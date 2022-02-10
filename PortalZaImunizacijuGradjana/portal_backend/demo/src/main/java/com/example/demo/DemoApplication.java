package com.example.demo;

import com.example.demo.util.InitXmlAndRdfDb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, JAXBException, XMLDBException, ClassNotFoundException, TransformerException, SAXException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println( "Hello World!" );
		//InitXmlAndRdfDb.inicijalizujXMLBazu();
		//InitXmlAndRdfDb.inicijalizujRDFBazu();
	}

}
