package com.example.sackerman.popularmovies.Models;

import java.io.Serializable;


/*
Credits:

The Movie Database API Documentation:
https://developers.themoviedb.org/3/getting-started/search-and-query-for-details

Tutorial for building the Movie App.
https://www.youtube.com/watch?v=FnlTIdJgg9s
https://www.youtube.com/watch?v=A5xcjzBC-sw

Serializable:


 */


public class Movie implements Serializable{

    //Class Vars:

    //Integers:
    private int id;

    //Strings:
    private String title;
    private String imageUrl;
    private String releaseDate;
    private String overview;
    private String originalTitle;

    //Doubles
    private double voteAvg;
    private double popularity;


    //Constructor:
    public Movie(String origTitle, String movieTitle, String posterImageURL, String overView, String releasedate,
                 double userRating, double popularity){
        this.originalTitle = origTitle;
        this.title = movieTitle;
        this.imageUrl = posterImageURL;
        this.overview = overView;
        this.releaseDate = releasedate;
        this.voteAvg = userRating;
        this.popularity = popularity;

    }

    //Getters & Setters.

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public String getReleaseDate() {return releaseDate;}

    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return imageUrl;
    }

    public void setPosterPath(String posterPath) {
        this.imageUrl = posterPath;
    }

    public double getUserRating() {
        return voteAvg;
    }

    public void setUserRating(double userRating) {
        this.voteAvg = userRating;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    
}//End Class.
