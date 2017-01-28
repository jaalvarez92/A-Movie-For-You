package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.Recommendation;
import com.example.jalvarez.amovieforyou.data.Video;

import java.util.List;

/**
 * Created by jalvarez on 1/13/17.
 */

public class MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovie(Movie movie);

        void showError();

        boolean isActive();

        void showVideo(Video video);

    }

    interface Presenter extends BasePresenter {

        void loadMovie();


    }
}
