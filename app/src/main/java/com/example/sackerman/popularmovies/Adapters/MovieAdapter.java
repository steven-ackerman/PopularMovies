package com.example.sackerman.popularmovies.Adapters;
//Imports.

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.R;

import java.util.ArrayList;
import java.util.List;

import com.example.sackerman.popularmovies.Models.Movie;
import com.example.sackerman.popularmovies.Utils.NetworkUtilities;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;



public class MovieAdapter extends android.support.v7.widget.RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private final Context context;
    private MoviesAdapterOnClickHandler clickHandler;
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener onItemClickListener;

    /*Constructor*/
    public MovieAdapter(Context currentContext,MoviesAdapterOnClickHandler clickHandler){
        this.context = currentContext;
        this.clickHandler = clickHandler;
    }
    /*Interface: Recieves click messages. */
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    //Done: TODO: create movies_rv_item xml with Layout
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder (@NonNull ViewGroup parentView, int vType){
        //Get the context & inflate the view.
        View movieView = LayoutInflater.from(context).inflate(android.R.layout.test_list_item, parentView, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(movieView);
        return movieViewHolder;


    }//End onCreateViewHolder Method.

    @Override
    public void onBindViewHolder (@NonNull MovieViewHolder holder, int position){
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());


        //Image Views.
        /*Credit http://square.github.io/picasso */
        String posterPath = movie.getImageUrl();
        String urlPath = NetworkUtilities.posterPathUrl(posterPath);
        Picasso.with(context).load(urlPath).into((Target) holder.ivPoster);


        //Done: ToDo: Implement getting image via API.

    }//End onBindViewHolder

    //Get Number of Movies In ArrayList of Movie objects.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //Movie View Holder Class: Extends RecyclerView Class.
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvTitle;
        public ImageView ivPoster;
        private Context context;

        public MovieViewHolder(View itemView){
            //Title and Image View
            super(itemView);
            itemView.setOnClickListener(this);
            ivPoster = itemView.findViewById(R.layout.id.poster_image);

        }//End Constructor.

        @Override
        public void onClick(View context) {
            /*
            *Send current ID to the Details Activity.
            */
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            clickHandler.onClick(movie);

        }

        /*This is credit to user in Slack. Keeps from having to having to create a new
         * Movie Adapter when new movies are fetched.Pretty slick. */
        public void swapList(List<Movie> newMovies) {
            movies = (ArrayList<Movie>) newMovies;
            notifyDataSetChanged();
        }
    }//End MovieViewHolder Class.


}



