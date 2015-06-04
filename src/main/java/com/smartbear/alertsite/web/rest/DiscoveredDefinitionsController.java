package com.smartbear.alertsite.web.rest;

import com.smartbear.alertsite.service.DiscoverResourcesService;
import com.smartbear.alertsite.web.rest.exceptions.ResourceBadParamsExceptions;
import com.smartbear.alertsite.web.rest.exceptions.ResourceNotFoundException;
import com.wordnik.swagger.models.Model;
import com.wordnik.swagger.models.properties.Property;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by yanamikhaylenko on 5/15/15.
 */
@RestController
@RequestMapping(value = "/definitions", method = RequestMethod.GET)
public class DiscoveredDefinitionsController extends SwaggerResourcesController {

    @RequestMapping
    public Map<String, Model> getDefinitions(@RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }
        Map<String, Model> definitions = parserService.getDefinitions();
        if (definitions != null) {
            return definitions;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{name}")
    public Model getDefinition(@PathVariable String name,
                               @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        Model definition = parserService.getDefinitionByName(name);
        if (definition != null) {
            return definition;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{name}/properties")
    public Map<String, Property> getDefinitionProperties(@PathVariable String name,
                                                         @RequestParam("swagUrl") String swaggerUrl) throws ExecutionException {
        DiscoverResourcesService parserService = cache.get(swaggerUrl);
        if (parserService.getSwagger() == null) {
            throw new ResourceBadParamsExceptions();
        }

        Map<String, Property> definitionProperties = parserService.getDefinitionProperties(name);
        if (definitionProperties != null) {
            return definitionProperties;
        } else {
            throw new ResourceNotFoundException();
        }
    }
}

