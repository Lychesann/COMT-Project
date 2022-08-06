package com.example.musicsteam2;

public class SongCollection {
    public static Song[] songs = new Song[7];

    public SongCollection() {
        Song thewayyoulooktonight = new Song("S1001",
                "The Way You Look Tonight",
                "Micheal Buble",
                "https://p.scdn.co/mp3-preview/b4893d249495bdad00828e6f0059b7e7d762da07?cid=2afe87a64b0042dabf51f37318616965",
                "4.66",
                R.drawable.michael_buble_collection);
        Song billejeans = new Song("S1002",
                "Bille Jeans",
                "Micheal Jackson",
                "https://p.scdn.co/mp3-preview/71638a1eac196a5daa9fbf152693585e323d8558?cid=2afe87a64b0042dabf51f37318616965",
                "4.9",
                R.drawable.billie_jean);
        Song photograph = new Song("S1003",
                "Photograph",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/34704823c55ae09f26988b106784f884bb781068?cid=2afe87a64b0042dabf51f37318616965",
                "4.32",
                R.drawable.photograph);
        Song dynamite = new Song("S1004",
                "Dynamite",
                "BTS",
                "https://p.scdn.co/mp3-preview/f8789cd023289fa8ea0a8d5e7c1b0001b998142d?cid=2afe87a64b0042dabf51f37318616965",
                "3.32",
                R.drawable.bts);
        Song justtheywayyouare = new Song("S1005",
                "Just The Way You Are",
                "Bruno Mars",
                "https://p.scdn.co/mp3-preview/fe45db93f0ed11c07cb0db0d6d422a8d80617df8?cid=2afe87a64b0042dabf51f37318616965",
                "3.68",
                R.drawable.brunomars1);

        Song acdc = new Song("S1006",
                "Highway To Hell",
                "AC/DC",
                "https://p.scdn.co/mp3-preview/3589df13595d1ab1a2667db423b76c7e1e86ed73?cid=2afe87a64b0042dabf51f37318616965",
                "3.47",
                R.drawable.acdc1);
        Song backstreetboys = new Song("S1007",
                "I Want It That Way",
                "Backstreet Boys",
                "https://p.scdn.co/mp3-preview/3d0e33c987e5eec29c69691f7aa56fb0ee96bd92?cid=2afe87a64b0042dabf51f37318616965",
                "3.56",
                R.drawable.backstreet1);

        songs[0] = thewayyoulooktonight;
        songs[1] = billejeans;
        songs[2] = photograph;
        songs[3] = dynamite;
        songs[4] = justtheywayyouare;
        songs[5] = acdc;
        songs[6] = backstreetboys;
    }

    public Song getCurrentSong(int currentSongId) {
        return songs[currentSongId];
    }

    public int searchSongById(String id) {
        for (int index = 0; index < songs.length; index++) {
            Song tempSong = songs[index];
            if (tempSong.getId().equals(id)) {
                return index;
            }
        }
        return -1;
    }
    public Song searchById(String id)
    {
        Song song = null;
        for(int index = 0;index <songs.length; index++)
    {
        song = songs[index];
        if(song.getId().equals(id))
        {return song;
        }}
        return song;
    }

    public int getNextSong(int currentSongIndex){
        if (currentSongIndex >= songs.length-1){
            return currentSongIndex;
        }
        else {
            return currentSongIndex +1;
        }
    }
    public int getPrevSong(int currentSongIndex){
        if(currentSongIndex <= 0){
            return currentSongIndex;
        }
        else {
            return currentSongIndex-1;
        }
    }
}
