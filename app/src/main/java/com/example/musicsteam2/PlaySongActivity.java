package com.example.musicsteam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlaySongActivity extends AppCompatActivity {
    private String title = "";
    private String artists = "";
    private String fileLink = "";
    private int drawable;
    private int currentIndex = -1;

    private MediaPlayer player = new MediaPlayer();
    private Button btnPlayPause = null;
    private Button repeat = null;
    private Button shuffle = null;
    private SeekBar seekBar;
    private Handler handler = new Handler();

    boolean repeatFlag = false;
    boolean shuffleFlag = false;
    private SongCollection songCollection = new SongCollection();
    private SongCollection originalSongCollection = new SongCollection();//Original sequence of the songs before shuffle is on
    List<Song> shuffleList = Arrays.asList(songCollection.songs);// mixed sequence of the songs after shuffle is on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        repeat = findViewById(R.id.repeat);
        shuffle = findViewById(R.id.shuffle);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("temasek", "Retrived position is: " + currentIndex);
        displaySongBasedOnIndex();
        playSong(fileLink);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(player.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {//This would make the seekbar to start when the song starts playing
            @Override
            public void run() {
                seekBar.setProgress(player.getCurrentPosition());
            }
        }, 0, 1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    player.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying())
            {
                seekBar.setProgress(player.getCurrentPosition());
            } handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(p_bar);
        player.release();
    }

    public void displaySongBasedOnIndex() {
        Song song = songCollection.getCurrentSong(currentIndex);
        title = song.getTitle();
        artists = song.getArtists();
        fileLink = song.getFilelink();
        drawable = song.getDrawable();
        TextView txttitle = findViewById(R.id.txtSongTitle);
        txttitle.setText(title);
        TextView txtartists = findViewById(R.id.txtArtist);
        txtartists.setText(artists);
        ImageView iCoverArt = findViewById(R.id.imgCoverArt);
        iCoverArt.setImageResource(drawable);
    }

    public void playSong(String songUrl) {
        try {
            player.reset();
            player.setDataSource(songUrl);
            player.prepare();
            player.start();
            gracefullyStopsWhenMusicEnds();
            btnPlayPause.setBackgroundResource(R.drawable.pause1);
            setTitle(title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playOrPauseMusic(View view) {
        if (player.isPlaying()) {
            player.pause();
            seekBar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,1000);
            btnPlayPause.setBackgroundResource(R.drawable.play);
        }
        else
        {
            player.start();
            gracefullyStopsWhenMusicEnds();
            btnPlayPause.setBackgroundResource(R.drawable.pause1);
        }
    }

    private void gracefullyStopsWhenMusicEnds() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getBaseContext(), "The Song has ended and the the onCompletionListener is activated\n" + "The button text is changed to 'PLAY ", Toast.LENGTH_LONG).show();

                if (repeatFlag){
                    playOrPauseMusic(null);
                }else {
                    currentIndex = songCollection.getNextSong(currentIndex);
                    displaySongBasedOnIndex();
                    player.reset();
                    btnPlayPause.setBackgroundResource(R.drawable.play);
                }
            }
        });
        shuffle =findViewById(R.id.shuffle);
    }


    public void playNext(View view) {
        currentIndex = songCollection.getNextSong(currentIndex);
        Toast.makeText(this, "After Clicking playNext, \n the current index of this song\n" + "in the SongCollection Array is now :" + currentIndex, Toast.LENGTH_LONG).show();
        displaySongBasedOnIndex();
        playSong(fileLink);
    }
    public void playPrevious(View view){
        currentIndex = songCollection.getPrevSong(currentIndex);
        Toast.makeText(this, "After clicking playPrevious, /nthe current index of this song/n" +"in the SongCollection array is now :" +currentIndex, Toast.LENGTH_LONG).show();
        displaySongBasedOnIndex();
        playSong(fileLink);
    }
    public void repeatSong(View view) {
        if (repeatFlag) {//When Repeat is clicked
            repeat.setBackgroundResource(R.drawable.repeat_off);
        } else {// When repeat is on
            repeat.setBackgroundResource(R.drawable.repeat_on);
        }
        repeatFlag = !repeatFlag;
    }

    public void shuffleSong(View view) {
        if (shuffleFlag) {// When shuffle button is clicked
            shuffle.setBackgroundResource(R.drawable.shuffle_on);
            songCollection = new SongCollection();
        } else {// When shuffle button is clicked again
            shuffle.setBackgroundResource(R.drawable.shuffle_off);
            Collections.shuffle(shuffleList);
            shuffleList.toArray(songCollection.songs);
        }// these 2 codes is when the shuffle is off, the songs will go back into their original sequence
        shuffleFlag=!shuffleFlag;
    }


}



