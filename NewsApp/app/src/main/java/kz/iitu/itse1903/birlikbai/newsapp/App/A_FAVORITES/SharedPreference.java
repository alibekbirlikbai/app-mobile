package kz.iitu.itse1903.birlikbai.newsapp.App.A_FAVORITES;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import kz.iitu.itse1903.birlikbai.newsapp.App.models.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

	public static final String PREFS_NAME = "NEWS_APP";
	public static final String FAVORITES = "Article_Favorite";
	
	public SharedPreference() {
		super();
	}

	// This four methods are used for maintaining favorites.
	public void saveFavorites(Context context, List<Article> favorites) {
		SharedPreferences settings;
		Editor editor;

		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor = settings.edit();

		Gson gson = new Gson();
		String jsonFavorites = gson.toJson(favorites);

		editor.putString(FAVORITES, jsonFavorites);

		editor.commit();
	}

	public void addFavorite(Context context, Article article) {
		List<Article> favorites = getFavorites(context);
		if (favorites == null)
			favorites = new ArrayList<Article>();
		favorites.add(article);
		saveFavorites(context, favorites);

		System.out.println(article.toString());
	}

	public void removeFavorite(Context context, Article article) {
		ArrayList<Article> favorites = getFavorites(context);

		for (Article article_remove:favorites) {
			if (article_remove.getTitle().equals(article.getTitle())) {
				favorites.remove(article_remove);
			}
		}

		saveFavorites(context, favorites);



		System.out.println("removed");

	}

	public void removeAllFavorites(Context context, Article article) {
		List<Article> favorites = getFavorites(context);
		favorites.removeAll(favorites);
		saveFavorites(context, favorites);
	}

	public ArrayList<Article> getFavorites(Context context) {
		SharedPreferences settings;
		List<Article> favorites;

		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		if (settings.contains(FAVORITES)) {
			String jsonFavorites = settings.getString(FAVORITES, null);
			Gson gson = new Gson();
			Article[] favoriteItems = gson.fromJson(jsonFavorites,
					Article[].class);

			favorites = Arrays.asList(favoriteItems);
			favorites = new ArrayList<Article>(favorites);
		} else
			return null;

		return (ArrayList<Article>) favorites;
	}
}
