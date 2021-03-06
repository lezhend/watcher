package com.fortinet.fcasb.watcher.alert.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zliu on 17/6/13.
 */
@Component
public class RestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestWrapper.class);

    private @Autowired
    RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<T> execute(String url, HttpMethod method, HttpEntity request,
                                         TypeReference responseType) {

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        HeaderRequestCallback requestCallback = new HeaderRequestCallback(request, messageConverters);
        FasterJsonResponseExtractor<T> responseExtractor = new FasterJsonResponseExtractor<>(responseType);
        try {
            return restTemplate.execute(url, method, requestCallback, responseExtractor);
        } catch (HttpClientErrorException error) {
            String errorContent = byteArrayToString(error.getResponseBodyAsByteArray());
            if (StringUtils.isNotBlank(errorContent)) {
                errorContent = errorContent.trim();
            }
            LOGGER.debug("error content : {}", errorContent);
            HttpHeaders httpHeaders = error.getResponseHeaders();
            setHttpHeadersErrorMessage(httpHeaders, errorContent);
            return new ResponseEntity<>(httpHeaders, error.getStatusCode());
        }
    }

    private void setHttpHeadersErrorMessage(HttpHeaders httpHeaders, String error){
        List errorList = new ArrayList<>();
        if (!StringUtils.isEmpty(error) && error.indexOf("<html>") > -1){
            errorList.add(error);
        }else {
            JSONObject errorJson = JSONObject.parseObject(error);
            errorList.add(errorJson.get("message"));
        }
        httpHeaders.put("errorMessage", errorList);
    }

    private String byteArrayToString(byte[] bytes) {
        try {
            return new String(bytes, Constants.DEFAULT.COMMON_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> ResponseEntity<T> put(String url, Object post, TypeReference<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(post);
        return execute(url, HttpMethod.PUT, request, responseType);
    }

    public <T> ResponseEntity<T> post(String url, Object post, TypeReference<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(post);
        return execute(url, HttpMethod.POST, request, responseType);
    }

    public <T> ResponseEntity<T> post(String url, Object post, HttpHeaders headers, TypeReference<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(post, headers);
        return execute(url, HttpMethod.POST, request, responseType);
    }

    public <T> ResponseEntity<T> get(String url, Map<String,String> params, TypeReference<T> responseType) {
        if(params.isEmpty()){
            HttpEntity request = HttpEntity.EMPTY;
            return execute(url, HttpMethod.GET, request, responseType);
        } else{
            HttpEntity request = HttpEntity.EMPTY;
            url=url+"?t=1";
            for(String key:params.keySet()){
                url = url+"&"+key+"="+params.get(key);
            }
            return execute(url, HttpMethod.GET, request, responseType);
        }

    }
    public <T> ResponseEntity<T> getBody(String url, Map<String,Object> body, TypeReference<T> responseType) {
        if(body.isEmpty()){
            HttpEntity request = HttpEntity.EMPTY;
            return execute(url, HttpMethod.GET, request, responseType);
        } else{
            HttpEntity request = new HttpEntity(body);
            return execute(url, HttpMethod.GET, request, responseType);
        }

    }

    public <T> ResponseEntity<T> get(String url, TypeReference<T> responseType) {
        HttpEntity request = HttpEntity.EMPTY;
        return execute(url, HttpMethod.GET, request, responseType);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, TypeReference<T> responseType) {
        HttpEntity request = new HttpEntity(headers);
        return execute(url, HttpMethod.GET, request, responseType);
    }

    public <T> ResponseEntity<T> formPost(String url, List<NameValuePair> formDatas, TypeReference<T> typeReference) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            if(null != formDatas) {
                post.setEntity(new UrlEncodedFormEntity(formDatas, Constants.DEFAULT.COMMON_CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(post);
            String content = IOUtils.toString(response.getEntity().getContent(), Constants.DEFAULT.COMMON_CHARSET);
            if (null != content && !content.isEmpty() && isJSON(content)) {
                T entity = JSON.parseObject(content, typeReference);
                return new ResponseEntity<>(entity, HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
            } else {
                throw new RuntimeException("need JSON format to convert to Object [" + content + "]");
            }
        } catch (Exception e) {
            LOGGER.error("http post error", e);
        } finally {
            close(httpClient);
        }
        return null;
    }

    public String formPost(String url, List<NameValuePair> formDatas) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(formDatas, Constants.DEFAULT.COMMON_CHARSET));
            CloseableHttpResponse response = client.execute(post);
            String content = IOUtils.toString(response.getEntity().getContent(), Constants.DEFAULT.COMMON_CHARSET);
            if (null != content && !content.isEmpty()) {
                return content;
            } else {
                throw new RuntimeException("need JSON format to convert to Object [" + content + "]");
            }
        } catch (Exception e) {
            LOGGER.error("http post error", e);
        } finally {
            close(client);
        }
        return null;
    }


    private static class HeaderRequestCallback implements RequestCallback {

        private final HttpEntity<?> requestEntity;
        private final List<HttpMessageConverter<?>> messageConverters;

        HeaderRequestCallback(HttpEntity<?> requestEntity, List<HttpMessageConverter<?>> messageConverters) {
            this.requestEntity = null == requestEntity ? HttpEntity.EMPTY : requestEntity;
            this.messageConverters = messageConverters;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void doWithRequest(ClientHttpRequest httpRequest) throws IOException {
            //Accepts only accept application/json;charset=utf-8, in converters array index 0
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(0);
            List<MediaType> supportedMediaTypes = getSupportedMediaTypes(httpMessageConverter);
            httpRequest.getHeaders().setAccept(supportedMediaTypes);
            //Set Headers
            if (!this.requestEntity.hasBody()) {
                HttpHeaders httpHeaders = httpRequest.getHeaders();
                HttpHeaders requestHeaders = this.requestEntity.getHeaders();
                if (!requestHeaders.isEmpty()) {
                    httpHeaders.putAll(requestHeaders);
                }
                if (httpHeaders.getContentLength() == -1) {
                    httpHeaders.setContentLength(0L);
                }
            } else {
                Object requestBody = this.requestEntity.getBody();
                Class<?> requestType = requestBody.getClass();
                HttpHeaders requestHeaders = this.requestEntity.getHeaders();
                MediaType requestContentType = requestHeaders.getContentType();
                for (HttpMessageConverter<?> messageConverter : messageConverters) {
                    if (messageConverter.canWrite(requestType, requestContentType)) {
                        if (!requestHeaders.isEmpty()) {
                            httpRequest.getHeaders().putAll(requestHeaders);
                        }
                        ((HttpMessageConverter<Object>) messageConverter).write(
                                requestBody, requestContentType, httpRequest);
                        return;
                    }
                }
                String message = "Could not write request: no suitable HttpMessageConverter found for request type [" +
                        requestType.getName() + "]";
                if (requestContentType != null) {
                    message += " and content type [" + requestContentType + "]";
                }
                throw new RestClientException(message);
            }
        }

        private List<MediaType> getSupportedMediaTypes(HttpMessageConverter<?> messageConverter) {
            List<MediaType> supportedMediaTypes = messageConverter.getSupportedMediaTypes();
            List<MediaType> result = new ArrayList<>(supportedMediaTypes.size());
            for (MediaType supportedMediaType : supportedMediaTypes) {
                if (supportedMediaType.getCharSet() != null) {
                    supportedMediaType =
                            new MediaType(supportedMediaType.getType(), supportedMediaType.getSubtype());
                }
                result.add(supportedMediaType);
            }
            return result;
        }
    }

    private static class FasterJsonResponseExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

        private TypeReference responseType;

        FasterJsonResponseExtractor(TypeReference responseType) {
            this.responseType = responseType;
        }

        @SuppressWarnings("unchecked")
        @Override
        public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
            checkContentType(response);
            String jsonContent = IOUtils.toString(response.getBody(), Constants.DEFAULT.COMMON_CHARSET);
            if (null == jsonContent || jsonContent.isEmpty() || !isJSON(jsonContent)) {
                LOGGER.info("content null or not a JSON : [{}]", jsonContent);
                return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
            } else {
                LOGGER.debug("ready to covert by FasterJson content : {}", jsonContent);
                T body = (T) JSON.parseObject(jsonContent, responseType);
                return new ResponseEntity<>(body, response.getHeaders(), response.getStatusCode());
            }

        }

        private void checkContentType(ClientHttpResponse response) {
            MediaType contentType = response.getHeaders().getContentType();
            if (contentType == null) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("No Content-Type header found, best choice is application/json;charset=UTF8");
                }
            } else {
                if (contentType.toString().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8.toString()) &&
                        LOGGER.isTraceEnabled()) {
                    LOGGER.trace("{} media type may parse error. try to do it...", contentType);
                }
            }
        }
    }

    private static boolean isJSON(String text) {
        return (text.startsWith("{") && text.endsWith("}")) ||
                (text.startsWith("[") && text.endsWith("]"));
    }

    private void close(Closeable... closeables){
        for(Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.error("close io error", e);
            }
        }
    }
}
