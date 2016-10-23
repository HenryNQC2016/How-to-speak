package nqc.henry.howtospeak.acitvity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Random;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.model.DeveloperKey;
import nqc.henry.howtospeak.model.SearchResponse;
import nqc.henry.howtospeak.model.Video;

public class VideoActivity extends YouTubeFailureRecoveryActivity implements
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        YouTubePlayer.OnFullscreenListener {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    private final int maxResults = 1500;
    private final int maxPresentResult = 50;
    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private String videoId;
    private boolean fullscreen;
    private String query;
    private ArrayList<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //videoId=extra.getString("videoId");


        if (bundle.getString("query") != null) {
            query = bundle.getString("query");
        }

        // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.

        playerView = (YouTubePlayerView) findViewById(R.id.player);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        doLayout();
        videos = new ArrayList<Video>();
        String JsonStr = "{\"videoList\":[{\"videoId\":\"1X2LwclN878\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"2R3GQMyjloo\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"2ZCVdpjJNro\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"5QhIK43IVA8\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"5taPKUVhlGI\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"}]}";
        loadJson(JsonStr);
        randomResult();

    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams =
                (LinearLayout.LayoutParams) playerView.getLayoutParams();
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            playerParams.height = LinearLayout.LayoutParams.MATCH_PARENT;

        } else {
            playerParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            playerParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }

    }


    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
        this.player = player;
        // Specify that we want to handle fullscreen behavior ourselves.
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);
        Random rand = new Random();
        int value = rand.nextInt(videos.size());
        if (!b) {
            player.loadVideo(videos.get(value).getVideoId(), 1000 * 60);
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
        //this.player=null;
    }
    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (player != null) {
                //player.cueVideo(videoId);
                player.loadVideo(videoId,5000);
            }
        }
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        this.player = null;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int controlFlags = player.getFullscreenControlFlags();
        if (isChecked) {
            // If you use the FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE, your activity's normal UI
            // should never be laid out in landscape mode (since the video will be fullscreen whenever the
            // activity is in landscape orientation). Therefore you should set the activity's requested
            // orientation to portrait. Typically you would do this in your AndroidManifest.xml, we do it
            // programmatically here since this activity demos fullscreen behavior both with and without
            // this flag).
            setRequestedOrientation(PORTRAIT_ORIENTATION);
            controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            controlFlags &= ~YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
        }
        player.setFullscreenControlFlags(controlFlags);
    }

    @Override
    public void onClick(View view) {
        player.setFullscreen(!fullscreen);
    }

    private void loadJson(String json) {

        SearchResponse searchResponse = new SearchResponse();
        ArrayList<Video> videoList = new ArrayList<>();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
            //lay json array
            JsonArray jsonArray = jsonObject.getAsJsonArray("videoList");

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject objectVideo = jsonArray.get(i).getAsJsonObject();
                Video video = new Video();
                video.setVideoId(objectVideo.get("videoId").getAsString());
                video.setVideoName(objectVideo.get("videoName").getAsString());
                video.setTime(objectVideo.get("time").getAsString());
                video.setChanelName(objectVideo.get("chanelName").getAsString());
                video.setView_count(objectVideo.get("viewCount").getAsLong());
                videoList.add(video);
            }
            searchResponse.setVideoList(videoList);
            videos = searchResponse.getVideoList();

        } catch (Exception e) {
            Log.e("Error:", "Loi lay videoList");

        }

    }

    private void randomResult() {
        Random rand = new Random();
        int value = rand.nextInt(videos.size()) - 1;
        int presentResult = rand.nextInt(maxPresentResult);
        int maxResults = this.maxResults + presentResult * rand.nextInt(10);
        String text = getString(R.string.how_to_pronounce) + " " + query
                + " " +
                "(" + String.valueOf(presentResult) + " " + getString(R.string.out_of) + " " + String.valueOf(maxResults) + ")";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
