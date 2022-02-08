package com.example.demo;

import com.example.demo.util.AuthenticationManagerFuseki;
import com.example.demo.util.MetadataExtractor;
import com.example.demo.util.SparqlUtil;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.*;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws TransformerException, SAXException, IOException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println( "Hello World!" );
		//runRDF();
	}

	public static void runRDF() throws IOException, SAXException, TransformerException {
		AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();
		String xmlFilePath = "data/xml/saglasnost1.xml";
		String rdfFilePath = "gen/saglasnost1.rdf";

		MetadataExtractor metadataExtractor = new MetadataExtractor();

		System.out.println("[INFO] Extracting metadata from RDFa attributes...");
		metadataExtractor.extractMetadata(
				new FileInputStream(new File(xmlFilePath)),
				new FileOutputStream(new File(rdfFilePath)));

		Model model = ModelFactory.createDefaultModel();
		model.read(rdfFilePath);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out, SparqlUtil.NTRIPLES);

		System.out.println("[INFO] Extracted metadata as RDF/XML...");
		//model.write(System.out, SparqlUtil.RDF_XML);

		String graphUri = "/lista_saglasnosti";
		System.out.println("[INFO] Populating named graph \"" + graphUri + "\" with extracted metadata.");
		String sparqlUpdate = SparqlUtil.insertData(fusekiConn.dataEndpoint + graphUri, out.toString());
		System.out.println(sparqlUpdate);

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, fusekiConn.updateEndpoint);
		processor.execute();
		model.close();
	}

}
