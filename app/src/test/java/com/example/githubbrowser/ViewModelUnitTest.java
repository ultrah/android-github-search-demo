package com.example.githubbrowser;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.githubbrowser.model.network.GitHubRepository;
import com.example.githubbrowser.model.network.pojo.SearchResult;
import com.example.githubbrowser.model.network.pojo.SearchResultItem;
import com.example.githubbrowser.viewmodel.GitHubListViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelUnitTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void shouldEmmitDataCorrectly() throws Exception {
        // Given a mocked git repository
        GitHubRepository mockRepo = new GitHubRepository() {

            @Override
            public Single<SearchResult> search(String keywords) {
                return Single.just(getMockSearchResultItem());
            }
        };

        GitHubListViewModel viewModel = new GitHubListViewModel(mockRepo);
        Observer<GitHubListViewModel.State> mockObserver = mock(Observer.class);
        viewModel.getState().observeForever(mockObserver);

        // When a search request is made to the view model
        viewModel.searchRepos("Foobar");

        // Then the state should change two times
        // TODO needs more thorough testing
        verify(mockObserver, times(2)).onChanged(any(GitHubListViewModel.State.class));
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