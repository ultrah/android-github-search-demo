package com.example.githubbrowser.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.githubbrowser.model.GithubRepo;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class GithubListViewModel extends AndroidViewModel {

    private MutableLiveData<List<GithubRepo>> mGithubRepos;

    public GithubListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<GithubRepo>> getGithubRepos() {
        if (mGithubRepos == null) {
            mGithubRepos = new MutableLiveData<>();
        }
        return mGithubRepos;
    }

    public void fetchData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Timber.d("fetchData() - Sending data");

                mGithubRepos.setValue(Collections.EMPTY_LIST);
            }
        }).run();
    }
}
