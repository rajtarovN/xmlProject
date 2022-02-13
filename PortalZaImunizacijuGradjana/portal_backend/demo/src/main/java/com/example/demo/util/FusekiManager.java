package com.example.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

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

        /*
         * Create UpdateProcessor, an instance of execution of an UpdateRequest.
         * UpdateProcessor sends update request to a remote SPARQL update service.
         */
        UpdateRequest request = UpdateFactory.create();

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, fusekiConn.updateEndpoint);
        processor.execute();

        // Creating the first named graph and updating it with RDF data
        System.out.println("[INFO] Writing the triples to a named graph \"" + NAMED_GRAPH + "\".");
        String sparqlUpdate = SparqlUtil.insertData(fusekiConn.dataEndpoint + NAMED_GRAPH, out.toString());
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
        String rdfString = stream.toString();

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
        String rdfString = stream.toString();

        query.close();
        System.out.println("[INFO] End.");

        return rdfString;
    }


    public List<String> query(String graphUri, String sparqlFilePath, List<String> queryParams) throws Exception {
        AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();
        queryParams.add(0, fusekiConn.dataEndpoint + graphUri);

        // Querying the named graph with a referenced SPARQL query
        System.out.println("[INFO] Loading SPARQL query from file \"" + sparqlFilePath + "\"");
        System.out.println(queryParams.toArray());
        String sparqlQuery = String.format(FileUtil.readFile(sparqlFilePath, StandardCharsets.UTF_8),
                queryParams.toArray());


        System.out.println(sparqlQuery);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(fusekiConn.queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint, iterate over the result set...
        System.out.println("[INFO] Showing the results for SPARQL query using the result handler.\n");
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;

        List<String> result = new ArrayList<String>();
        while (results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            // Retrieve variable bindings
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);

                result.add(varValue.toString().substring(varValue.toString().lastIndexOf("/") + 1));
            }
        }

        // Issuing the same query once again...

        // Create a QueryExecution that will access a SPARQL service over HTTP
        query = QueryExecutionFactory.sparqlService(fusekiConn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response as XML
        System.out.println("[INFO] Showing the results for SPARQL query in native SPARQL XML format.\n");
        results = query.execSelect();

        // ResultSetFormatter.outputAsXML(System.out, results);
        ResultSetFormatter.out(System.out, results);

        query.close();

        System.out.println("[INFO] End.");
        return result;
    }
}

