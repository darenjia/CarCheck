package com.bokun.bkjcb.carcheck.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengShuai on 2018/11/2.
 * Description :
 */
public class PlanContent {
    public static final List<Plan> ITEMS = new ArrayList<>();

    public static void addPlan(Plan plan) {
        ITEMS.add(plan);
    }

    public static void addPlan(List<Plan> plans) {
        ITEMS.addAll(plans);
    }

    public static void remove(Plan plan) {
        ITEMS.remove(plan);
    }

    public static void remove(int count) {
        ITEMS.remove(count);
    }

    public static List<Plan> getItemsFromJson(String json) {
        List<Plan> list = new ArrayList<>();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement element : array) {
            Plan plan = gson.fromJson(element, Plan.class);
            list.add(plan);
        }
        return list;
    }

    public static class Plan {
        private long id;
        private String name;
        private String describe;
        private String Guid;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getGuid() {
            return Guid;
        }

        public void setGuid(String guid) {
            Guid = guid;
        }
    }
}
