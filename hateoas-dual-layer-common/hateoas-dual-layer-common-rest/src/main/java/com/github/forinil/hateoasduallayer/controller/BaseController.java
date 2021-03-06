package com.github.forinil.hateoasduallayer.controller;

import com.github.forinil.hateoasduallayer.describer.ControllerDescriber;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
public abstract class BaseController {

    private ControllerDescriber controllerDescriber;

    private Class controllerClass;

    BaseController(ControllerDescriber controllerDescriber) {
        this.controllerDescriber = controllerDescriber;
        this.controllerClass = this.getClass();
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Controller index")
    @ApiResponses({@ApiResponse(code = 200, message = "Response sent")})
    public HttpEntity<Resource<String>> index(){
        logger.debug("Requesting service index");

        String controllerName = controllerClass.getCanonicalName()
                .substring(controllerClass.getCanonicalName()
                        .lastIndexOf(".") + 1);
        String message = String.format("Index of controller %s", controllerName);
        Resource<String> response = new Resource<>(message, controllerDescriber.getHATEOASLinks());

        logger.debug("Returning: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
