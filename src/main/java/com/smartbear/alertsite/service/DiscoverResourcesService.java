package com.smartbear.alertsite.service;

import com.smartbear.alertsite.model.ParameterPlace;
import com.wordnik.swagger.models.*;
import com.wordnik.swagger.models.parameters.Parameter;
import com.wordnik.swagger.models.properties.Property;
import io.swagger.parser.SwaggerParser;
import org.springframework.http.HttpMethod;

import java.net.URL;
import java.util.*;

import static com.smartbear.alertsite.model.ParameterPlace.*;
import static org.springframework.http.HttpMethod.*;

/**
 * Created by yanamikhaylenko on 5/13/15.
 */

public class DiscoverResourcesService {
    private String pathToSwaggerApiDocs;
    private Swagger swagger;

    public DiscoverResourcesService(String pathToSwaggerApiDocs) {
        // TODO: Check input params
        this.pathToSwaggerApiDocs = pathToSwaggerApiDocs;
        this.swagger = new SwaggerParser().read(pathToSwaggerApiDocs);
    }

    public String getPathToSwaggerApiDocs() {
        return pathToSwaggerApiDocs;
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public Map<String, List<String>> getAllResources() {
        Map<String, Path> paths = swagger.getPaths();
        Set<Map.Entry<String, Path>> entrySet = paths.entrySet();

        Map<String, List<String>> discoveredResources = new HashMap<String, List<String>>();

        for (Map.Entry<String, Path> entry : entrySet) {
            String pathId = entry.getKey();
            Path path = entry.getValue();

            List<String> operations = new ArrayList<String>();

            System.out.println("\t Resource path : " + pathId);

            Operation getOperation = path.getGet();
            System.out.println(swagger.getSchemes() + "YAEH!");
            if (getOperation != null) {
                operations.add(String.valueOf(GET));
                System.out.println("\t \t Method Type : GET");
                printOperationDetails(getOperation);
            }
            Operation postOperation = path.getPost();
            if (postOperation != null) {
                operations.add(String.valueOf(POST));
                System.out.println("\t \t Method Type : POST");
                printOperationDetails(postOperation);
            }

            Operation putOperation = path.getPut();
            if (putOperation != null) {
                operations.add(String.valueOf(PUT));
                System.out.println("\t \t Method Type : PUT");
                printOperationDetails(putOperation);
            }

            Operation deleteOperation = path.getDelete();
            if (deleteOperation != null) {
                operations.add(String.valueOf(DELETE));
                System.out.println("\t \t Method Type : DELETE");
                printOperationDetails(deleteOperation);
            }

            Operation optionsOperation = path.getOptions();
            if (optionsOperation != null) {
                operations.add(String.valueOf(OPTIONS));
                System.out.println("\t \t Method Type : OPTIONS");
                printOperationDetails(optionsOperation);
            }

            /*Operation patchOperation = path.getPatch();
            if (patchOperation != null) {
                operations.add(String.valueOf(HttpMethodType.PATCH));
                System.out.println("\t \t Method Type : PATCH");
                printOperationDetails(patchOperation);
            }*/

            discoveredResources.put(pathId, operations);

        }
        return discoveredResources;
    }

    public Map<String, Model> getDefinitions() {
        if (swagger == null) {
            return null;
        }
        return swagger.getDefinitions();
    }

    public Model getDefinitionByName(String name) {
        // TODO: check defName on null
        Map<String, Model> definitions = swagger.getDefinitions();
        if (definitions != null) {
            return definitions.get(name);
        }
        return null;
    }

    public Map<String, Property> getDefinitionProperties(String name) {
        // TODO: check defName on null

        Model model = getDefinitionByName(name);
        if (model != null) {
            return model.getProperties();
        }
        return null;
    }


    public Operation getResourceByPathAndHttpMethod(HttpMethod httpMethod, String pathId) {
        // TODO: add checks on null
        Operation operation = null;
        Map<String, Path> paths = swagger.getPaths();
        if (!pathId.startsWith("/")) {
            pathId = "/" + pathId;
        }


        Path path = paths.get(pathId);
        System.out.println("HEY " + pathId);

        if (path != null) {
            System.out.println("Im here! " + httpMethod);
            switch (httpMethod) {
                case GET:
                    operation = path.getGet();
                    break;
                case POST:
                    operation = path.getPost();
                    break;
                case PUT:
                    operation = path.getPut();
                    break;
                case DELETE:
                    operation = path.getDelete();
                    break;
                case OPTIONS:
                    operation = path.getOptions();
                    break;
                case PATCH:
                    operation = path.getPatch();
                    break;
            }
        } else {
            //TODO:add write to debug log
        }
        //System.out.println("OP! " + operation);
        //generateDummyData(httpMethod, pathId);
        return operation;
    }

    public String getResourceOperationId(HttpMethod httpMethod, String pathId) {
        Operation operation = getResourceByPathAndHttpMethod(httpMethod, pathId);
        return (operation != null) ? operation.getOperationId() : null;
    }

    public List<Parameter> getResourceParameters(HttpMethod httpMethod, String pathId) {
        Operation operation = getResourceByPathAndHttpMethod(httpMethod, pathId);
        return (operation != null) ? operation.getParameters() : null;
    }

    public Map<String, Response> getResourceResponses(HttpMethod httpMethod, String pathId) {
        Operation operation = getResourceByPathAndHttpMethod(httpMethod, pathId);
        return (operation != null) ? operation.getResponses() : null;

    }

    public List<String> getResourceProduces(HttpMethod httpMethod, String pathId) {
        Operation operation = getResourceByPathAndHttpMethod(httpMethod, pathId);
        return (operation != null) ? operation.getProduces() : null;
    }

    public List<String> getResourceConsumes(HttpMethod httpMethod, String pathId) {
        Operation operation = getResourceByPathAndHttpMethod(httpMethod, pathId);
        return (operation != null) ? operation.getConsumes() : null;
    }


    private void printOperationDetails(Operation operation) {
        List<Parameter> params = operation.getParameters();
        System.out.println("\t \t \t" + operation.getOperationId());

    }

    public String generateDummyData(HttpMethod httpMethod, String pathId) {
        List<Parameter> params = getResourceParameters(httpMethod, pathId);

        String url = pathId;

        System.out.println(params);

        for (Parameter param : params) {
            System.out.println("Param: " + param.getIn() + " " + BODY);
            /**
             * Skipp optional field while generating dummy data
             */
            if (!param.getRequired()) {
                continue;
            }

            // TODO: check on null param.getIn()
            String inPlace = param.getIn();
            switch (ParameterPlace.valueOf(inPlace.toUpperCase())) {
                case PATH:
                    url = url.replaceAll("\\{" + param.getName() + "\\}", "hhh");
                    break;
                case BODY:

                    break;
                case COOKIE:
                    break;
                case FORMDATA:
                    break;
                case HEADER:
                    break;
                case QUERY:
                    break;
            }
        }
        return null;
    }
}
