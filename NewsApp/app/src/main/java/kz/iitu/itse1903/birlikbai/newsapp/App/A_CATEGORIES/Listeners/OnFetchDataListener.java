package kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Listeners;


import java.util.List;

import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Models.NewsHeadline;

public interface OnFetchDataListener<T> {
    void onFetchData(List<NewsHeadline> data, String message);
    void onError(String message);

}
