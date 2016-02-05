package ninja.bryansills.spotidemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kaaes.spotify.webapi.android.models.TrackSimple;

public class TrackViewHolder extends RecyclerView.ViewHolder {

    public TextView number, title, length;

    public TrackViewHolder(View itemView) {
        super(itemView);

        number = (TextView) itemView.findViewById(R.id.track_number);
        title = (TextView) itemView.findViewById(R.id.track_title);
        length = (TextView) itemView.findViewById(R.id.track_length);
    }

    public static void bind(TrackViewHolder holder, final TrackSimple track, final TrackAdapter.TrackAdapterListener listener) {
        holder.number.setText(String.valueOf(track.track_number));
        holder.title.setText(track.name);
        holder.length.setText(TimeUtils.formatDuration(track.duration_ms));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTrackClick(track.uri);
            }
        });
    }
}
