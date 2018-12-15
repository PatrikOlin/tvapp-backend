package com.tvapp.utils.constants;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public interface UrlConstants {
    public static final String MOVIE_DB = "TheMovieDB";
    public static final String THE_TV_DB = "TheTvDB";

    public static final String TVDB_SEARCH_URL = "https://api.thetvdb.com/search/series";
    public static final String TVDB_SHOW_DETAILS_URL = "https://api.thetvdb.com/series/";
    public static final String TVDB_LOGIN_URL = "https://api.thetvdb.com/login";
    public static final String TVDB_EPISODE_QUERY = "/episodes/query";

    public final static String MOVIEDB_SEARCH_URL = "https://api.themoviedb.org/3/search/tv";
    public final static String MOVIEDB_SHOW_DETAILS_URL = "https://api.themoviedb.org/3/tv/";
}
