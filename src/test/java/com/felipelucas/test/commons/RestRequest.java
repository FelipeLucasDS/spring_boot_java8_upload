package com.felipelucas.test.commons;

import java.io.File;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

public class RestRequest<E> {

    private String baseUrl = "";
    private String endpoint = "";
    private HttpMethod method;
    private HttpHeaders headers = new HttpHeaders();
    private E payload;

    private RestRequest() {
        this.headers = new HttpHeaders();
    }

    public static RestRequest build() {
        return new RestRequest();
    }

    public RestRequest baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RestRequest method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RestRequest endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public RestRequest payload(E payload) {
        this.payload = payload;
        return this;
    }


    public ResponseEntity execute() {
        HttpEntity entity = this.payload == null ? new HttpEntity(this.headers) : new HttpEntity(this.payload, this.headers);
        String requestUrl = this.baseUrl + this.endpoint;

        return RestTemplateBuilder.build().exchange(requestUrl, this.method, entity, new ParameterizedTypeReference<E>() {}, new Object[0]);
    }

    public <E> ResponseEntity<E> execute(Class<E> responseType) {
        HttpEntity entity = this.payload == null ? new HttpEntity(this.headers) : new HttpEntity(this.payload, this.headers);
        String requestUrl = this.baseUrl + this.endpoint;
        try {
            return RestTemplateBuilder.build()
                    .exchange(
                            requestUrl,
                            this.method, entity, responseType, new Object[0]);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public <C> ResponseEntity<C> execute(ParameterizedTypeReference<C> responseType) {
        HttpEntity entity = this.payload == null ? new HttpEntity(this.headers) : new HttpEntity(this.payload, this.headers);
        String requestUrl = this.baseUrl + this.endpoint;

        return RestTemplateBuilder.build().exchange(requestUrl, this.method, entity, responseType, new Object[0]);
    }

    public <C> ResponseEntity<C> execute(ParameterizedTypeReference<C> responseType, File file) {
        LinkedMultiValueMap<String, Object> multipart = new LinkedMultiValueMap<>();
        multipart.add("file", new FileSystemResource(file));

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(multipart, headers);
        try {
            return RestTemplateBuilder.build().exchange(
                    baseUrl + endpoint,
                    HttpMethod.POST,
                    requestEntity,
                    responseType);
        }catch(HttpClientErrorException ex){
            return new ResponseEntity<C>(HttpStatus.valueOf(Integer.parseInt(ex.getMessage().trim())));
        }
    }

}
