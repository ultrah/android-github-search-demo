package com.example.githubbrowser.model.logic;

import com.example.githubbrowser.model.network.pojo.SearchResultItem;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResultConverter {

    public static List<GitHubRepoDisplayItem> convert(List<SearchResultItem> searchResultItems) {
        ArrayList<GitHubRepoDisplayItem> displayItems = new ArrayList<>();

        for (SearchResultItem item : searchResultItems) {
            String name = item.getName();
            int score = calculateScore(item);
            String avatarUrl = item.getOwner().getAvatarUrl();
            displayItems.add(new GitHubRepoDisplayItem(name, score, avatarUrl));
        }

        return displayItems;
    }

    // GitHub supplies a score, but we do it it ourselves anyway
    private static int calculateScore(SearchResultItem item) {
        int score = 1;

        if (item.getWatchers() != 0) {
            score *= item.getWatchers();
        }

        if (item.getForks() != 0) {
            score *= item.getForks();
        }

        if (item.getStargazers_count() != 0) {
            score *= item.getStargazers_count();
        }

        return score;
    }
}
