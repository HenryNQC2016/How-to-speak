
package nqc.henry.howtospeak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoName")
    @Expose
    private String videoName;
    @SerializedName("chanelName")
    @Expose
    private String chanelName;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("viewCount")
    @Expose
    private long view_count = -1;

    public long getView_count() {
        return view_count;
    }

    public void setView_count(long view_count) {
        this.view_count = view_count;
    }

    /**
     * 
     * @return
     *     The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * 
     * @param videoId
     *     The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * 
     * @return
     *     The videoName
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * 
     * @param videoName
     *     The videoName
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * 
     * @return
     *     The chanelName
     */
    public String getChanelName() {
        return chanelName;
    }

    /**
     * 
     * @param chanelName
     *     The chanelName
     */
    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}
