package com.jarvis.framework.openfeign.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.HttpMessageConverter;

public class FeignHttpMessageConverters extends HttpMessageConverters {
    private final HttpMessageConverters httpMessageConverters;
    private List<HttpMessageConverter<?>> converters;
    private final FeignMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public FeignHttpMessageConverters(HttpMessageConverters httpMessageConverters) {
        super(new HttpMessageConverter[0]);
        this.httpMessageConverters = httpMessageConverters;
        this.mappingJackson2HttpMessageConverter = new FeignMappingJackson2HttpMessageConverter(true);
    }

    public List<HttpMessageConverter<?>> getConverters() {
        if (null == this.converters) {
            this.initConverters();
        }

        return this.converters;
    }

    private synchronized void initConverters() {
        if (null == this.converters) {
            this.converters = new ArrayList();
            this.converters.add(this.mappingJackson2HttpMessageConverter);
            Iterator var1 = this.httpMessageConverters.getConverters().iterator();

            while(var1.hasNext()) {
                HttpMessageConverter<?> converter = (HttpMessageConverter)var1.next();
                if (!(converter instanceof FeignMappingJackson2HttpMessageConverter)) {
                    this.converters.add(converter);
                }
            }

        }
    }
}
