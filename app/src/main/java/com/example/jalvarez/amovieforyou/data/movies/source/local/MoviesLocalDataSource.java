package com.example.jalvarez.amovieforyou.data.movies.source.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesDataSource;
import com.example.jalvarez.amovieforyou.data.videos.Video;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by jalvarez on 1/29/17.
 * This is a file created for the project A-Movie-For-You
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MoviesLocalDataSource implements MoviesDataSource {

    private static MoviesLocalDataSource INSTANCE;
    private Realm mRealm;

    private MoviesLocalDataSource(){
        mRealm = Realm.getDefaultInstance();
    }

    public static MoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {
        ArrayList<Movie> movies = new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> realmMovies = mRealm.where(Movie.class)
                .findAll();
        if (realmMovies.size() > 0) {
            for (Movie movie : realmMovies) {
                movies.add(movie);
            }
            callback.onMoviesLoaded(movies);
        }
        else {
            callback.onDataNotAvailable();
        }
//        realm.close();
    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {
//        Realm realm = Realm.getDefaultInstance();
        Movie realmMovie = mRealm.where(Movie.class)
                .equalTo("id", movieId)
                .findFirst();
        if (realmMovie != null) {
            callback.onMovieLoaded(realmMovie);
        }
        else{
            callback.onDataNotAvailable();
        }
//        realm.close();
    }

    @Override
    public void deleteAllMovies() {
//        Realm realm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Movie.class)
                        .findAll()
                        .deleteAllFromRealm();

            }
        });
//        realm.close();
    }

    @Override
    public void deleteMovie(@NonNull final String movieId) {
//        Realm realm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Movie.class)
                        .equalTo("id", movieId)
                        .findAll()
                        .deleteAllFromRealm();

            }
        });
//        realm.close();
    }

    @Override
    public void saveMovie(@NonNull final Movie movie) {
//        Realm realm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("Save Movie", "Get here");

                Movie realmMovie = realm.createObject(Movie.class);
                realmMovie.setId(movie.getId());
                realmMovie.setTitle(movie.getTitle());
                realmMovie.setSynopsis(movie.getSynopsis());
                realmMovie.setPosterURL(movie.getPosterURL());
                realmMovie.setRating(movie.getRating());
                realmMovie.setBackdropURL(movie.getBackdropURL());
                realmMovie.setVideos(new RealmList<Video>());


                for(Video video : movie.getVideos()){
                    Video realmVideo = realm.createObject(Video.class);
                    realmVideo.setName(video.getName());
                    realmVideo.setURL(video.getURL());
                    realmMovie.getVideos().add(realmVideo);
                }


            }
        });
//        realm.close();
    }

    @Override
    public void updateMovie(@NonNull final String movieId, @NonNull final Movie movie) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Movie realmMovie = realm.where(Movie.class)
                        .equalTo("id", movieId)
                        .findFirst();

                realmMovie.setId(movie.getId());
                realmMovie.setTitle(movie.getTitle());
                realmMovie.setSynopsis(movie.getSynopsis());
                realmMovie.setPosterURL(movie.getPosterURL());
                realmMovie.setRating(movie.getRating());
                realmMovie.setBackdropURL(movie.getBackdropURL());
                realmMovie.setVideos(new RealmList<Video>());


                for(Video video : movie.getVideos()){
                    Video realmVideo = realm.createObject(Video.class);
                    realmVideo.setName(video.getName());
                    realmVideo.setURL(video.getURL());
                    realmMovie.getVideos().add(realmVideo);
                }

            }
        });
    }

    @Override
    public void refreshMovies() { }

}
