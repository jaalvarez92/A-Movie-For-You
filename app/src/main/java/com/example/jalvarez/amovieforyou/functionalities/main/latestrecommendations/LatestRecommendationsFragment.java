package com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.data.recommendations.Recommendation;

import java.util.ArrayList;
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

public class LatestRecommendationsFragment extends Fragment implements LatestRecommendationsContract.View {


    private LatestRecommendationsContract.Presenter mPresenter;

    private RecommendationsAdapter mListAdapter;

    private View mNoRecommendationsView;


    public LatestRecommendationsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NowOnCinemasFragment.
     */
    public static LatestRecommendationsFragment newInstance() {
        return new LatestRecommendationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new RecommendationsAdapter(new ArrayList<Recommendation>(0), mItemListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull LatestRecommendationsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_latest_recommendations, container, false);

        // Set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.recommendations_list);
        listView.setAdapter(mListAdapter);

        // Set up  no tasks view
        mNoRecommendationsView = root.findViewById(R.id.noRecommendations);
        TextView noRecommendationsAddView = (TextView) root.findViewById(R.id.noRecommendationsAdd);
        noRecommendationsAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAddTask();
            }
        });
        return root;
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        final ProgressBar progressBar =
                (ProgressBar) getView().findViewById(R.id.progress_bar);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        if (active)
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        else
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
    }

    @Override
    public void showRecommendations(List<Recommendation> recommendations) {
        mListAdapter.replaceData(recommendations);
        mNoRecommendationsView.setVisibility(View.GONE);
    }

    @Override
    public void showRecommendationDetailsUi(String recommendationId) {

    }

    @Override
    public void showNoRecommendations() {
        mNoRecommendationsView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    private static class RecommendationsAdapter extends BaseAdapter {

        private List<Recommendation> mRecommendations;
        private RecommendationItemListener mItemListener;

        RecommendationsAdapter(List<Recommendation> recommendations, RecommendationItemListener itemListener) {
            setList(recommendations);
            mItemListener = itemListener;
        }

        void replaceData(List<Recommendation> recommendations) {
            setList(recommendations);
            notifyDataSetChanged();
        }

        private void setList(List<Recommendation> recommendations) {
            mRecommendations = checkNotNull(recommendations);
        }

        @Override
        public int getCount() {
            return mRecommendations.size();
        }

        @Override
        public Recommendation getItem(int i) {
            return mRecommendations.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.recommendation_item, viewGroup, false);
            }

            final Recommendation recommendation = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            TextView dateTV = (TextView) rowView.findViewById(R.id.date);

            titleTV.setText(recommendation.getMovie().getTitle());
            dateTV.setText(recommendation.getDate().toString());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onRecommendationClick(recommendation);
                }
            });

            return rowView;
        }
    }

    public interface RecommendationItemListener {

        void onRecommendationClick(Recommendation clickedRecommendation);
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    RecommendationItemListener mItemListener = new RecommendationItemListener(){
        @Override
        public void onRecommendationClick(Recommendation clickedRecommendation) {
            mPresenter.openRecommendationDetails(clickedRecommendation);
        }
    };

}
