package com.example.githubbrowser;

import com.example.githubbrowser.model.logic.SearchResultConverter;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;
import com.example.githubbrowser.model.network.pojo.SearchResult;
import com.example.githubbrowser.model.network.pojo.SearchResultItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SearchResultConverterUnitTest {

    @Test
    public void shouldConvertData() throws Exception {
        List<GitHubRepoDisplayItem> listItems = SearchResultConverter.convert(getMockSearchResultItem().getItems());
        assertEquals(listItems.get(0).getScore(), 2 * 3 * 4);
    }

    private SearchResult getMockSearchResultItem() {
        SearchResultItem item = new SearchResultItem();
        item.setId(1);
        item.setForks(2);
        item.setStargazers_count(3);
        item.setWatchers(4);
        item.setName("Foobar");

        SearchResultItem.Owner owner = new SearchResultItem.Owner();
        owner.setAvatarUrl("Barbaz");
        owner.setId(5);

        item.setOwner(owner);

        SearchResult searchResult = new SearchResult();
        searchResult.setItems(Collections.singletonList(item));

        return searchResult;
    }


}