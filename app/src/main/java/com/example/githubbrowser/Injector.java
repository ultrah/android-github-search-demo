package com.example.githubbrowser;

import com.example.githubbrowser.model.network.GitHubNetworkRepository;
import com.example.githubbrowser.model.network.GitHubRepository;
import com.example.githubbrowser.viewmodel.ViewModelFactory;

public class Injector {

    public ViewModelFactory provideViewModelFactory() {
        GitHubRepository dataSource = provideGitHubRepository();
        return new ViewModelFactory(dataSource);
    }

    public GitHubRepository provideGitHubRepository() {
        return GitHubNetworkRepository.getInstance();
    }

}
