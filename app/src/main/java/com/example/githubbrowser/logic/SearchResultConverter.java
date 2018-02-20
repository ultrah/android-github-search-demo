package com.example.githubbrowser.logic;

import com.example.githubbrowser.model.local.GitHubRepoDisplayItem;
import com.example.githubbrowser.model.network.github.SearchResultItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResultConverter {

    public static List<GitHubRepoDisplayItem> convert(List<SearchResultItem> searchResultItems) {
        ArrayList<GitHubRepoDisplayItem> displayItems = new ArrayList<>();

        for(SearchResultItem item : searchResultItems) {
            String name = item.getName();
            int score = calculateScore(item);
            String avatarUrl = item.getOwner().getAvatarUrl();
            displayItems.add(new GitHubRepoDisplayItem(name, score, avatarUrl));
        }

        return displayItems;
    }

    private static int calculateScore(SearchResultItem item) {
        return item.getWatchers() * item.getStargazers_count() * item.getForks();
    }
}
