package nqc.henry.howtospeak.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.adapter.ListVideoAdapter;
import nqc.henry.howtospeak.model.SearchResponse;
import nqc.henry.howtospeak.model.Video;

/**
 * Created by Henry on 15-Oct-16.
 */
public class VideosFragment extends Fragment {
    public VideosFragment() {
    }


    private ListView listView;
    private ArrayList<Video> videos;

    private ListVideoAdapter listVideoAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_video);
        videos = new ArrayList<Video>();
        String JsonStr = "{\"videoList\":[{\"videoId\":\"x0DqhUz-Qr8\", \"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"},{\"videoId\":\"x0DqhUz-Qr8\",\"videoName\":\"[Trên tay] Card đồ họa Asus XXXX\",\"chanelName\":\"Tinh Tế\",\"time\":\"5:10\",\"viewCount\":\"100\"}]}";

        listVideoAdapter = new ListVideoAdapter(getContext(), R.layout.video_list_item, videos);

        listView.setAdapter(listVideoAdapter);
        loadJson(JsonStr);
        listVideoAdapter.updateList(videos);
        Log.d("Size", String.valueOf(videos.size()));


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
            videos.removeAll(videos);
            videos = searchResponse.getVideoList();

        } catch (Exception e) {
            Log.e("Error:", "Loi lay videoList");

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listVideoAdapter.releaseLoaders();
    }
    public void setLabelVisibility(boolean visible) {
        listVideoAdapter.setLabelVisibility(visible);
    }
}
