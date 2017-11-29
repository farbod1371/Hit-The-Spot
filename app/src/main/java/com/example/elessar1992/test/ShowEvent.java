package com.example.elessar1992.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.elessar1992.test.Model.Explore;
import com.example.elessar1992.test.Model.Item_;
import com.example.elessar1992.test.Service.FourSquareService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elessar1992 on 11/22/17.
 */

public class ShowEvent extends AppCompatActivity
{
    EditText etGeolocation, etQuery;
    Button btnSearch;
    ListView listView;
    String Client_ID = "BSQDX22EU4YYHOGSSPF2NHFC4QWNM1XWYNRUTTWRB3DJTBPW";
    String Client_Secret = "IRP4Y2X0CENP0QDV24ASTRG3M4WTYUPPPE2YMKO2CVVSXRJV";
    String apiVersion = "20130815";
    //String geoLocation = "40.7,-74";
    String ll = "37.7749,-122.4194";
    String query;
    String radius = "100000";


    List<Item_> item_list = new ArrayList<Item_>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showevents);
        listView = (ListView)findViewById(R.id.listivew);
        //findViewByIds();

        Button Restaurant = (Button) findViewById(R.id.Restaurant);
        Restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                exploreAsyncTask.execute();
                build_retrofit_and_get_response("restaurant");
            }
        });

        Button Art = (Button) findViewById(R.id.Art);
        Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                exploreAsyncTask.execute();
                build_retrofit_and_get_response("art");
            }
        });

        Button Tech = (Button) findViewById(R.id.Tech);
        Tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                exploreAsyncTask.execute();
                build_retrofit_and_get_response("tech");
            }
        });

        Button Nightclub = (Button) findViewById(R.id.Nightclub);
        Nightclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                exploreAsyncTask.execute();
                build_retrofit_and_get_response("nightclub");
            }
        });
        /*btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                exploreAsyncTask.execute();

                FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
                final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll, query, radius);
                //final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll);
                call.enqueue(new Callback<Explore>() {
                    @Override
                    public void onResponse(Call<Explore> call, Response<Explore> response)
                    {
                        item_list = response.body().getResponse().getGroups().get(0).getItems();
                        ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
                        listView.setAdapter(exploreListAdapter);
                    }

                    @Override
                    public void onFailure(Call<Explore> call, Throwable t)
                    {

                    }
                });
            }
        });*/
    }



    private void build_retrofit_and_get_response(String query)
    {
        FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
        final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll, query, radius);
        //final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll);
        call.enqueue(new Callback<Explore>() {
            @Override
            public void onResponse(Call<Explore> call, Response<Explore> response)
            {
                item_list = response.body().getResponse().getGroups().get(0).getItems();
                ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
                listView.setAdapter(exploreListAdapter);
            }

            @Override
            public void onFailure(Call<Explore> call, Throwable t)
            {

            }
        });
    }


    void findViewByIds(){
        //etGeolocation = (EditText) findViewById(R.id.et_geolocation);
        //etQuery = (EditText) findViewById(R.id.et_query);
        //btnSearch = (Button) findViewById(R.id.btn_search);
        listView = (ListView)findViewById(R.id.listivew);
    }

    public class ExploreAsyncTask extends AsyncTask<Void,Void,List<Item_>> {

        public ExploreAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Item_> doInBackground(Void... voids) {
            FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
            final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll, query, radius);

            try {
                Explore explore =  call.execute().body();
                item_list = explore.getResponse().getGroups().get(0).getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return item_list;
        }

        @Override
        protected void onPostExecute(List<Item_> item_s) {
            super.onPostExecute(item_s);
            ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_s);
            listView.setAdapter(exploreListAdapter);
        }
    }
}
