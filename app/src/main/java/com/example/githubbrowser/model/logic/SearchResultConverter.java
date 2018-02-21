package com.example.githubbrowser.model.logic;

import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;
import com.example.githubbrowser.model.network.pojo.SearchResultItem;

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
