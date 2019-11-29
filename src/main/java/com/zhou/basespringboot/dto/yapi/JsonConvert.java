package com.zhou.basespringboot.dto.yapi;

import com.alibaba.fastjson.JSON;
import com.zhou.basespringboot.dto.postman.ExportDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zx
 * @date 2019/11/27 15:24
 */
public class JsonConvert {
    /**
     * postman导出来的json转换为yapi的json格式
     *
     * @param fileName
     * @return
     */
    public static List<QueryDTO> convert(String fileName) {
        String json = readFile(fileName);
        ExportDTO exportDTO = JSON.parseObject(json, ExportDTO.class);
        List<ExportDTO.Controller> controllerList = exportDTO.getItem().get(0).getItem().get(0).getItem();

        List<QueryDTO> resultList = new ArrayList<>();
        controllerList.forEach(controller -> {
            QueryDTO queryDTO = new QueryDTO(controller.getName());
            resultList.add(queryDTO);
            List<ExportDTO.Query> queryList = controller.getItem();
            queryList.forEach(query -> {
                String url = query.getRequest().getUrl().getRaw();
                String body = query.getRequest().getBody().getRaw();
                queryDTO.getList().add(new QueryDTO.Query(query.getName(), url, body));
            });
        });
        return resultList;
    }

    /**
     * 从文件读取，默认为项目根目录
     *
     * @param fileName 文件名
     * @return
     */
    public static String readFile(String fileName) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            if (reader.ready()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void filePath() {
        //1. .../target/classes
        String path = JsonConvert.class.getResource("/").getPath();
        System.out.println(path);
        //2. .../target/classes/{packageName}
        String path2 = JsonConvert.class.getResource("").getPath();
        System.out.println(path2);
        //3.  .../{project root path}
        File file = new File("");
        System.out.println(file.getAbsolutePath());

        //4. 同3
        String path4 = System.getProperty("user.dir");
        System.out.println(path4);

        //5. 忽略....
        String path5 = System.getProperty("java.class.path");
        System.out.println(path5);
    }

}
