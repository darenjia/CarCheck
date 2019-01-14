package com.bokun.bkjcb.carcheck;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("检查进度");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
