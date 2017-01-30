package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesRepository;
import com.example.jalvarez.amovieforyou.data.movies.source.local.MoviesLocalDataSource;
import com.example.jalvarez.amovieforyou.data.movies.source.remote.MoviesRemoteDataSource;
import com.example.jalvarez.amovieforyou.util.ActivityUtils;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */


public class MovieDetailActivity extends AppCompatActivity {

    public static final String ID_PARAM = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String movieId = getIntent().getStringExtra(ID_PARAM);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        MovieDetailFragment movieDetailFragment =
                (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), movieDetailFragment, R.id.contentFrame);
        }

        new MovieDetailPresenter(MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(), MoviesLocalDataSource.getInstance()), movieDetailFragment, movieId);
    }
}
