package com.zhou.basespringboot.dto.yapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @date 2019/11/27 14:47
 */
@Data
public class QueryDTO {
    private int index;
    private String name;
    private String desc = "";
    private int add_time;
    private int up_time;

    private List<Query> list = new ArrayList<>();

    public QueryDTO(String controllerName) {
        int now = (int) (System.currentTimeMillis() / 1000);
        this.name = controllerName;
        this.add_time = now;
        this.up_time = now;
    }


    @Data
    static class Query {
        private Path query_path;
        //        private int edit_uid;
        private String status = "done";
        private String type = "static";
        private boolean req_body_is_json_schema = false;
        private boolean res_body_is_json_schema = false;
        private boolean api_opened = false;
        private int index;
        private List<String> tag = new ArrayList<>();
        //        private int _id;
        private String method = "post";
        //        private int catId;
        private String title;
        private String path;
        //        private int project_id;
        private List<Map<String, Object>> req_params;
        private String res_body_type;
        //        private int uid;
        private int add_time;
        private int up_time;

        private List<Map<String, Object>> req_query;
        private List<Header> req_headers = new ArrayList<>();
        private List<Map<String, Object>> req_body_form;

        private int __v = 0;
        private String desc = "";
        private String markdown = "";

        private String req_body_other = "{}";
        private String req_body_type = "json";
        private String res_body = "{}";

        public Query(String queryName, String queryUrl, String queryBody) {
            int now = (int) (System.currentTimeMillis() / 1000);
            this.title = queryName;
            this.path = queryUrl;
            this.req_body_other = queryBody;
            this.req_headers.add(new Header());
            this.add_time = now;
            this.up_time = now;
        }
    }

    @Data
    static class Path {
        private String path;
        private List<Map<String, Object>> params;
    }

    @Data
    static class Header {
        private String required = "1";
        //        private String _id;
        private String name = "Content-Type";
        private String value = "application/json";
    }
}
