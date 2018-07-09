package com.example.sackerman.popularmovies.Utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/*
Credits: Scanners, InputSteams and Delimiters for one line:
https://ratherbsailing.wordpress.com/2011/11/01/reading-a-string-from-an-inputstream-in-one-line/
Tutorial on reading Strings as one line.
 */

public class NetworkUtilities {

    //Constants:
    final static String MOVIE_API_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key={";
    final static String MOVIE_API_URL = "https://api.themoviedb.org/3/movie";
    final static String API_KEY = "d4bae961a1fa0bbae35ee2a2461ef7db";
    final static String IMAGE_URL = "http://image.tmdb.org/t/p";
    final static String POSTER_SIZE = "W185";
    private static String POPULAR_SORT = "popular";
    private static String TOP_RATED_SORT = "top_rated";

    //Used for Threading Flag for inintialized Input Stream.
    private static boolean initialized = false;

    //Parsing Data for Movie Info.

    //TODO: 5/16/18  From the Ruberic:
    // In a background thread, app queries the
    // /movie/popular or /movie/top_rated API for the sort
    // criteria specified in the settings menu.

    public static URL jSonPopularListUrl(){
        Uri builtUri = Uri.parse(MOVIE_API_URL+"//"+POPULAR_SORT)
                .buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        //Create null URL & Try / Catch & create a new URL with uri toString.
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    private static URL jSonTopRatedListUrl(){
        Uri builtUri = Uri.parse(MOVIE_API_URL+TOP_RATED_SORT)
                .buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        //Create null URL & Try / Catch & create a new URL with uri toString.
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String posterPathUrl(String posterID){
        return IMAGE_URL + POSTER_SIZE + posterID;
    }

    //Gets result from the HTTP Request. Takes a URL object reads the data as a Input Stream.

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        if (!initialized) {
            CookieHandler.setDefault(new CookieHandler() {
                @Override
                public Map<String, List<String>> get(URI uri, Map<String, List<String>>
                        request) throws IOException {
                    return null;
                }

                @Override
                public void put(URI uri, Map<String, List<String>> response) throws IOException {

                }
            });
            initialized = true;
        }
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) { //Note: hasInput is true.
                response = scanner.next();
            }//Close the scanner & return the response.
            scanner.close();
            return response;
        } finally { //Always close the socket.
            urlConnection.disconnect();
        }
    }


    public static ArrayList<String> parseMovieDetails(String movieName){
        ArrayList<String> movies = new ArrayList<>();

        String movieSearch = MOVIE_API_SEARCH+API_KEY+"}"+"&query=J"+movieName;


        //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=movie;
        try {
            JSONObject movieData = new JSONObject(movieSearch);

            JSONArray movieDataArray = movieData.getJSONArray(String.valueOf(movieData));
            //ArrayList<String> = convertJsonArray(alsoKnownAsJSON);
            //JSONArray individualMovieData = null;
        }catch (JSONException e) {
            e.printStackTrace();
        }


        return movies;
    }//End parseMovieDetails Method.

}
