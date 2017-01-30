package com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.recommendations.Recommendation;

import java.util.List;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class LatestRecommendationsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showRecommendations(List<Recommendation> recommendations);

        void showRecommendationDetailsUi(String recommendationId);

        void showNoRecommendations();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadRecommendations();

        void openRecommendationDetails(@NonNull Recommendation requestedRecommendation);

    }
}
