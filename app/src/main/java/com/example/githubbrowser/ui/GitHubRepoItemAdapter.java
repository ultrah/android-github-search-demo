package com.example.githubbrowser.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubbrowser.R;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;
import com.example.githubbrowser.util.GlideApp;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvRepoName.setText(mItems.get(position).getName());
        holder.mTvScore.setText(Integer.toString(mItems.get(position).getScore()));

        GlideApp.with(holder.mIvAvatar.getContext())
                .load(mItems.get(position).getAvatarUrl())
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(holder.mIvAvatar);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<GitHubRepoDisplayItem> items) {
        mItems = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvRepoName;
        TextView mTvScore;
        ImageView mIvAvatar;

        ViewHolder(View v) {
            super(v);
            mTvRepoName = v.findViewById(R.id.tv_repo_name);
            mTvScore = v.findViewById(R.id.tv_score);
            mIvAvatar = v.findViewById(R.id.iv_avatar);
        }
    }
}
