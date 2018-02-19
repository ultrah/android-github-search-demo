package com.example.githubbrowser;

import com.example.githubbrowser.network.GitHubNetworkRepository;
import com.example.githubbrowser.network.GitHubRepository;
import com.example.githubbrowser.viewmodel.ViewModelFactory;

public class Injector {

    public static ViewModelFactory provideViewModelFactory() {
        GitHubRepository dataSource = provideGitHubRepository();
        return new ViewModelFactory(dataSource);
    }

    public static GitHubRepository provideGitHubRepository() {
        return GitHubNetworkRepository.getInstance();
    }

}
