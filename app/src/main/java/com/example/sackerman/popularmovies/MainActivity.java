package com.example.sackerman.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sackerman.popularmovies.Adapters.MovieAdapter;
import com.example.sackerman.popularmovies.Models.Movie;
import com.example.sackerman.popularmovies.Utils.JsonParse;
import com.example.sackerman.popularmovies.Utils.NetworkUtilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*Credits:
 * https://www.youtube.com/watch?v=S2aH5HCZVoQ
 * Tutorial:
 * http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/
 * https://stackoverflow.com/questions/36220180/udacity-popular-movie-stage-1-issue
 *
 *  */

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MoviesAdapterOnClickHandler {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;
    private List<Movie> movieList = new ArrayList<Movie>();
    private URL url;//Commented to Troubleshoot. = com.example.sackerman.popularmovies.Utils.NetworkUtilities.buildPopularListJsonUrl();
    private boolean sortedUserRating;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movie);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, movieList,this);
        recyclerView.setAdapter(movieAdapter);
        showLoading();

        url = NetworkUtilities.buildUserRatedJsonUrl();
        sortedUserRating = true;
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

    //Done TODO: Finish MainActivity and Detail Activity.


    @Override
    public void onClick(Movie movie) {

        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        String serializedData = getString(R.string.Serialized);
        detailIntent.putExtra(serializedData, movie);
        startActivity(detailIntent);
    }

    /**
     * AsyncTask does the JSON parsing, processes HTTP response on background thread.
     */
    public class MoviesFetchTask extends AsyncTask<Void, Void, List<Movie>> {

        //private Void[] voids;

        @Override
        protected void onPreExecute() {
            showLoading();
            super.onPreExecute();
        }

        /* @RequiresApi(api = Build.VERSION_CODES.KITKAT) */
        @Override
        protected List<Movie> doInBackground(Void...voids) {
            //this.voids = voids;

            try {
                String jsonResponse = NetworkUtilities.getResponseFromHttpUrl(url);
                return JsonParse.getMovieDataListFromString(jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            //This could be where my problem is. I might need to use a list.
            //Stopped here.

            movieAdapter.swapList(movies);
            showView();
        }
    }//End MoviesFetchTask


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles the Type of Sort the User selects.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Clicked on App Bar. Switch case by
        switch (item.getItemId()) {
            // Click on Menu Item for Popularity
            case R.id.action_sort_by_popularity:
                if (!sortedUserRating) {
                    url = NetworkUtilities.buildPopularListJsonUrl();
                    sortedUserRating = true;
                    new MoviesFetchTask().execute();
                }
                return true;
            //Click on Menu Item for Ratings
            case R.id.action_sort_by_ratings:
                if (sortedUserRating) {
                    url = NetworkUtilities.buildUserRatedJsonUrl();
                    sortedUserRating = false;
                    new MoviesFetchTask().execute();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}