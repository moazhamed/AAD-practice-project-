package com.example.aadpractice.ui.home.fragments.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.aadpractice.R;
import com.example.aadpractice.model.Leader;
import com.example.aadpractice.ui.home.adapter.LeaderListAdapter;
import com.example.aadpractice.utils.ProjectUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LearningLeadersFragment extends Fragment implements LeaderListAdapter.OnItemClickedListener {
    private LeaderListAdapter mLeaderListAdapter;
    private List<Leader> mItemsList;
    private SwipeRefreshLayout mSwipe;
    private LearningLeadersViewModel mLearningLeadersViewModel;
    private RecyclerView mRecyclerView;

    public LearningLeadersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaders, container, false);
        mLearningLeadersViewModel = new ViewModelProvider(this).get(LearningLeadersViewModel.class);
        mLearningLeadersViewModel.mListResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });

        initSwipeRefresh(view);
        initRecyclerView(view);
        getList();
        return view;
    }

    private void initSwipeRefresh(View view) {
        mSwipe = view.findViewById(R.id.swipe_refresh);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
        mSwipe.setRefreshing(true);
    }

    private void initRecyclerView(View view) {
        mItemsList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.home_rv);
        mLeaderListAdapter = new LeaderListAdapter(this.getContext(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mLeaderListAdapter);
    }

    private void getList() {
        if (ProjectUtils.isNetworkConnected(this.getContext())) {// check for internet connection first
            mSwipe.setRefreshing(true);
            mLearningLeadersViewModel.getList();
        }
        else { // No Internet Connection
            mSwipe.setRefreshing(false);
            mRecyclerView.setVisibility(View.INVISIBLE);
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(R.id.view_pager), R.string.no_internet, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSwipe.setRefreshing(true);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
            snackbar.setBackgroundTint(getResources().getColor(R.color.red));
            snackbar.show();
        }
    }

    private void updateUi(List<Leader> apiResponse) {
        mSwipe.setRefreshing(false);
        if (apiResponse != null){
            mItemsList = apiResponse;
            mLeaderListAdapter.setItems(mItemsList);
            mLeaderListAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
        }else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            Toast.makeText(this.getContext(), R.string.network_request_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClicked(Leader leader) {
    }
}