package com.bokun.bkjcb.carcheck.Database;

import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.MyApplication;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengShuai on 2018/11/12.
 * Description :
 */
public class DataUtils {
    public static List<CheckPlan> getItemsFromJson() {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = MyApplication.getContext().getAssets().open("CheckPlan");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String buffer;
            while ((buffer = reader.readLine()) != null) {
                builder.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CheckPlan> list = new ArrayList<>();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonArray array = parser.parse(builder.toString()).getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement element : array) {
            CheckPlan plan = gson.fromJson(element, CheckPlan.class);
            list.add(plan);
        }
        return list;
    }
}
