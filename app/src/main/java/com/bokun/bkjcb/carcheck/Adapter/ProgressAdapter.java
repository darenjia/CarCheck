package com.bokun.bkjcb.carcheck.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.Model.PlanContent;
import com.bokun.bkjcb.carcheck.R;
import com.haozhang.lib.SlantedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengShuai on 2018/4/4.
 * Description :
 */

public class ProgressAdapter extends BaseAdapter {
    private Context context;
    private List<PlanContent.Plan> list = new ArrayList<>();
    public ProgressAdapter(Context context) {
        this.context = context;
    }

    public ProgressAdapter(Context context, List<PlanContent.Plan> list) {
        this.context = context;
        this.list.addAll(list);
    }

    public void refreshData(List<PlanContent.Plan> data){
        list.clear();
        list.addAll(data);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        PlanContent.Plan progress = list.get(position);
        if (convertView==null){
            holder = new ViewHolder();
            convertView =  View.inflate(context, R.layout.progress_item,null);
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
        holder.title.setText(progress.getName());
        holder.endtime.setText("2018-10-05");
        holder.time.setText("2018-9-12");
        holder.qu.setText("黄浦区");
        holder.type.setText("日常检查");
        if (progress.getId()/2==0){
            holder.slantedview.setSlantedBackgroundColor(context.getResources().getColor(R.color.green));
        }else {
            holder.slantedview.setSlantedBackgroundColor(context.getResources().getColor(R.color.bittersweet));
        }
        holder.slantedview.setText("已办结");
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
