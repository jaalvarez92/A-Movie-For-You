package com.example.jalvarez.amovieforyou.data.source;

import com.example.jalvarez.amovieforyou.data.Movie;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.util.List;

import retrofit2.Callback;


/**
 * Created by jalvarez on 1/16/17.
 */

public class TMDBApiHelper {
    Tmdb tmdb = new Tmdb("49870ba460de9c5d43ed226e5f316b7c");
    MoviesService moviesService = tmdb.moviesService();


    public void getMoviesOnCinemas(Callback<MovieResultsPage> callback){
        moviesService.nowPlaying(1, "en").enqueue(callback);
    }



}
