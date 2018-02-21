package com.example.githubbrowser.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.githubbrowser.AppApp;
import com.example.githubbrowser.R;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;
import com.example.githubbrowser.viewmodel.GitHubListViewModel;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mTvStatus;
    private View mBtnSearch;
    private EditText mEditSearch;

    private LinearLayoutManager mLayoutManager;
    private GitHubRepoItemAdapter mAdapter;
    private GitHubListViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvStatus = findViewById(R.id.tv_status);
        mBtnSearch = findViewById(R.id.btn_search);
        mEditSearch = findViewById(R.id.edit_keywords);
        mRecyclerView = findViewById(R.id.container_recycler);

        // Get the ViewModel.
        mModel = ViewModelProviders.of(this, AppApp.getInjector().provideViewModelFactory()).get(GitHubListViewModel.class);

        mModel.getState().observe(this, new Observer<GitHubListViewModel.State>() {

            public void onChanged(@Nullable GitHubListViewModel.State state) {
                onStateChanged(state);
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModel.searchRepos(mEditSearch.getText().toString());
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new GitHubRepoItemAdapter(Collections.<GitHubRepoDisplayItem>emptyList());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onStateChanged(GitHubListViewModel.State state) {
        Timber.d("onChanged() %s", state.getStatus());

        switch (state.getStatus()) {
            case IDLE:
                mTvStatus.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mEditSearch.setText("");
                break;
            case SEARCHING:
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText("Hold on..");
                mRecyclerView.setVisibility(View.GONE);
                mEditSearch.setText(state.getKeywords());
                break;
            case DISPLAYING:
                List<GitHubRepoDisplayItem> items = state.getDisplayItems();
                if (items.isEmpty()) {
                    Timber.d("No items to display");
                    mTvStatus.setVisibility(View.VISIBLE);
                    mTvStatus.setText("No results");
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mTvStatus.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.setItems(state.getDisplayItems());
                    mAdapter.notifyDataSetChanged();
                }
                mEditSearch.setText(state.getKeywords());
                break;
            case ERROR:
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText("Error"); // This obviously should be more detailed
                mRecyclerView.setVisibility(View.GONE);
                mEditSearch.setText(state.getKeywords());
                break;
            default:
                throw new IllegalStateException("Unknown state" + state.getStatus());
        }
    }
}
