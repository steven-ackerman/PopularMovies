package com.example.sackerman.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.R;

import com.example.sackerman.popularmovies.Adapters.MovieAdapter;
import com.example.sackerman.popularmovies.Models.Movie;
import com.example.sackerman.popularmovies.Utils.NetworkUtilities;
import com.example.sackerman.popularmovies.MainActivity;

import java.net.URL;
import java.util.ArrayList;

import android.R.layout;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MoviesAdapterOnClickHandler {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;
    private URL url;
    private boolean sortedByPopularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.select_dialog_item);

        recyclerView = findViewById(R.layout.rv_movie);
        loadingIndicator = findViewById(R.layout.pb_loading_indicator);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, this);
        recyclerView.setAdapter(movieAdapter);
        showLoading();

        url = NetworkUtilities.jSonPopularListUrl();
        sortedByPopularity = true;
        new MoviesFetchTask().execute();
    }

    private void showView() {

        /* Hide Loading */
        loadingIndicator.setVisibility(View.INVISIBLE);
        /* Make data visible in RecyclerView */
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        /*Hide while loading*/
        recyclerView.setVisibility(View.INVISIBLE);
        /*Show view loading. */
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    //TODO: Finish MainActivity and Detail Activity.


    @Override
    public void onClick(Movie movie) {

        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        String serializedData = getString(R.string.Serialized);
        detailIntent.putExtra(serializedData, movie);
        startActivity(detailIntent);
    }

    /**
     * AsyncTask class that runs the JSON data parsing and Http response processing
     * on a background thread.
     */
    private class MoviesFetchTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            showLoading();
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try {
                URL url = NetworkUtilities.jSonPopularListUrl();
                ArrayList<Movie> movie;
                String moviesFromNetwork = NetworkUtilities.getResponseFromHttpUrl(url);
                ArrayList<String> moviesList = NetworkUtilities.parseMovieDetails(moviesFromNetwork);
                return moviesList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> movies) {

            movies.MovieAdapter.swapList(movies);
            showView();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * This method handles executing the sort when menu item gets pressed.
     *
     * @param item The MenuItem for the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Popularity" menu option
            case id.action_sort_by_popularity:
                if (!sortedByPopularity) {
                    url = NetworkUtilitiess.buildPopularListJsonUrl();
                    sortedByPopularity = true;
                    new MoviesFetchTask().execute();
                }
                return true;
            // Respond to a click on the "Ratings" menu option
            case id.action_sort_by_ratings:
                if (sortedByPopularity) {
                    url = NetworkUtilities.buildTopRatedListJsonUrl();
                    sortedByPopularity = false;
                    new MoviesFetchTask().execute();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}