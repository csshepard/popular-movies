package com.example.chris.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chris.popularmovies.databinding.ActivityMainBinding;
import com.example.chris.popularmovies.themoviedb.TheMovieDBAPI;
import com.example.chris.popularmovies.themoviedb.TheMovieDBService;
import com.example.chris.popularmovies.themoviedb.model.MovieResult;
import com.example.chris.popularmovies.themoviedb.model.MovieResultList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ItemClickListener, AdapterView.OnItemSelectedListener{

    enum SortOrder {
        MOST_POPULAR,
        TOP_RATED
    }
    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieListAdapter mAdapter = null;

    private String apiKey;
    private boolean isLoading = false;
    private int currentPage = 1;
    private int maxPage;
    private SortOrder currentSort = SortOrder.MOST_POPULAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        apiKey = getString(R.string.themoviedb_key);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.movieGrid.setLayoutManager(gridLayoutManager);

        mAdapter = new MovieListAdapter(getApplicationContext());
        mAdapter.setClickListener(this);
        binding.movieGrid.setAdapter(mAdapter);

        binding.movieGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && gridLayoutManager.getChildCount() + gridLayoutManager.findFirstVisibleItemPosition() >= gridLayoutManager.getItemCount()) {
                    connectAndGetNextPage();
                }

            }
        });

        connectAndGetFirstPage();

    }

    private Call<MovieResultList> getMovieService(SortOrder sortOrder, String apiKey, Integer page){
        switch (sortOrder) {
            case MOST_POPULAR:
                return TheMovieDBAPI.getClient().create(TheMovieDBService.class).getPopularMovies(apiKey, page);
            case TOP_RATED:
                return TheMovieDBAPI.getClient().create(TheMovieDBService.class).getTopRatedMovies(apiKey, page);
            default:
                throw new UnsupportedOperationException("Sort Order Not Supported");
        }
    }

    private void connectAndGetFirstPage() {

        currentPage = 1;

        Call<MovieResultList> call = getMovieService(currentSort, apiKey, null);
        call.enqueue(new Callback<MovieResultList>() {
            @Override
            public void onResponse(@NonNull Call<MovieResultList> call, @NonNull Response<MovieResultList> response) {
                MovieResultList body = response.body();
                if (body != null) {
                    maxPage = body.getTotalPages();
                    Log.i(TAG, "onResponse: maxPages: " + Integer.toString(maxPage));
                    List<MovieResult> movieResults = body.getMovieResults();
                    mAdapter.addAll(movieResults);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResultList> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void connectAndGetNextPage() {

        if (currentPage != maxPage) {
            isLoading = true;
            currentPage += 1;
            Log.i(TAG, "connectAndGetNextPage: getting page: " + Integer.toString(currentPage));

            Call<MovieResultList> call = getMovieService(currentSort, apiKey, currentPage);
            call.enqueue(new Callback<MovieResultList>() {
                @Override
                public void onResponse(@NonNull Call<MovieResultList> call, @NonNull Response<MovieResultList> response) {
                    MovieResultList body = response.body();
                    if (body != null) {
                        List<MovieResult> movieResults = body.getMovieResults();
                        mAdapter.addAll(movieResults);
                    }
                    isLoading = false;
                }

                @Override
                public void onFailure(@NonNull Call<MovieResultList> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem item = menu.findItem(R.id.sort_order_item);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_order_array, R.layout.sort_order_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        SortOrder newSort;
        if (item.equals(getString(R.string.most_popular))){
            newSort = SortOrder.MOST_POPULAR;
        } else if (item.equals(getString(R.string.top_rated))) {
            newSort = SortOrder.TOP_RATED;
        } else {
            throw new UnsupportedOperationException("Sort Order Not Supported");
        }

        if (newSort != currentSort) {
            currentSort = newSort;
            mAdapter.clear();
            connectAndGetFirstPage();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(View view, int position) {
        String url = mAdapter.getItem(position).getVoteAverage().toString();
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
    }
}
