package ninja.bryansills.spotidemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kaaes.spotify.webapi.android.models.TrackSimple;

public class TrackViewHolder extends RecyclerView.ViewHolder {

    public TextView title, artist;

    public TrackViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.track_title);
        artist = (TextView) itemView.findViewById(R.id.track_artist);
    }

    public static void bind(TrackViewHolder holder, final TrackSimple track, final TrackAdapter.TrackAdapterListener listener) {
        holder.title.setText(track.name);
        holder.artist.setText(track.artists.get(0).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTrackClick(track.uri);
            }
        });
    }
}
