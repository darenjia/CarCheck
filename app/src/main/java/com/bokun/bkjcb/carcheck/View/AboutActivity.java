package com.bokun.bkjcb.carcheck.View;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bokun.bkjcb.carcheck.BaseActivity;
import com.bokun.bkjcb.carcheck.R;

public class AboutActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
