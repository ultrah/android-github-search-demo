package com.example.githubbrowser.model.local;

public class GitHubRepoDisplayItem {

    private String name;
    private int score;
    private String avatarUrl;

    public GitHubRepoDisplayItem(String name, int score, String avatarUrl) {
        this.name = name;
        this.score = score;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
