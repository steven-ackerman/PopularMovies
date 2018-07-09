package com.example.sackerman.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.databinding.DataBindingUtil;

import com.example.sackerman.popularmovies.Utils.NetworkUtilities;
import com.example.sackerman.popularmovies.databinding.ActivityDetailBinding;
import com.example.sackerman.popularmovies.Models.Movie;


import com.squareup.picasso.Picasso;


class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailDataBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDataBind = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(getString(R.string.Serialized));
        bindingContents(movie);
    }

    /**
     *
     * Data binding method for the detail view.
     *
     * Takes a Movie object from main activity.
     *
     * */
    private void bindingContents(Movie movie){

        //-----Poster------//
        String posterPath = NetworkUtilities.posterPathUrl(movie.getPosterPath());
        Picasso.with(this).load(posterPath).into(detailDataBind.posterDetailImage);

        //-----Bind and Set Title------//
        detailDataBind.title.setText(movie.getTitle());

        //-----Bind and Set Original Title------//
        detailDataBind.originalTitle.setText(movie.getOriginalTitle());

        //-----Bind and Set By User Rating------//
        String userStars = getString(R.string.out_of_ten);
        String userRating = Double.toString(movie.getUserRating()) + userStars;
        detailDataBind.userRating.setText(userRating);

        //-----Bind and Set Release Date------//
        detailDataBind.releaseDate.setText(movie.getReleaseDate());

        //-----Bind and Set OverView-----//
        detailDataBind.overView.setText(movie.getOverview());
    }
}
