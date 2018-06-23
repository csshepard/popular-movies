package com.example.chris.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.popularmovies.databinding.VideoListItemBinding;
import com.example.chris.popularmovies.themoviedb.model.VideoResult;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideosVH> {

    private static final String TAG = MovieListAdapter.class.getSimpleName();
    private final List<VideoResult> videos;
    private final Context context;
    private ItemClickListener mItemClickListener;


    VideoListAdapter(Context context) {
        this.context = context;
        this.videos = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideosVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        VideoListItemBinding itemBinding =
                VideoListItemBinding.inflate(layoutInflater, parent, false);
        return new VideosVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosVH holder, int position) {
        String videoName = videos.get(position).getName();
        holder.binding.videoName.setText(videoName);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addAll(List<VideoResult> videos){
        for (VideoResult video: videos){
            add(video);
        }
    }

    public void add(VideoResult video){
        if (video.getType().equalsIgnoreCase("trailer")) {
            this.videos.add(video);
            notifyItemInserted(videos.size() - 1);
        }

    }

    private void remove(VideoResult video){
        int position = videos.indexOf(video);
        if (position > -1){
            videos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while(getItemCount() > 0){
            remove(getItem(0));
        }
    }

    public class VideosVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final VideoListItemBinding binding;

        VideosVH(VideoListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            binding.playButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    VideoResult getItem(int position) {
        return videos.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
