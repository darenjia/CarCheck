package com.bokun.bkjcb.carcheck;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.Database.DataUtils;
import com.bokun.bkjcb.carcheck.Fragment.ItemDetailFragment;
import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.Utils.AppManager;
import com.bokun.bkjcb.carcheck.View.AboutActivity;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ItemListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private DrawerLayout drawerLayout;
    private Box<CheckPlan> box;
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter recyclerViewAdapter;
    private Box<CheckPlan> checkPlanBox;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.action_sign_in, R.string.action_sign_in_short);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        refreshLayout = findViewById(R.id.refresh_layout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        checkPlanBox = MyApplication.getBoxStore().boxFor(CheckPlan.class);
        if (checkPlanBox.query().build().count() == 0) {
            List<CheckPlan> list = DataUtils.getItemsFromJson();
            checkPlanBox.put(list);
        }
        setupRecyclerView();
        getCheckPlan(3);
    }

    private void getCheckPlan(int length) {
        recyclerViewAdapter.replaceData(checkPlanBox.query().build().find(0, length));
    }

    @Override
    protected void initListener() {
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_check_plan:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_info_progress:
                startActivity(new Intent(ItemListActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_info_check:
                startActivity(new Intent(ItemListActivity.this, SearchActivity.class));
                break;
            case R.id.nav_update_result:
                startActivity(new Intent(ItemListActivity.this, UploadActivity.class));
                break;
            case R.id.nav_app_introduce:
                startActivity(new Intent(ItemListActivity.this, AboutActivity.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(ItemListActivity.this, LoginActivity.class));
                break;
            case R.id.nav_exit:
                AppManager.getAppManager().AppExit(this);
                break;
        }

        return true;
    }

    @Override
    public void onRefresh() {
        getCheckPlan(6);
        refreshLayout.setRefreshing(false);

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<CheckPlan> mValues = new ArrayList<>();
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPlan item = (CheckPlan) view.getTag();

                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getId());

                context.startActivity(intent);
            }

        };

        SimpleItemRecyclerViewAdapter() {
        }

        void replaceData(List<CheckPlan> items) {
            mValues.clear();
            mValues.addAll(items);
            notifyDataSetChanged();
        }

        void addData(List<CheckPlan> items) {
            mValues.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getProjectName());
            holder.mQuView.setText(mValues.get(position).getQu());
            holder.mYearView.setText("2018-08-15");
            holder.mAreaView.setText(mValues.get(position).getArea());
            holder.mTypeView.setText("待检查");
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mQuView;
            final TextView mYearView;
            final TextView mTypeView;
            final TextView mAreaView;
            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mQuView = (TextView) view.findViewById(R.id.qu_text);
                mYearView = (TextView) view.findViewById(R.id.year_text);
                mTypeView = (TextView) view.findViewById(R.id.type_text);
                mAreaView = (TextView) view.findViewById(R.id.area_text);
            }
        }
    }
}
