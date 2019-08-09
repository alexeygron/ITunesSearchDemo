package dev.tunse.demo.itunessearch.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.tunse.demo.itunessearch.R;
import dev.tunse.demo.itunessearch.model.Track;

public class ListTrackAdapter  extends RecyclerView.Adapter<ListTrackAdapter.Holder> {

    private Context context;
    private List<Track> mData;

    public ListTrackAdapter(Context context){
        this.context = context;
        mData = new ArrayList<>();
    }

    void setData(List<Track> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_track, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Track track = mData.get(position);
        holder.bindDoctor(track);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.trackName) TextView trackName;
        @BindView(R.id.artistName) TextView artistName;
        @BindView(R.id.genre) TextView genre;
        @BindView(R.id.artwork) ImageView artwork;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDoctor(Track track) {
            trackName.setText(track.getTrackName());
            artistName.setText(track.getArtistName());
            genre.setText("Жанр: " + track.getGenre() + " / " + "Цена: " + track.getTrackPrice());
            // Using Glide library, loading artwork for track, and set him in view
            Glide.with(context).load(track.getArtworkUrl()).into(artwork);
        }
    }
}
