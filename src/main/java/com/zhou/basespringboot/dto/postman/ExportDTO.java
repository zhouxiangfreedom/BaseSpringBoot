package com.zhou.basespringboot.dto.postman;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zx
 * @date 2019/11/27 15:13
 */
@Data
public class ExportDTO {
    private Info info;
    private List<Project> item = new ArrayList<>();

    @Data
    public static class Info {
        String _postman_id = "";
        String name = "";
        String schema = "";
    }

    @Data
    public static class Project {
        String name = "";
        List<Category> item = new ArrayList<>();
    }

    @Data
    public static class Category {
        String name = "";
        boolean _postman_isSubFolder;
        List<Controller> item = new ArrayList<>();
    }

    @Data
    public static class Controller {
        String name = "";
        boolean _postman_isSubFolder;
        List<Query> item = new ArrayList<>();
    }

    @Data
    public static class Query {
        String name = "";
        Request request;
        Response response;
    }

    @Data
    public static class Request {
        String method;
        List<Header> header;
        Body body;
        Url url;
    }

    @Data
    public static class Response {
        String name = "";
    }

    @Data
    public static class Header {
        String key;
        String name;
        String type;
        String value;
    }

    @Data
    public static class Body {
        String mode;
        String raw;
    }

    @Data
    public static class Url {
        String raw;
        List<String> host = new ArrayList<>();
        String port;
        List<String> path = new ArrayList<>();
    }
}
