package ninja.bryansills.spotidemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kaaes.spotify.webapi.android.models.TrackSimple;

public class TrackAdapter extends RecyclerView.Adapter<TrackViewHolder> {

    private TrackAdapterListener listener;
    private List<TrackSimple> trackList;

    public TrackAdapter(List<TrackSimple> trackList, TrackAdapterListener listener) {
        this.trackList = trackList;
        this.listener = listener;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_track, parent, false);

        TrackViewHolder vh = new TrackViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        TrackViewHolder.bind(holder, getTrack(position), listener);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setTrackList(List<TrackSimple> newTracks) {
        trackList = newTracks;
        notifyDataSetChanged();
    }

    public TrackSimple getTrack(int position) {
        return trackList.get(position);
    }

    public interface TrackAdapterListener {
        void onTrackClick(String uri);
    }
}
