package com.example.githubbrowser.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.githubbrowser.R;
import com.example.githubbrowser.model.local.GithubRepoDisplayItem;
import com.example.githubbrowser.viewmodel.GithubListViewModel;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private GithubListViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewModel.
        mModel = ViewModelProviders.of(this).get(GithubListViewModel.class);

        // Create the observer which updates the UI.
        final Observer repoObserver = new Observer<List<GithubRepoDisplayItem>>() {

            @Override
            public void onChanged(@Nullable List<GithubRepoDisplayItem> githubRepos) {
                Timber.d("onChanged() " + githubRepos);
                //TODO update UI
            }
        };

        mModel.getGithubRepos().observe(this, repoObserver);

        findViewById(R.id.btn_do_the_thing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModel.searchRepos("Foobar");
            }
        });
    }
}
