package com.example.musicsteam2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongAdapter extends RecyclerView.Adapter<MyView> implements Filterable {// In the recycledView it is able to filter out the songs based on the characters used by the user
    private List<Song> songs;
    Context context;
    private List<Song>songsFiltered;
    public SongAdapter(List<Song> songs) {
        this.songs = songs;
        this.songsFiltered =songs;
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);//To view the favlist songs
        View songView = inflater.inflate(R.layout.item_song, parent, false);//To view the favlist songs
        MyView viewHolder = new MyView(songView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Song song = songsFiltered.get(position);

        TextView artist = holder.titleArtist;
        artist.setText(song.getArtists());
        TextView title = holder.titleTxt;
        title.setText((song.getTitle()));
        int imageId = song.getDrawable();
        holder.image1.setImageResource(imageId);
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.favList.remove(position);// when the remove button is clicked next to the song in the fav list, it will be deleted
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString =constraint.toString();
                if (charString.isEmpty()){
                    songsFiltered=songs;
                } else {
                    List<Song>filteredList = new ArrayList<Song>();
                    for (int i = 0; i < songs.size(); i++) {
                        if (songs.get(i).getTitle().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(songs.get(i));
                        }
                    }
                    songsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=songsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                songsFiltered= (List<Song>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
