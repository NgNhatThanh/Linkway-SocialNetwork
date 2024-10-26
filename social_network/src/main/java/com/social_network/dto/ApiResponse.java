package com.social_network.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private int statusCode;

    private String error;

    private Object message;

    private T data;

}
