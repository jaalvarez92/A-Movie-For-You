package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

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
import com.example.jalvarez.amovieforyou.data.Movie;
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
        ListView listView = (ListView) root.findViewById(R.id.movies_list);
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
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void showNoMovies() {
        noMoviesView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    private static class MoviesAdapter extends BaseAdapter {

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

        @Override
        public int getCount() {
            return mMovies.size();
        }

        @Override
        public Movie getItem(int i) {
            return mMovies.get(i);
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
                rowView = inflater.inflate(R.layout.movie_item, viewGroup, false);
            }

            final Movie movie = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            SimpleDraweeView image = (SimpleDraweeView) rowView.findViewById(R.id.image);

            titleTV.setText(movie.getTitle());
            image.setImageURI(movie.getImageURL());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onMovieClick(movie);
                }
            });

            return rowView;
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
