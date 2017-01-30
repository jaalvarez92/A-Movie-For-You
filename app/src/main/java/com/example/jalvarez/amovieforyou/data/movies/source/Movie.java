package com.example.jalvarez.amovieforyou.data.movies.source;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.videos.Video;
import com.google.common.base.Objects;
import com.uwetrottmann.tmdb2.entities.Videos;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class Movie extends RealmObject{

    @NonNull
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String synopsis;

    @NonNull
    private String posterURL;

    @NonNull
    private String backdropURL;

    @NonNull
    private RealmList<Video> videos;

    private double rating;


    public Movie() {

    }

    public Movie(@NonNull String id, @NonNull String title, @NonNull String synopsis, @NonNull String posterURL, double rating){
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.posterURL = posterURL;
        this.backdropURL = posterURL;
        this.rating = rating;
        videos = new RealmList<>();
    }

    public Movie(@NonNull com.uwetrottmann.tmdb2.entities.Movie movie){
        String tmdbPosterResourcePrefix = "http://image.tmdb.org/t/p/w185/";
        String tmdbBackdropResourcePrefix = "http://image.tmdb.org/t/p/w780/";
        id = movie.id.toString();
        title = movie.title;
        synopsis = movie.overview;
        posterURL = tmdbPosterResourcePrefix + movie.poster_path;
        backdropURL = tmdbBackdropResourcePrefix + movie.backdrop_path;
        rating = movie.vote_average / 2;
        videos = new RealmList<>();
        if (movie.videos != null && movie.videos.results != null) {
            for (Videos.Video video : movie.videos.results) {
                if (video.site.equals("YouTube")) {
                    videos.add(new Video(video));
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getSynopsis(){
        return synopsis;
    }

    public String getPosterURL(){
        return posterURL;
    }

    public String getBackdropURL(){
        return backdropURL;
    }

    public double getRating() {
        return rating;
    }

    public RealmList<Video> getVideos() {
        return videos;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }


    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }


    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }


    public void setRating(double rating) {
        this.rating = rating;
    }


    public void setVideos(RealmList<Video> videos) {
        this.videos = videos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equal(id, movie.id) &&
                Objects.equal(title, movie.title) &&
                Objects.equal(synopsis, movie.synopsis);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, synopsis);
    }

    @Override
    public String toString() {
        return "Movie with title " + title;
    }



}
