package com.example.jalvarez.amovieforyou.data.movies.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/29/17.
 * This is a file created for the project A-Movie-For-You
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MoviesRepository implements MoviesDataSource {

    private static MoviesRepository INSTANCE = null;

    private final MoviesDataSource mMoviesRemoteDataSource;

    private final MoviesDataSource mMoviesLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Movie> mCachedMovies;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    private MoviesRepository(@NonNull MoviesDataSource moviesRemoteDataSource,
                             @NonNull MoviesDataSource moviesLocalDataSource) {
        mMoviesRemoteDataSource = checkNotNull(moviesRemoteDataSource);
        mMoviesLocalDataSource = checkNotNull(moviesLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param moviesRemoteDataSource the backend data source
     * @param moviesLocalDataSource  the device storage data source
     * @return the {@link MoviesRepository} instance
     */
    public static MoviesRepository getInstance(MoviesDataSource moviesRemoteDataSource,
                                               MoviesDataSource moviesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository(moviesRemoteDataSource, moviesLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(MoviesDataSource, MoviesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }



    @Override
    public void getMovies(@NonNull final LoadMoviesCallback callback) {
        checkNotNull(callback);

        if (mCachedMovies != null && !mCacheIsDirty) {
            callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
            return;
        }

        if (mCacheIsDirty) {
            getMoviesFromRemoteDataSource(callback);
        } else {
            mMoviesLocalDataSource.getMovies(new LoadMoviesCallback() {
                @Override
                public void onMoviesLoaded(List<Movie> movies) {
                    refreshCache(movies);
                    callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getMoviesFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getMovie(@NonNull final String movieId, @NonNull final GetMovieCallback callback) {
        checkNotNull(movieId);
        checkNotNull(callback);

        Movie cachedMovie = getMovieWithId(movieId);

        if (cachedMovie != null && cachedMovie.getVideos().size() > 0) {
            callback.onMovieLoaded(cachedMovie);
            return;
        }

        mMoviesLocalDataSource.getMovie(movieId, new GetMovieCallback() {
            @Override
            public void onMovieLoaded(Movie movie) {
                if (mCachedMovies == null) {
                    mCachedMovies = new LinkedHashMap<>();
                }
                mCachedMovies.put(movie.getId(), movie);
                callback.onMovieLoaded(movie);
            }

            @Override
            public void onDataNotAvailable() {
                mMoviesRemoteDataSource.getMovie(movieId, new GetMovieCallback() {
                    @Override
                    public void onMovieLoaded(Movie movie) {
                        if (mCachedMovies == null) {
                            mCachedMovies = new LinkedHashMap<>();
                        }
                        mCachedMovies.put(movie.getId(), movie);
                        callback.onMovieLoaded(movie);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void refreshMovies() {
        mCacheIsDirty = true;
    }


    private void getMoviesFromRemoteDataSource(@NonNull final LoadMoviesCallback callback) {
        mMoviesRemoteDataSource.getMovies(new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                refreshCache(movies);
//                refreshLocalDataSource(movies);
                callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Movie> movies) {
        if (mCachedMovies == null) {
            mCachedMovies = new LinkedHashMap<>();
        }
        mCachedMovies.clear();
        for (Movie movie : movies) {
            mCachedMovies.put(movie.getId(), movie);
        }
        mCacheIsDirty = false;
    }

//    private void refreshLocalDataSource(List<Movie> movies) {
//        mMoviesLocalDataSource.deleteAllTasks();
//        for (movie task : movies) {
//            Save in db
//        }
//    }

    @Nullable
    private Movie getMovieWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedMovies == null || mCachedMovies.isEmpty()) {
            return null;
        } else {
            return mCachedMovies.get(id);
        }
    }
}
