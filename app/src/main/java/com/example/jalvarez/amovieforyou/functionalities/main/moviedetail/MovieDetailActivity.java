package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.util.ActivityUtils;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ID_PARAM = "ID";
    private MovieDetailPresenter mMovieDetailPresenter;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mId = getIntent().getStringExtra(ID_PARAM);


        // Set the fragment
        MovieDetailFragment movieDetailFragment =
                (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (movieDetailFragment == null) {
            // Create the fragment
            movieDetailFragment = MovieDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), movieDetailFragment, R.id.contentFrame);
        }

        // Create the presenter
        mMovieDetailPresenter = new MovieDetailPresenter(movieDetailFragment, mId);
    }
}
