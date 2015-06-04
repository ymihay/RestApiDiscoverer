package com.smartbear.alertsite.web.rest;

import com.smartbear.alertsite.service.DiscoverResourcesService;
import com.smartbear.alertsite.web.rest.exceptions.ResourceBadParamsExceptions;
import com.smartbear.alertsite.web.rest.exceptions.ResourceNotFoundException;
import com.wordnik.swagger.models.Operation;

import com.wordnik.swagger.models.Response;
import com.wordnik.swagger.models.parameters.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by yanamikhaylenko on 5/14/15.
 */

@RestController
@RequestMapping(value = "/resources", method = RequestMethod.GET)
public class DiscoveredResourcesController extends SwaggerResourcesController {

    @RequestMapping
    public Map<String, List<String>> getDiscoveredResources(@RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }
        return parserService.getAllResources();
    }

    @RequestMapping(value = "/{httpMethod}")
    public Operation getDiscoveredResource(@PathVariable("httpMethod") HttpMethod httpMethod,
                                           @RequestParam("pathId") String pathId,
                                           @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }
        Operation discoveredResource = parserService.getResourceByPathAndHttpMethod(httpMethod, pathId);
        if (discoveredResource != null) {
            return discoveredResource;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{httpMethod}/id")
    public String getDiscoveredResourceOperationId(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                   @RequestParam("pathId") String pathId,
                                                   @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        String discoveredResourceOpId = parserService.getResourceOperationId(httpMethod, pathId);
        if (discoveredResourceOpId != null) {
            return discoveredResourceOpId;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{httpMethod}/parameters")
    public List<Parameter> getDiscoveredResourceParameters(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                           @RequestParam("pathId") String pathId,
                                                           @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        List<Parameter> resourceParameters = parserService.getResourceParameters(httpMethod, pathId);
        if (resourceParameters != null) {
            return resourceParameters;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{httpMethod}/responses")
    public Map<String, Response> getDiscoveredResourceResponses(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                                @RequestParam("pathId") String pathId,
                                                                @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        Map<String, Response> resourceResponses = parserService.getResourceResponses(httpMethod, pathId);
        if (resourceResponses != null) {
            return resourceResponses;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{httpMethod}/produces")
    public List<String> getDiscoveredResourceProduces(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                      @RequestParam("pathId") String pathId,
                                                      @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        List<String> discoveredResourceProduces = parserService.getResourceProduces(httpMethod, pathId);
        if (discoveredResourceProduces != null) {
            return discoveredResourceProduces;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{httpMethod}/consumes")
    public List<String> getDiscoveredResourceConsumes(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                      @RequestParam("pathId") String pathId,
                                                      @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        List<String> discoveredResourceConsumes = parserService.getResourceConsumes(httpMethod, pathId);
        if (discoveredResourceConsumes != null) {
            return discoveredResourceConsumes;
        } else {
            throw new ResourceNotFoundException();
        }
    }


    @RequestMapping(value = "/{httpMethod}/dummyData")
    public String getGeneratedDummyData(@PathVariable("httpMethod") HttpMethod httpMethod,
                                                      @RequestParam("pathId") String pathId,
                                                      @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        String dummyData = parserService.generateDummyData(httpMethod, pathId);
        if (dummyData != null) {
            return dummyData;
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
