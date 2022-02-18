package com.example.sluzbenik_back.util;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class FusekiManager {
    /**
     *
     * @param rdfInputStream string rdf pretvoren u niz bajtova
     * @param NAMED_GRAPH    je oblika: zalbanacutanje/idDokumentaIzXmlBaze, npr:
     *                       zalbanacutanje/c115f225-592c-4478-8ede-c77a5bce74fb
     * @throws IOException
     */
    public void writeFuseki(InputStream rdfInputStream, String NAMED_GRAPH) throws IOException {
        AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();
        // Creates a default model
        Model model = ModelFactory.createDefaultModel();
        model.read(rdfInputStream, null);

        // out stream nam treba da bismo videli ispis na konzoli
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);
        System.out.println("[INFO] Rendering model as RDF/XML...");
        model.write(System.out, SparqlUtil.RDF_XML);

        String s = new String(out.toByteArray());

        /*
         * Create UpdateProcessor, an instance of execution of an UpdateRequest.
         * UpdateProcessor sends update request to a remote SPARQL update service.
         */
        UpdateRequest request = UpdateFactory.create();

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, fusekiConn.updateEndpoint);
        processor.execute();

        // Creating the first named graph and updating it with RDF data
        System.out.println("[INFO] Writing the triples to a named graph \"" + NAMED_GRAPH + "\".");
        String sparqlUpdate = SparqlUtil.insertData(fusekiConn.dataEndpoint + NAMED_GRAPH, new String(out.toByteArray()));
        System.out.println(sparqlUpdate);

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);
        processor = UpdateExecutionFactory.createRemote(update, fusekiConn.updateEndpoint);
        processor.execute();

    }

    /**
     *
     * @param uri je string i ima oblik
     *            zalbanacutanje/c115f225-592c-4478-8ede-c77a5bce74fb
     * @return
     * @throws IOException
     * @throws BadRequestException
     */
    public String readFileAsXML(String uri) throws IOException {
        AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();
        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph \"" + uri + "\".");
        String sparqlQuery = SparqlUtil.selectData(fusekiConn.dataEndpoint + uri, "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(fusekiConn.queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint
        ResultSet results = query.execSelect();

        // citanje rdfa u promenljivu tipa string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsXML(stream, results);
        String rdfString = new String(stream.toByteArray());

        query.close();
        System.out.println("[INFO] End.");

        return rdfString;

    }

    public String readFileAsJSON(String uri) throws IOException {
        AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();

        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph \"" + uri + "\".");
        String sparqlQuery = SparqlUtil.selectData(fusekiConn.dataEndpoint + uri, "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(fusekiConn.queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint
        ResultSet results = query.execSelect();

        // citanje rdfa u promenljivu tipa string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(stream, results);
        String rdfString = new String(stream.toByteArray());

        query.close();
        System.out.println("[INFO] End.");

        return rdfString;
    }
}

