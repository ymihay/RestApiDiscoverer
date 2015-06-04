package com.smartbear.alertsite.service;

import com.eviware.soapui.impl.rest.*;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.SoapUIException;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;


/**
 * Created by yanamikhaylenko on 5/13/15.
 */
public class SoapUICreator {

    public void createSoapUIProject() throws IOException, SoapUIException, XmlException {
        // create new project
        WsdlProject project = new WsdlProject();



        RestService restService = new RestServiceFactory().createNew(project, "TEST_NAME");
        restService.addEndpoint("http://petstore.service.io/v2/service.json");

        // restService.addNewResource("name", "/v2/service.json");


        // get desired operation
        RestResource operation = restService.getOperationByName("MyOperation");
        RestMethod method = operation.addNewMethod("My request");

        // create a new empty request for that operation
        RestRequest request = method.addNewRequest("Request 1");


        // generate the request content from the schema
        request.setRequestContent(operation.createRequest(true));

        // submit the request
        //WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(), false);

        // wait for the response
        //Response response = submit.getResponse();

        //	print the response
        //String content = response.getContentAsString();
        //System.out.println(content);
        //assertNotNull( content );
        //assertTrue( content.indexOf( "404 Not Found" ) > 0  );

        project.saveAs("Test.xml");
    }

    public static void main(String ... args) {
        SoapUICreator creator = new SoapUICreator();
        try {
            creator.createSoapUIProject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SoapUIException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
    }
}
