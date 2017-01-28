package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.functionalities.main.moviedetail.MovieDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowOnCinemasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowOnCinemasFragment extends Fragment implements NowOnCinemasContract.View {


    private NowOnCinemasContract.Presenter mPresenter;

    private MoviesAdapter mListAdapter;

    private View noMoviesView;


    public NowOnCinemasFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NowOnCinemasFragment.
     */
    public static NowOnCinemasFragment newInstance() {
        return new NowOnCinemasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new MoviesAdapter(new ArrayList<Movie>(0), mItemListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull NowOnCinemasContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_now_on_cinemas, container, false);

        // Set up tasks view
        RecyclerView listView = (RecyclerView) root.findViewById(R.id.movies_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        listView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(mListAdapter);

        // Set up  no tasks view
        noMoviesView = root.findViewById(R.id.noMovies);
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
    public void showMovies(List<Movie> movies) {
        mListAdapter.replaceData(movies);
        noMoviesView.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetailsUi(String movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.ID_PARAM, movieId);
        startActivity(intent);
    }

    @Override
    public void showNoMovies() {
        noMoviesView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    private static class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            final Movie movie = mMovies.get(position);
            // - replace the contents of the view with that element
            holder.mTitleTextView.setText(movie.getTitle());
            holder.mSynopsisTextView.setText(movie.getSynopsis());
            holder.mPosterImageView.setImageURI(movie.getPosterURL());
            holder.mPosterImageView.setAspectRatio(0.67f);
            holder.mRatingBar.setRating((float) movie.getRating());
            holder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onMovieClick(movie);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public View mContainer;
            public TextView mTitleTextView;
            public TextView mSynopsisTextView;
            public SimpleDraweeView mPosterImageView;
            public RatingBar mRatingBar;


            public ViewHolder(View v) {
                super(v);
                mContainer = v;
                mTitleTextView = (TextView) v.findViewById(R.id.title);
                mSynopsisTextView = (TextView) v.findViewById(R.id.synopsis);
                mPosterImageView = (SimpleDraweeView) v.findViewById(R.id.image);
                mRatingBar = (RatingBar) v.findViewById(R.id.rating);
            }
        }


        private List<Movie> mMovies;
        private MovieItemListener mItemListener;

        public MoviesAdapter(List<Movie> movies, MovieItemListener itemListener) {
            setList(movies);
            mItemListener = itemListener;
        }

        public void replaceData(List<Movie> movies) {
            setList(movies);
            notifyDataSetChanged();
        }

        private void setList(List<Movie> movies) {
            mMovies = checkNotNull(movies);
        }
    }

    public interface MovieItemListener {

        void onMovieClick(Movie clickedMovie);
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    MovieItemListener mItemListener = new MovieItemListener(){
        @Override
        public void onMovieClick(Movie clickedMovie) {
            mPresenter.openMovieDetails(clickedMovie);
        }
    };

}
