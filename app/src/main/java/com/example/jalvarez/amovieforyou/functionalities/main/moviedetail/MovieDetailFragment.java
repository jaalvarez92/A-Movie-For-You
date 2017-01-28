package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.Video;
import com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas.NowOnCinemasContract;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {


    private MovieDetailContract.Presenter mPresenter;

    public MovieDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MovieDetailFragment.
     */
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
        ((TextView) getView().findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) getView().findViewById(R.id.synopsis)).setText(movie.getSynopsis());
        ((SimpleDraweeView) getView().findViewById(R.id.backdrop)).setImageURI(movie.getBackdropURL());
        ((SimpleDraweeView) getView().findViewById(R.id.backdrop)).setAspectRatio(1.78f);
        ((LinearLayout)getView().findViewById(R.id.videosContainer)).removeAllViews();
        for(final Video video: movie.getVideos()) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.video_item, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) view.findViewById(R.id.title)).setText(video.getName());
            ((LinearLayout) getView().findViewById(R.id.videosContainer)).addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVideo(video);
                }
            });
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showVideo(Video video) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.getURL())));

    }

}
