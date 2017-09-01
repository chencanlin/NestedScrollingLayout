package com.ccl.perfectisshit.nestedscrolling.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccl.perfectisshit.nestedscrolling.R;
import com.ccl.perfectisshit.nestedscrolling.interf.TabFragmentInterface;

/**
 * Created by ccl on 2017/8/30.
 */

public class TabFragment extends Fragment implements TabFragmentInterface {
    public static final String COMMON_KEY_TITLE = "common_title";
    private SparseArrayCompat<String> data = new SparseArrayCompat<>();
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    private String title = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tab, container, false);
        init(inflate);
        return inflate;
    }

    private void init(View inflate) {
        initView(inflate);
        initData();
        refreshView();
    }

    private void refreshView() {
        mMyRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void initData() {
        Bundle arguments = getArguments();
        if(arguments != null ){
            title = arguments.getString(COMMON_KEY_TITLE);
        }
        for (int i = 0; i < 50; i++) {
            data.put(i, title+" " + i);
        }
    }

    private void initView(View inflate) {
        RecyclerView rv = inflate.findViewById(R.id.rv);
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(mMyRecyclerViewAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public String getPagerTitle() {
        return "";
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
        @Override
        public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder holder, int position) {
            holder.mTvInfo.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private final TextView mTvInfo;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTvInfo = itemView.findViewById(R.id.id_info);
            }
        }
    }

    public static TabFragment newInstance(String title){
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COMMON_KEY_TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
