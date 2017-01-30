package com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.recommendations.Recommendation;
import com.example.jalvarez.amovieforyou.data.dummy.RecommendationsDummyData;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class LatestRecommendationsPresenter implements LatestRecommendationsContract.Presenter {

    private final LatestRecommendationsContract.View mLatestRecommendationsView;


    public LatestRecommendationsPresenter(LatestRecommendationsContract.View latestRecommendationsView) {
        mLatestRecommendationsView = checkNotNull(latestRecommendationsView, "tasksView cannot be null!");
        mLatestRecommendationsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRecommendations(true);
    }


    @Override
    public void loadRecommendations() {
        loadRecommendations(true);
    }


    private void loadRecommendations(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mLatestRecommendationsView.setLoadingIndicator(true);
        }
        Recommendation[] recommendations = RecommendationsDummyData.getRecommendations();
        processRecommendations(Arrays.asList(recommendations));
        if (showLoadingUI) {
            mLatestRecommendationsView.setLoadingIndicator(false);
        }

    }


    private void processRecommendations(List<Recommendation> recommendations) {
        if (recommendations.isEmpty()) {
            processEmptyRecommendations();
        } else {
            mLatestRecommendationsView.showRecommendations(recommendations);
        }
    }

    private void processEmptyRecommendations() {
        mLatestRecommendationsView.showNoRecommendations();
    }


    @Override
    public void openRecommendationDetails(@NonNull Recommendation requestedRecommendation) {
        checkNotNull(requestedRecommendation, "requestedRecommendation cannot be null!");
        mLatestRecommendationsView.showRecommendationDetailsUi(requestedRecommendation.getId());
    }


}
