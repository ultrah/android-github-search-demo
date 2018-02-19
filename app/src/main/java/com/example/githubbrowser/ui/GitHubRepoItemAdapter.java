package com.example.githubbrowser.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.githubbrowser.R;
import com.example.githubbrowser.model.local.GitHubRepoDisplayItem;

import java.util.List;

public class GitHubRepoItemAdapter extends RecyclerView.Adapter<GitHubRepoItemAdapter.ViewHolder> {

    private List<GitHubRepoDisplayItem> mItems;

    public GitHubRepoItemAdapter(List<GitHubRepoDisplayItem> items) {
        mItems = items;
    }

    @Override
    public GitHubRepoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.github_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<GitHubRepoDisplayItem> items) {
        mItems = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv_name);
        }
    }
}
