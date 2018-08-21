package com.felipelucas.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueDTO<T> {

    public T value;
}
