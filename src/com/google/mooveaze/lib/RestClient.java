package com.google.mooveaze.lib;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class RestClient {
    public final static class Header {
        private String key;
        private String value;

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public final static class Response {
        public int statusCode;
        public String entity;

        public Response(int statusCode, String entity) {
            this.statusCode = statusCode;
            this.entity = entity;
        }
    }

    public Response get(String url) {
        return get(url, null);
    }

    public Response get(String url, Header[] headers) {
        HttpGet get = new HttpGet(url);

        if(headers != null) {
            for(Header h : headers) {
                get.setHeader(h.key, h.value);
            }
        }

        try {
            HttpResponse response = new DefaultHttpClient().execute(get);
            InputStream inputStream = response.getEntity().getContent();
            return new Response(response.getStatusLine().getStatusCode(), toString(inputStream));
        }
        catch(Exception e) {
            Log.error(e);
            return new Response(500, "");
        }
    }

    public Response post(String url, String entity, Header[] headers) {
        HttpPost post = new HttpPost(url);

        if(headers != null) {
            for(Header h : headers) {
                post.setHeader(h.key, h.value);
            }
        }

        try {
            post.setEntity(new StringEntity(entity));
            HttpResponse response = new DefaultHttpClient().execute(post);
            InputStream inputStream = response.getEntity().getContent();
            return new Response(response.getStatusLine().getStatusCode(), toString(inputStream));
        }
        catch(Exception e) {
            Log.error(e);
            return new Response(500, "");
        }
    }

    private String toString(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        StringWriter sw = new StringWriter();

        char[] buffer = new char[4096];
        int read;

        try {
            while((read = reader.read(buffer)) > 0) {
                sw.write(buffer, 0, read);
            }
        }
        finally {
            is.close();
        }

        return sw.toString();
    }
}
