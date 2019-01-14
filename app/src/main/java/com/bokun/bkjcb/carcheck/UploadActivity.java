package com.bokun.bkjcb.carcheck;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.Model.CheckPlan_;

import java.util.List;

import io.objectbox.Box;

public class UploadActivity extends BaseActivity {

    private List<CheckPlan> plans;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_upload);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("上传管理");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListView upload_view = findViewById(R.id.upload_listview);
        Box<CheckPlan> checkResultBox = MyApplication.getBoxStore().boxFor(CheckPlan.class);
        plans = checkResultBox.query()
                .equal(CheckPlan_.type, 2).build().find();
        upload_view.setAdapter(new UploadAdapter());
    }

    class UploadAdapter extends BaseAdapter {
        ViewHolder viewHolder;
        @Override
        public int getCount() {
            return plans.size();
        }

        @Override
        public Object getItem(int i) {
            return plans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final int flg = i;
            final CheckPlan plan = plans.get(flg);
            if (convertView == null) {
                convertView = View.inflate(UploadActivity.this, R.layout.upload_item_view, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.task_title);
                viewHolder.state = (TextView) convertView.findViewById(R.id.task_state);
                viewHolder.button = (Button) convertView.findViewById(R.id.task_btn);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(plan.getProjectName());
            viewHolder.state.setText("等待上传");
            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView state;
            Button button;
        }
    }
}
