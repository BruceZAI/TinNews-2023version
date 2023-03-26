package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BottomNavigationView can response to menuItem click behavior
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // mvc
        // FragmentContainerView -> view
        // NavHostFragment -> Controller
        // nav_graph -> data

        // when click on bottom nav menu, ask controller to set screen on the view

        // get controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        // router
        navController = navHostFragment.getNavController();

        // connect navController with BottomNavigationView
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        // tmp code, will remove
        getEverything();
    }

    void getEverything() {
        // NewsApi is the interface we will use actually
        NewsApi newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        Call<NewsResponse> newsResponseTask = newsApi.getEverything("tesla", 10);

        // sync vs async
        // UI running in Main thread
        // enqueue to execute the task in async
        // callback: finished the task, and back to current thread
        newsResponseTask.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                // get response
                if (response.isSuccessful()) { //  status code 200 ~ 300
                    Log.d("MainActivity", response.body().toString());
                } else {
                    Log.d("MainActivity", response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("MainActivity", t.toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}





