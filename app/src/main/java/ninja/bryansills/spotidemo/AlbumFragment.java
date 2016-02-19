package ninja.bryansills.spotidemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.TrackSimple;
import retrofit.client.Response;

public class AlbumFragment extends Fragment implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String TAG = AlbumFragment.class.getName();
    private static final String CLIENT_ID = BuildConfig.SPOTIFY_CLIENT_ID;
    private static final String EXTRA_ACCESS_TOKEN = "EXTRA_ACCESS_TOKEN";

    private SpotifyApi spotifyApi;
    private Player mPlayer;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TrackAdapter adapter;

    public static AlbumFragment newInstance(String token) {
        AlbumFragment fragment = new AlbumFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_ACCESS_TOKEN, token);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.track_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<TrackSimple> items = new ArrayList<>();

        adapter = new TrackAdapter(items, new TrackAdapter.TrackAdapterListener() {
            @Override
            public void onTrackClick(String uri) {
                mPlayer.play(uri);
            }
        });
        recyclerView.setAdapter(adapter);

        String token = getArguments().getString(EXTRA_ACCESS_TOKEN);
        spotifyApi = new SpotifyApi();
        spotifyApi.setAccessToken(token);
        SpotifyService spotifyService = spotifyApi.getService();

        spotifyService.getAlbum("4wJ585ippGitIpRcGs1GD7", new SpotifyCallback<Album>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("BLARG", spotifyError.toString());
            }

            @Override
            public void success(Album album, Response response) {
                adapter.setTrackList(album.tracks.items);
                ((AlbumArtToolbar)getActivity()).setAlbumArt(album.images.get(0).url);
            }
        });

        Config playerConfig = new Config(getActivity(), token, CLIENT_ID);
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(AlbumFragment.this);
                mPlayer.addPlayerNotificationCallback(AlbumFragment.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "Could not initialize player: " + throwable.getMessage());
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onLoggedIn() {
        Log.d(TAG, "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d(TAG, "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d(TAG, "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d(TAG, "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d(TAG, "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(PlayerNotificationCallback.EventType eventType, PlayerState playerState) {
        Log.d(TAG, "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(PlayerNotificationCallback.ErrorType errorType, String errorDetails) {
        Log.d(TAG, "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }
}
