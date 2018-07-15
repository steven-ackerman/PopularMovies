package com.example.sackerman.popularmovies.Utils;

import android.net.Uri;

import com.example.sackerman.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {


    //URL
    private static final String IMDB_URL = "http://api.themoviedb.org/3/movie/";
    //Popular List
    private static final String POPULAR_SEARCH = "popular";
    //User Rated List.
    private static final String USER_RATED_SEARCH = "top_rated";

    //API Key
    private static final String API_KEY = "d4bae961a1fa0bbae35ee2a2461ef7db";
    private static final String API_PARAM = "api_key";

    /* Base URL for themoviedb.org API */
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    /* Path for poster size */
    private static final String POSTER_SIZE = "w185";

    private static boolean initialized = false;

    /**
     * Builds the URL to start a sorting by popularity.
     *
     */
    public static URL buildPopularListJsonUrl() {
        Uri builtUri = Uri.parse(IMDB_URL).buildUpon()
                .appendPath(POPULAR_SEARCH)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        return buildUrl(builtUri);
    }

    /**
     *
     * Builds URL for User Rated Sort
     *
     */
    public static URL buildUserRatedJsonUrl() {
        Uri builtUri = Uri.parse(IMDB_URL).buildUpon()
                .appendPath(USER_RATED_SEARCH)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        return buildUrl(builtUri);
    }

    /**
     * Builds String for getting poster data.
     *
     */
    public static String buildPosterPath(String posterId) {
        return IMAGE_URL + POSTER_SIZE + posterId;
    }


    /**
     *Converts Uri to Url.
     */
    private static URL buildUrl(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Getting the HTTP response.
     * This was code was adapted from a Java Client/Server I wrote.
     * See Repo: https://github.com/steven-ackerman
     * Look at the ThreadedClient/Server repo.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        if (!initialized) {
            CookieHandler.setDefault(new CookieManager());
            initialized = true;
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
