package com.example.githubbrowser.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.githubbrowser.R;
import com.example.githubbrowser.model.local.GitHubRepoDisplayItem;
import com.example.githubbrowser.viewmodel.GitHubListViewModel;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mTvLoading;

    private GitHubListViewModel mModel;
    private LinearLayoutManager mLayoutManager;
    private GitHubRepoItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewModel.
        mModel = ViewModelProviders.of(this).get(GitHubListViewModel.class);

        // Create the observer which updates the UI.
        final Observer repoObserver = new Observer<List<GitHubRepoDisplayItem>>() {

            @Override
            public void onChanged(@Nullable List<GitHubRepoDisplayItem> gitHubRepos) {
                Timber.d("onChanged() -> %s", gitHubRepos.size());
                onItemsReceived(gitHubRepos);
            }
        };

        mModel.getGitHubRepos().observe(this, repoObserver);

        mTvLoading = findViewById(R.id.tv_loading);

        findViewById(R.id.btn_do_the_thing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mTvLoading.setVisibility(View.VISIBLE);
                }
                mModel.searchRepos("Foobar");
            }
        });

        mRecyclerView = findViewById(R.id.container_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GitHubRepoItemAdapter(Collections.<GitHubRepoDisplayItem>emptyList());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void onItemsReceived(List<GitHubRepoDisplayItem> gitHubRepos) {
        // TODO null check
        // TODO empty list check
        mAdapter.setItems(gitHubRepos);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);
    }
}
