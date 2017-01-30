package com.example.jalvarez.amovieforyou.data.movies.source.remote;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.services.MoviesService;

import retrofit2.Callback;


/**
 * Created by jalvarez on 1/16/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class TMDBApiHelper {
    private Tmdb tmdb = new Tmdb("49870ba460de9c5d43ed226e5f316b7c");
    private MoviesService moviesService = tmdb.moviesService();


    public void getMoviesOnCinemas(Callback<MovieResultsPage> callback){
        moviesService.nowPlaying(1, "en").enqueue(callback);
    }


    public void getMovie(String mId, Callback<com.uwetrottmann.tmdb2.entities.Movie> callback){
        moviesService.summary(Integer.parseInt(mId),"en", new AppendToResponse(AppendToResponseItem.VIDEOS)).enqueue(callback);
    }



}
