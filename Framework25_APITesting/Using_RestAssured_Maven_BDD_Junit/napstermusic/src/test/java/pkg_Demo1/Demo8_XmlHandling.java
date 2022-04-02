package pkg_Demo1;

import com.jayway.restassured.path.xml.XmlPath;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Demo8_XmlHandling {

    @Test
    public void Test_Demo81_XmlUpdater() throws Exception{
        System.out.println("Test_Demo81_XmlUpdater=========================");

/*

<?xml version="1.0" encoding="UTF-8"?>
<root>
   <email emergencycontact="9876543210" empid="221221">sherlock@holmes.com</email>
   <fullname salute="Mr" gender="m" dateofbirth="2000-01-01">Sherlock Holmes</fullname>
   <address>
      <city>london</city>
      <country>united kingdom</country>
      <housenum>221b</housenum>
      <street>baker street</street>
   </address>
   <job>
      <department>secret service</department>
      <designation>detective</designation>
      <location>england</location>
   </job>
</root>

*/

        File inputFile    = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\dummy.xml");
        SAXReader reader  = new SAXReader();
        Document document = reader.read(inputFile);
        Node node         = null;



        // Updating Node value
        node = document.selectSingleNode("root/address/country");
        node.setText("Germany");

        // Removing Node
        node = document.selectSingleNode("root/job/location");
        node.detach();

        // Add Node and set a value of that node
        node = document.selectSingleNode("root/job");
        Element element = (Element) node;
        element.addElement("skills").setText("Java");




        // Updating Attribute value
        node = document.selectSingleNode("root/email");
        Element element2 = (Element) node;
        element2.setAttributeValue("empid","999000");

        // Removing Attribute
        node = document.selectSingleNode("root/fullname");
        Element element3 = (Element) node;
        element3.attribute("gender").detach();

        // Add Attribute
        node = document.selectSingleNode("root/fullname");
        Element element4 = (Element) node;
        element4.addAttribute("married","no");

        // Pretty print XML
        System.out.println(document.asXML());

        System.out.println("Test_Demo81_XmlUpdater=========================");
        System.out.println("\n\n");
    }

    @Test
    public void Test_Demo82_XmlParsing() throws Exception{
        System.out.println("Test_Demo82_XmlParsing=========================");
        System.out.println("\n");

        File inputFile    = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\dummy.xml");
        SAXReader reader  = new SAXReader();
        Document document = reader.read(inputFile);
        Node node         = null;
        XmlPath xmlPath   = new XmlPath(document.asXML().replace("\n","")).setRoot("root");

        // if Node exists
        if( ! xmlPath.getString("address.country").isEmpty()){
            System.out.println("Following Node exists          : " + "address.country");
        }
        if( xmlPath.getString("address.district").isEmpty()){
            System.out.println("Following Node does NOT exists : " + "address.district");
        }

        // get Node text
        System.out.println("Node text for address.country  : " + xmlPath.getString("address.country"));
        System.out.println("Node text for address.district : " + xmlPath.getString("address.district"));
        System.out.println("\n");


        // if attribute in a Node exists
        if( ! xmlPath.getString("email.@empid").isEmpty()){
            System.out.println("Following Attribute exists          : " + "email.@empid");
        }
        if( xmlPath.getString("email.@bloodgroup").isEmpty()){
            System.out.println("Following Attribute does NOT exists : " + "email.@bloodgroup");
        }

        // get attribute value of a Node
        System.out.println("Attribute value of email.@empid is  : " + xmlPath.getString("email.@empid"));
        System.out.println("Attribute value of email.@bloodgroup is: " + xmlPath.getString("email.@bloodgroup"));

        System.out.println("\n\n");
        System.out.println("Test_Demo82_XmlParsing=========================");
    }


}
