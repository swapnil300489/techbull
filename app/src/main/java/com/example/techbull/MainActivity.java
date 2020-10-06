package com.example.techbull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techbull.netwokcall.APIClient;
import com.example.techbull.netwokcall.APIInterface;
import com.example.techbull.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar id_movietoolbar;
    private TextView id_noData_found_txt;
    private RecyclerView id_movies_RCV;
    private Movie_Adapter movie_adapter;
    public static  String URL = "";
    private ProgressDialog pDialog ;
    private APIInterface apiInterface;
    private Button id_searchBtn;
    private EditText id_movie_edTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        id_searchBtn.setOnClickListener(v->{

            String movie = id_movie_edTxt.getText().toString().trim();

            if (movie.length() == 0){
                id_movie_edTxt.requestFocus();
                id_movie_edTxt.setError("Enter movie name");
            }else {

                getMovie(movie);
            }


        });


    }

    private void init() {

        id_movietoolbar = findViewById(R.id.id_movietoolbar);
        id_noData_found_txt = findViewById(R.id.id_noData_found_txt);
        id_movies_RCV = findViewById(R.id.id_movies_RCV);

        id_noData_found_txt.setText("Please enter movie name");

        id_movies_RCV.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        pDialog = new ProgressDialog(MainActivity.this);

        id_searchBtn = findViewById(R.id.id_searchBtn);
        id_movie_edTxt =findViewById(R.id.id_movie_edTxt);

        setSupportActionBar(id_movietoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_movietoolbar.setTitle("Movies");
        this.setTitle("Movies");

    }

    private void getMovie(String movie) {

        pDialog.setMessage("please wait....");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Movie> movieCall = apiInterface.MOVIE_CALL(movie, Config.API_KEY);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                pDialog.dismiss();

                Log.e("Response_Done", response.body().getResponse());

                Movie  movie = response.body();

                switch (movie.getResponse()){

                    case "True":
                        List<Movie.Search> searchList = movie.getSearch();

                        if (searchList.size() != 0){
                            id_movies_RCV.setVisibility(View.VISIBLE);
                            id_noData_found_txt.setVisibility(View.GONE);

                            movie_adapter = new Movie_Adapter(MainActivity.this,searchList);
                            id_movies_RCV.setAdapter(movie_adapter);

                        }else {
                            id_movies_RCV.setVisibility(View.GONE);
                            id_noData_found_txt.setVisibility(View.VISIBLE);
                            id_noData_found_txt.setText("No Movies Found");
                        }
                        break;
                    case "False":
                        id_movies_RCV.setVisibility(View.GONE);
                        id_noData_found_txt.setVisibility(View.VISIBLE);
                        id_noData_found_txt.setText("No Movies Found");
                        break;
                }



            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                pDialog.dismiss();
                id_movies_RCV.setVisibility(View.GONE);
                id_noData_found_txt.setVisibility(View.VISIBLE);
                id_noData_found_txt.setText("No Movies Found");
                Log.e("Response_Fail", t.getMessage());
            }
        });


    }
}