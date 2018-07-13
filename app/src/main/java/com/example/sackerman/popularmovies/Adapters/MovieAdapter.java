package com.example.sackerman.popularmovies.Adapters;

//Imports.

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sackerman.popularmovies.R;
import java.util.ArrayList;
import java.util.List;

import com.example.sackerman.popularmovies.Models.Movie;
import com.squareup.picasso.Picasso;




public class MovieAdapter extends android.support.v7.widget.RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private final Context context;
    private MoviesAdapterOnClickHandler clickHandler;
    private List<Movie> movies = new ArrayList<>();

    /*Constructor*/
    public MovieAdapter(Context currentContext, List<Movie> movies,MoviesAdapterOnClickHandler clickHandler){
        this.context = currentContext;
        this.movies = movies;
        this.clickHandler = clickHandler;
    }
    /*Interface: Receives click messages. */
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    //Done: TODO: create rv_movies xml with Layout
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder (@NonNull ViewGroup parentView, int vType){
        //Get the context & inflate the view.
        View movieView = LayoutInflater.from(context).inflate(R.layout.rv_movie, parentView, false);
        //return new MovieAdapterViewHolder(view);
        return new MovieViewHolder(movieView);


    }//End onCreateViewHolder Method.

    @Override
    public void onBindViewHolder (@NonNull MovieViewHolder holder, int position){
        Movie movie = movies.get(position);
        //holder.tvTitle.setText(movie.getTitle());



        //Image Views.
        /*Credit http://square.github.io/picasso */
        String posterPath = movie.getImageUrl();
        String urlPath = com.example.sackerman.popularmovies.
                Utils.NetworkUtilities.buildPosterPath(posterPath);
        Picasso.with(context).load(urlPath).into(holder.ivPoster);


        //Done: ToDo: Implement getting image via API.

    }//End onBindViewHolder

    //Get Number of Movies In ArrayList of Movie objects.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //Movie View Holder Class: Extends RecyclerView Class.
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final int layoutPosition = 0;
        public TextView tvTitle;
        public ImageView ivPoster;
        private Context context;

        public MovieViewHolder(View itemView){
            //Title and Image View
            super(itemView);
            itemView.setOnClickListener(this);
            ivPoster = itemView.findViewById(R.id.iv_poster_image);


        }//End Constructor.

        /*
         * Called when a view has been clicked
         */

        @SuppressLint("NewApi")
        @Override
        public void onClick(View context) {
            /*
            *Send current ID to the Details Activity.
            */
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Movie movie = movies.get(position);
                clickHandler.onClick(movie);
            }else {
                Toast.makeText(context.getContext(), "Error: Try Again.", Toast.LENGTH_SHORT).show();
            }


        }


    }//End MovieViewHolder Class.

    /*This is credit to user in Slack. Keeps from having to having to create a new
     * Movie Adapter when new movies are fetched.Pretty slick. */
    public void swapList(List<Movie> newMovies) {
        movies = (ArrayList<Movie>) newMovies;
        notifyDataSetChanged();
    }//End swapList method.


}//End MovieAdapter Class.



