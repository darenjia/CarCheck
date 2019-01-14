package com.bokun.bkjcb.carcheck;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.Adapter.CheckDetailViewPagerAdapter;
import com.bokun.bkjcb.carcheck.Fragment.CheckItemFragment;
import com.bokun.bkjcb.carcheck.Fragment.ItemDetailFragment;
import com.bokun.bkjcb.carcheck.Model.CheckItem;
import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.Model.CheckResult;
import com.bokun.bkjcb.carcheck.View.AlertGuidBuilder;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class CheckActivity extends BaseActivity {


    /**
     * 1
     */
    private TextView mCurrentPage;
    /**
     * 22
     */
    private TextView mAllPage;
    private TextView mCheckFinish;
    private FrameLayout mFrameLayout;
    private ViewPager viewPager;
    private ImageView back;
    private LinearLayout page_layout;
    private CheckItem checkItem;
    private CheckPlan checkPlan;
    private Box<CheckPlan> checkPlanBox;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check);

        mCurrentPage = (TextView) findViewById(R.id.current_page);
        mAllPage = (TextView) findViewById(R.id.all_page);
        mCheckFinish = (TextView) findViewById(R.id.check_finish);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        viewPager = findViewById(R.id.viewpager);
        back = findViewById(R.id.check_back);
        page_layout = findViewById(R.id.page_layout);
    }

    @Override
    protected void initData() {
        long projectID = getIntent().getLongExtra(ItemDetailFragment.ARG_ITEM_ID, 0);
        checkItem = CheckItem.getCheckItem(this);
        List<CheckItemFragment> fragments = new ArrayList<>();
        List<CheckResult> results = new ArrayList<>();
        checkPlanBox = MyApplication.getBoxStore().boxFor(CheckPlan.class);
        checkPlan = checkPlanBox.get(projectID);
        CheckResult result;
        for (int i = 0; i < checkItem.getItems().size(); i++) {
            result = new CheckResult();
            result.setResultID(Integer.parseInt(checkItem.getItems().get(i).getId()));
            results.add(result);
        }
        if (checkPlan.getResults() == null||checkPlan.getResults().size()==0) {
            checkPlan.getResults().addAll(results);
            checkPlan.setType(1);
            checkPlanBox.put(checkPlan);
            checkPlan = checkPlanBox.get(projectID);
        }
        for (int i = 0; i < checkItem.getItems().size(); i++) {
            CheckItemFragment item = new CheckItemFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", checkItem.getItems().get(i));
            bundle.putLong("id", checkPlan.getResults().get(i).getId());
            item.setArguments(bundle);
            fragments.add(item);
        }
        CheckDetailViewPagerAdapter adapter = new CheckDetailViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        mAllPage.setText(String.valueOf(checkItem.getItems().size()));
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPage.setText(String.valueOf(position + 1));
            }
        });
        page_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertGuidBuilder(checkItem.getItems(), CheckActivity.this, new AlertGuidBuilder.OnClickListener() {
                    @Override
                    public void onClick(int position) {
                        viewPager.setCurrentItem(position, false);
                    }
                }, viewPager.getCurrentItem()).builder();
            }
        });
        mCheckFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPlan.setType(2);
                checkPlanBox.put(checkPlan);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
