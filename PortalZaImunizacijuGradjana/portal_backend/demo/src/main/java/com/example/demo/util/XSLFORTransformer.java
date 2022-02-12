package tim1.sluzbenik.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.xalan.processor.TransformerFactoryImpl;

import static com.example.demo.util.PathConstants.FOP_XCONF;

public class XSLFORTransformer {

    private FopFactory fopFactory;

    private TransformerFactory transformerFactory;

    private DocumentBuilderFactory documentFactory;

    public XSLFORTransformer() throws SAXException, IOException {
        // Initialize FOP factory object
        fopFactory = FopFactory.newInstance(new File(FOP_XCONF));

        // Setup the XSLT transformer factory
        transformerFactory = new TransformerFactoryImpl();

        /* Inicijalizacija DOM fabrike */
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);

        /* Inicijalizacija Transformer fabrike */
        transformerFactory = TransformerFactory.newInstance();
    }

    public Document buildDocument(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(new StringReader(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean generateHTML(String xmlPath, String output_file, String xsl_path) {
        try {
            StreamSource transformSource = new StreamSource(new File(xsl_path));
            Transformer transformer = transformerFactory.newTransformer(transformSource);

            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

            DOMSource source = new DOMSource(buildDocument(xmlPath));
            StreamResult result = new StreamResult(new FileOutputStream(output_file));
            transformer.transform(source, result);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generatePDF(String xml_path, String output_file, String xsl_path) {
        try {
            File xslFile = new File(xsl_path);

            StreamSource transformSource = new StreamSource(xslFile);

            StringReader r = new StringReader(xml_path);

            StreamSource source = new StreamSource(r);

            FOUserAgent userAgent = fopFactory.newFOUserAgent();

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            System.out.println(transformSource.getInputStream());
            Transformer transformer = transformerFactory.newTransformer(transformSource);

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(source, res);

            File pdfFile = new File(output_file);

            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
            out.write(outStream.toByteArray());
            out.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
