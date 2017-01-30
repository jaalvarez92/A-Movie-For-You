package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.example.jalvarez.amovieforyou.data.videos.Video;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

    private MovieDetailContract.Presenter mPresenter;

    public MovieDetailFragment() { }

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MovieDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        final ProgressBar progressBar =
                (ProgressBar) getView().findViewById(R.id.progress_bar);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        if (active)
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        else
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
    }

    @Override
    public void showMovie(Movie movie) {
        View view = getView();
        if (view != null) {
            ((TextView) view.findViewById(R.id.title)).setText(movie.getTitle());
            ((TextView) view.findViewById(R.id.synopsis)).setText(movie.getSynopsis());
            ((SimpleDraweeView) view.findViewById(R.id.backdrop)).setImageURI(movie.getBackdropURL());
            ((SimpleDraweeView) view.findViewById(R.id.backdrop)).setAspectRatio(1.78f);
            ((RatingBar) view.findViewById(R.id.rating)).setRating((float) movie.getRating());
            ((LinearLayout) view.findViewById(R.id.videosContainer)).removeAllViews();
            view.findViewById(R.id.rating).setVisibility(View.VISIBLE);
            for (final Video video : movie.getVideos()) {
                View videoView = View.inflate(getActivity(), R.layout.video_item, null);
                videoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TextView) videoView.findViewById(R.id.title)).setText(video.getName());
                ((LinearLayout) view.findViewById(R.id.videosContainer)).addView(videoView);
                videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVideo(video);
                    }
                });
            }
        }
    }

    @Override
    public void showError() {
        Log.d(MovieDetailFragment.class.getName(), "Error while loding movie detail");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showVideo(Video video) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.getURL())));
    }

}
