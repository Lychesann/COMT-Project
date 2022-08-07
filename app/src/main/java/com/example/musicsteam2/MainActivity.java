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
    static ArrayList<Song> favList = new ArrayList<Song>(); //This is to call the method by using Main Activity instead of typing ArrayList<Song>
    SharedPreferences sharedPreferences; // To store and retrieve data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences =getSharedPreferences("playList", MODE_PRIVATE); // The mode private is to make the files only accessible to me instead of others
        String albumns = sharedPreferences.getString("list", "");
        if (!albumns.equals(""))
        {
            TypeToken<ArrayList<Song>>token = new TypeToken<ArrayList<Song>>(){}; //
            Gson gson = new Gson();// when the Gson implementation is added, this code would enable Gson library
            favList =gson.fromJson(albumns,token.getType());
        }// These 4 codes is when if there are no songs in the favlist, it will not show any songs.
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
        String json = gson.toJson(favList);//making favlist a Json String
        SharedPreferences.Editor editor = sharedPreferences.edit();// When the songs are added to the favlist, the songs would be in the sharedpref file
        editor.putString("list", json);// it will be found in the sharedpref file
        editor.apply();// The string in the file will be added when this code is called
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