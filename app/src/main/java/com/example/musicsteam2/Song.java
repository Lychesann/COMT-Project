package com.example.musicsteam2;

public class Song {
    private String id;
    private String title;
    private String artist;
    private String filelink;
    private String songLength;
    private int drawable;

    public Song(String id, String title, String artist, String filelink, String songLength, int drawable){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filelink = filelink;
        this.songLength = songLength;
        this.drawable = drawable;
    }
    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getArtists(){return artist;}
    public String getFilelink(){return filelink;}
    public String getSongLength(){return songLength;}
    public int getDrawable(){return drawable;}

}

