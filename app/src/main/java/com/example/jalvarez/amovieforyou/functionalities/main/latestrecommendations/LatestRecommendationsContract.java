package com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.Recommendation;

import java.util.List;

/**
 * Created by jalvarez on 1/13/17.
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
