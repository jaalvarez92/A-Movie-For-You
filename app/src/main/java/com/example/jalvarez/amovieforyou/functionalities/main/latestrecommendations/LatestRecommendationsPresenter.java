package com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.Recommendation;
import com.example.jalvarez.amovieforyou.data.dummy.RecommendationsDummyData;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/13/17.
 */

public class LatestRecommendationsPresenter implements LatestRecommendationsContract.Presenter {

    private final LatestRecommendationsContract.View mLatestRecommendationsView;
    private boolean mFirstLoad = true;



    public LatestRecommendationsPresenter(LatestRecommendationsContract.View latestRecommendationsView) {
        mLatestRecommendationsView = checkNotNull(latestRecommendationsView, "tasksView cannot be null!");
        mLatestRecommendationsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRecommendations(false);
    }


    @Override
    public void loadRecommendations() {

    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
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
            // Show a message indicating there are no tasks for that filter type.
            processEmptyRecommendations();
        } else {
            // Show the list of tasks
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
