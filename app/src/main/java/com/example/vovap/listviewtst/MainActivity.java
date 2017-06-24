package com.example.vovap.listviewtst;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends Activity {

    private final String firstName = "Po";
    private final String lastName = "Sun";

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    List<String> listItems = new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    private ListView listView;

    private AsyncTask<String, Void, List<String>> task;

    private Retrofit retrofit;
    public interface ICNDB {
        @GET("/jokes/random/{amt}")
        Call<IcndbJokeList> getJoke(@Path("amt") String amtJokes,
                                @Query("firstName") String firstName,
                                @Query("lastName") String lastName,
                                @Query("limitTo") String limitTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listView == null) {
            listView = (ListView) findViewById(R.id.listView);
        }

        adapter = new ArrayAdapter<>(this
                , simple_list_item_1
                , listItems);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        listView.setAdapter(adapter);
    }

    public void onGetJokes(View view){
        task = new NewsTask().execute();//new JokeTask().execute("1", firstName, lastName);
    }

    public void onGet100Jokes(View view){
        task = new JokeTask().execute("100", firstName, lastName);
    }

    public void onPressDeleteAll(View view){
        listItems.clear();
        adapter.notifyDataSetChanged();
    }

    private class NewsTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            List<String> titles = new ArrayList<>();

            Document doc = null;
            try {
                doc = Jsoup.connect("https://m.zn.ua/")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                        .referrer("http://www.google.com")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements newsHeadlines = doc.select("ul.news_list>li.news_item");

            for (Element e: newsHeadlines){
                String url = e.select("a.news_link").attr("href");
                String headline = e.select("span.news_title").text();
                titles.add(headline);
            }

            return titles;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            listItems.addAll(0, result);
            adapter.notifyDataSetChanged();
        }
    }

    private class JokeTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            ICNDB icndb = retrofit.create(ICNDB.class);
            List<String> jokes = new ArrayList<>();

            try {
                Call<IcndbJokeList> icndbJoke = icndb.getJoke(params[0], params[1], params[2], "[nerdy]");
                //                String url = icndbJoke.request().url().toString();
                jokes = icndbJoke.execute().body().getJoke();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jokes;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            listItems.addAll(0, result);
            adapter.notifyDataSetChanged();
        }
    }


}
