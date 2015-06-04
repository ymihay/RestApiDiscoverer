package com.smartbear.alertsite.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.ExecutionException;

/**
 * Created by yanamikhaylenko on 5/19/15.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadParamsExceptions extends RuntimeException {
}