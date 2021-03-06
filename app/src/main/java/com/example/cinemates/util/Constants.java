package com.example.cinemates.util;

import static java.util.Locale.getDefault;

import com.example.cinemates.BuildConfig;

import java.util.HashMap;

/**
 * @author Antonio Di Nuzzo
 * Created 21/04/2022 at 15:48
 */
public class Constants {
    public static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String YT_API_KEY = BuildConfig.YT_API_KEY;
    public static final String BaseURL = "https://api.themoviedb.org/3/";
    public static final String DEFAULT_SYSTEM_LANGUAGE = getDefault().getLanguage();
    public static final String ImageBaseURL = "https://image.tmdb.org/t/p/original";
    public static final String ImageBaseURLw780 = "https://image.tmdb.org/t/p/w780";
    public static final String ImageBaseURLw500 = "https://image.tmdb.org/t/p/w500";

    public static final String Attribution = "This product uses the TMDb API but is not endorsed or certified by TMDb.";
    public static final String Popular = "Popular";
    public static final String Upcoming = "Upcoming";
    public static final String Current = "Current";
    public static final String TopRated = "TopRated";
    public static final String YOUTUBE_COM_WATCH_V = "http://www.youtube.com/watch?v=";

    public static HashMap<Integer, String> getGenreMap() {
        HashMap<Integer, String> genreMap = new HashMap<>();
        genreMap.put(28, "Action");
        genreMap.put(12, "Adventure");
        genreMap.put(16, "Animation");
        genreMap.put(35, "Comedy");
        genreMap.put(80, "Crime");
        genreMap.put(99, "Documentary");
        genreMap.put(18, "Drama");
        genreMap.put(10751, "Family");
        genreMap.put(14, "Fantasy");
        genreMap.put(36, "History");
        genreMap.put(27, "Horror");
        genreMap.put(10402, "Music");
        genreMap.put(9648, "Mystery");
        genreMap.put(10749, "Romance");
        genreMap.put(878, "Science Fiction");
        genreMap.put(53, "Thriller");
        genreMap.put(10752, "War");
        genreMap.put(37, "Western");
        genreMap.put(10770, "TV Movie");

        return genreMap;
    }

}
