package com.rahuls.spacex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String FETCHURL = "https://api.spacexdata.com/v4/crew";
    List<Crew> recipes;
    private RecyclerView recyclerview;
    private ArrayList<Crew> arrayList;
    private RVAdapter adapter;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        recyclerview = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        adapter = new RVAdapter(this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);

        if (isNetworkAvailable()) {
            fetchfromServer();
        } else {
            fetchfromRoom();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void fetchfromRoom() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                List<Member> recipeList = DatabaseClient.getInstance(MainActivity.this).getAppDatabase().memberDao().getAll();
                arrayList.clear();
                for (Member rep: recipeList) {
                    Crew repo = new Crew(rep.getId(),
                            rep.getName(),
                            rep.getAgency(),
                            rep.getImage(),
                            rep.getWikipedia(),
                            rep.getStatus());
                    arrayList.add(repo);
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        thread.start();


    }

    private void fetchfromServer() {
        pb.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(FETCHURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            if (response == null) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        try{
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject country = response.getJSONObject(i);


                                Crew continent = new Crew(
//                                        country.getLong("id"),
//                                        country.getString("name"),
//                                        country.getString("agency"),
//                                        country.getString("image"),
//                                        country.getString("wikipedia"),
//                                        country.getString("status")
                                );

//                                 Get the current student (json object) data
                                continent.setName(country.getString("name"));
                                continent.setAgency(country.getString("agency"));
                                continent.setImage(country.getString("image"));
                                continent.setWikipedia(country.getString("wikipedia"));
                                continent.setStatus(country.getString("status"));

                                // Display the formatted json data in text view
                                arrayList.add(continent);
                            }
                        }catch (JsonSyntaxException | JSONException e){
                            e.printStackTrace();
                        }


                            // refreshing recycler view
                            adapter.notifyDataSetChanged();

                            pb.setVisibility(View.GONE);

                            saveTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                pb.setVisibility(View.GONE);
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setShouldCache(false);

        requestQueue.add(request);
    }


    private void saveTask() {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                recipes = arrayList;

                if(recipes == null) {
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().memberDao().insert(new  Member());
                    return null;
                }

                //creating a task

                for (int i = 0; i < recipes.size(); i++) {
                    Member recipe= new  Member();
                    recipe.setId(recipes.get(i).getId());
                    recipe.setName(recipes.get(i).getName());
                    recipe.setAgency(recipes.get(i).getAgency());
                    recipe.setImage(recipes.get(i).getImage());
                    recipe.setWikipedia(recipes.get(i).getWikipedia());
                    recipe.setStatus(recipes.get(i).getStatus());
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().memberDao().insert(recipe);
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    public void deleteAll(View view) {
        DatabaseClient.getInstance(MainActivity.this).getAppDatabase().memberDao().delete();
        fetchfromRoom();
    }

    public void refresh(View view) {
        fetchfromServer();
    }
}