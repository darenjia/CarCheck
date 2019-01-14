package com.bokun.bkjcb.carcheck.Model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DengShuai on 2018/11/2.
 * Description :
 */
public class CheckItem {
    /**
     * version : 1
     * name : 安全项目检察
     * items : [{"id":"1","description":"建立日常检查制度"},{"id":"2","description":"已办理地下空间安全使用备案"},{"id":"3","description":"制定地下空间突发公共事件应急处置操作规程并定期演练"},{"id":"4","description":"消防控制室、特种设施设备使用等岗位工作人员持证上岗"},{"id":"5","description":"建筑工程或场所依法通过消防设计审核、消防验收、备案和备案抽查"},{"id":"6","description":"消防给水和灭火设施、防烟和排烟设施、火灾自动报警系统等消防设施处于自动状态"},{"id":"7","description":"消防设施、器材无损坏、挪用或者擅自拆除、停用等现象，消火栓无埋压、圈占、遮挡等现象"},{"id":"8","description":"疏散通道、安全出口无占用、堵塞、封闭或者其他妨碍安全疏散行为的现象"},{"id":"9","description":"按照标准要求设置消防应急照明和疏散指示标志，并确保完好有效"},{"id":"10","description":"民防设施设备完好、使用运行正常，无缺损或破坏现象"},{"id":"11","description":"无住宿、生产、经营、储存\u201c三合一\u201d或\u201c四合一\u201d现象"},{"id":"12","description":"出入口设置驼峰（台阶)或配置防汛挡板、沙袋等防汛物资"}]
     */
    public static CheckItem getCheckItem(Context context){
        StringBuilder builder = new StringBuilder();
        try {
           InputStream inputStream =  context.getAssets().open("CheckItemsDetail");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String buffer;
            while ((buffer = reader.readLine())!=null){
                builder.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(builder.toString()).getAsJsonObject();
        Gson gson = new Gson();
        return gson.fromJson(element,CheckItem.class);
    }

    private String version;
    private String name;
    private List<CheckItemDetail> items;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckItemDetail> getItems() {
        return items;
    }

    public void setItems(List<CheckItemDetail> items) {
        this.items = items;
    }

    public static class CheckItemDetail implements Serializable {
        /**
         * id : 1
         * description : 建立日常检查制度
         */

        private String id;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
