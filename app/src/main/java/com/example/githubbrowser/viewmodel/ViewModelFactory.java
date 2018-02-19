package com.example.githubbrowser.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.githubbrowser.network.GitHubRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final GitHubRepository mGitHubRepository;

    public ViewModelFactory(GitHubRepository gitHubRepository) {
        mGitHubRepository = gitHubRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GitHubListViewModel.class)) {
            return (T) new GitHubListViewModel(mGitHubRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

