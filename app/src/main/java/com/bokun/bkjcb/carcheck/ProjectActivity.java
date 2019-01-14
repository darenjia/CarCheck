package com.bokun.bkjcb.carcheck;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.Adapter.ProgressAdapter;
import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.Model.CheckPlan_;
import com.haozhang.lib.SlantedTextView;

import java.util.List;

import io.objectbox.Box;

public class ProjectActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListView mList;
    private List<CheckPlan> plans;

    public void initView() {
        setContentView(R.layout.activity_project);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mList = (ListView) findViewById(R.id.list);
        mToolbar.setTitle("检查进度");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList.setAdapter(new ProgressAdapter(this));
        ListView listView = findViewById(R.id.list);
        Box<CheckPlan> checkResultBox = MyApplication.getBoxStore().boxFor(CheckPlan.class);
        plans = checkResultBox.query()
                .equal(CheckPlan_.type, 1).build().find();
        listView.setAdapter(new ProjectAdapter());
    }
    class ProjectAdapter extends BaseAdapter{
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
            ViewHolder holder;
            CheckPlan progress = plans.get(i);
            if (convertView==null){
                holder = new ViewHolder();
                convertView =  View.inflate(ProjectActivity.this, R.layout.progress_item,null);
                holder.title = (TextView) convertView.findViewById(R.id.project_name);
                holder.qu = (TextView) convertView.findViewById(R.id.qu);
                holder.time = (TextView) convertView.findViewById(R.id.project_time);
                holder.type = (TextView) convertView.findViewById(R.id.check_type);
                holder.endtime = (TextView) convertView.findViewById(R.id.end_time);
                holder.slantedview = (SlantedTextView) convertView.findViewById(R.id.slantedview);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.title.setText(progress.getProjectName());
            holder.endtime.setText(progress.getArea());
            holder.time.setText("2018-09-12");
            holder.qu.setText(progress.getQu());
            holder.type.setText(progress.getId()%2==0?"日常检查":"联合检查");
            if (progress.getId()%2==0){
                holder.slantedview.setSlantedBackgroundColor(getResources().getColor(R.color.green));
            }else {
                holder.slantedview.setSlantedBackgroundColor(getResources().getColor(R.color.bittersweet));
            }
            //holder.slantedview.setText(progress.getBjqk());
            return convertView;
        }
        class ViewHolder {
            TextView title;
            TextView qu;
            TextView time;
            TextView type;
            TextView endtime;
            SlantedTextView slantedview;
        }
    }
}
