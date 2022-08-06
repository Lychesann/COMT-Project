package com.example.musicsteam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SongCollection songCollection = new SongCollection();
    static ArrayList<Song> favList = new ArrayList<Song>();
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences =getSharedPreferences("playList", MODE_PRIVATE);
        String albumns = sharedPreferences.getString("list", "");
        if (!albumns.equals(""))
        {
            TypeToken<ArrayList<Song>>token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            favList =gson.fromJson(albumns,token.getType());
        }
    }

    public void sendDataToActivity(int index) {
        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);

    }

    public void handleSelection(View myView) {
        String resourceId = getResources().getResourceEntryName(myView.getId());
        int currentArrayIndex = songCollection.searchSongById(resourceId);
        Log.d("temasek", "the index in the array for this song is :" + currentArrayIndex);
        sendDataToActivity(currentArrayIndex);
    }

    public void addToFavourite(View view) {
        String songID = view.getContentDescription().toString();
        Song song = songCollection.searchById(songID);
        favList.add(song);
        Gson gson = new Gson();
        String json = gson.toJson(favList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();
        Log.d("gson", json);
        for (int i = 0; i < favList.size(); i++) {
            Log.d("Music123", "addToFavourite"+ favList.get(i).getId());
        }
        //Toast.makeText(this, "button is clicked", Toast.LENGTH_SHORT).show();
    }

    public void gotoFavouriteActivity(View view) {
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);

    }
}