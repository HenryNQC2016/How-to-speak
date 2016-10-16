
package nqc.henry.howtospeak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResponse {

    @SerializedName("videoList")
    @Expose
    private ArrayList<Video> videoList = new ArrayList<Video>();

    /**
     * 
     * @return
     *     The videoList
     */
    public ArrayList<Video> getVideoList() {
        return videoList;
    }

    /**
     * 
     * @param videoList
     *     The videoList
     */
    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
    }

}
