package Repo;

import Domain.Student;
import Validator.ValidationException;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class StudentXMLRepository extends AbstractCRUDRepository<Integer, Student> {
    private String xmlFile;

    public StudentXMLRepository(Validator v, String xmlFile) {
        super(v);
        this.xmlFile = xmlFile;
        loadData();
    }

    void loadData() {
        try {
            Document docXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            Element root = docXml.getDocumentElement();
            NodeList list = root.getChildNodes();
            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Element.ELEMENT_NODE) {
                    try {
                        super.save(getFromNode((Element)node));
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            Document docXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = docXml.createElement("studenti");
            docXml.appendChild(root);

            entities.values().forEach(s -> root.appendChild(getFromStudent(s, docXml)));
            Transformer transXml = TransformerFactory.newInstance().newTransformer();
            transXml.setOutputProperty(OutputKeys.INDENT,"yes");
            transXml.transform(new DOMSource(docXml), new StreamResult(xmlFile));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private Element createElement(Document docXml, String tag, String value) {
        Element el = docXml.createElement(tag);
        el.setTextContent(value);
        return el;
    }

    private Element getFromStudent(Student s, Document docXml) {
        Element e = docXml.createElement("student");
        e.setAttribute("id", s.getID().toString());
//        docXml.appendChild(e);
        e.appendChild(createElement(docXml, "nume", s.getNume()));
        e.appendChild(createElement(docXml, "grupa", s.getGrupa().toString() ));
        e.appendChild(createElement(docXml, "email", s.getEmail()));
        return e;
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        super.save(entity);
        saveToFile();
        return entity;
    }

    @Override
    public Student delete(Integer id) throws ValidationException {
        Student s=super.delete(id);
        saveToFile();
        return s;
    }

    @Override
    public Student update(Student entity) throws ValidationException {
        super.update(entity);
        saveToFile();
        return entity;
    }

    private Student getFromNode(Element node) {
        String id = node.getAttributeNode("id").getValue();
        String nume = node.getElementsByTagName("nume").item(0).getTextContent();
        String grupa = node.getElementsByTagName("grupa").item(0).getTextContent();
        String email = node.getElementsByTagName("email").item(0).getTextContent();
        return new Student(Integer.parseInt(id), nume, Integer.parseInt(grupa), email);
    }
}
