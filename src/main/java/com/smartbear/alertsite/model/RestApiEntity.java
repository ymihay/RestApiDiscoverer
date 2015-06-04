package com.smartbear.alertsite.model;

import org.springframework.http.HttpMethod;

/**
 * Created by yanamikhaylenko on 5/14/15.
 */
public class RestApiEntity {
    private String id;
    private HttpMethod methodType;
    private String resourcePath;

}
