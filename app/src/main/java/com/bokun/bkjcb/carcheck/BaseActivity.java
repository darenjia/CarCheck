package com.bokun.bkjcb.carcheck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bokun.bkjcb.carcheck.Utils.AppManager;

/**
 * Created by DengShuai on 2018/11/1.
 * Description :
 */
public class BaseActivity extends AppCompatActivity {

    private long time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
        initListener();
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 1000) {
            time = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }
}
