package com.bokun.bkjcb.carcheck.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bokun.bkjcb.carcheck.ItemDetailActivity;
import com.bokun.bkjcb.carcheck.ItemListActivity;
import com.bokun.bkjcb.carcheck.Model.CheckPlan;
import com.bokun.bkjcb.carcheck.MyApplication;
import com.bokun.bkjcb.carcheck.R;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private CheckPlan mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = MyApplication.getBoxStore().boxFor(CheckPlan.class).get(getArguments().getLong(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getProjectName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        TextView name = rootView.findViewById(R.id.name);
        TextView address = rootView.findViewById(R.id.address);
        TextView type = rootView.findViewById(R.id.type);
        TextView date = rootView.findViewById(R.id.date);
        TextView area = rootView.findViewById(R.id.area);
        TextView berth = rootView.findViewById(R.id.berth);
        if (mItem != null) {
            name.setText(mItem.getProjectName());
            address.setText(mItem.getProjectAddress());
            type.setText(mItem.getArea());
            date.setText("2018-08-15");
            area.setText(mItem.getBuildingAllArea()+" ㎡");
            berth.setText(mItem.getBuildingAllBerth()+" 个");
        }

        return rootView;
    }
}
