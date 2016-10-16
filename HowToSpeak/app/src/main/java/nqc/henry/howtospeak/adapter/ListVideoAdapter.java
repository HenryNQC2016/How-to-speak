package nqc.henry.howtospeak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.model.DeveloperKey;
import nqc.henry.howtospeak.model.Video;

/**
 * Created by hinh1 on 10/16/2016.
 */
public class ListVideoAdapter extends ArrayAdapter<Video>{
    private final List<View> entryViews;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private final ThumbnailListener thumbnailListener;

    private boolean labelsVisible;

    private List<Video> entries;
    private LayoutInflater layoutInflater;
    public ListVideoAdapter(Context context, int resource, List<Video> objects) {
        super(context, resource, objects);
        this.entries=objects;
        this.layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.entryViews = new ArrayList<View>();
        this.thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        //inflater = LayoutInflater.from(context);
        this.thumbnailListener = new ThumbnailListener();

        this.labelsVisible = true;
    }
    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            loader.release();
        }
    }
    public void updateList(List<Video> objects){
        this.entries=objects;
        notifyDataSetChanged();
    }
    public void setLabelVisibility(boolean visible) {
        labelsVisible = visible;
        for (View view : entryViews) {
            view.findViewById(R.id.text).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Video getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        Video entry = entries.get(position);

        // There are three cases here
        if (view == null) {
            viewHolder=new ViewHolder();
            // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
            view = layoutInflater.inflate(R.layout.video_list_item, parent, false);
            viewHolder.videoTime=(TextView) view.findViewById(R.id.itemDurationView);
            viewHolder.videoTime.setText("3:50");
            viewHolder.thumbnailView= (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            viewHolder.thumbnailView.setTag(entry.getVideoId());
            viewHolder.thumbnailView.initialize(DeveloperKey.DEVELOPER_KEY, thumbnailListener);
            viewHolder.chanelName=(TextView) view.findViewById(R.id.itemUploaderView);
            viewHolder.chanelName.setText(entry.getChanelName());
            viewHolder.viewCount=(TextView)view.findViewById(R.id.itemViewCountView);
            viewHolder.viewCount.setText(shortViewCount(entry.getView_count()));
            view.setTag(viewHolder);
        } else {

            viewHolder=(ViewHolder)view.getTag();
            /*viewHolder.thumbnailView= (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            viewHolder.videoTime=(TextView) view.findViewById(R.id.itemDurationView);
            viewHolder.videoTime.setText("3:80");*/
            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(viewHolder.thumbnailView);
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                viewHolder.thumbnailView.setTag(entry.getVideoId());
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                viewHolder.thumbnailView.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(entry.getVideoId());
            }
        }
        viewHolder.label= ((TextView) view.findViewById(R.id.itemVideoTitleView));
        viewHolder.label.setText(entry.getVideoName());
        viewHolder.label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.loading_thumbnail);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.no_thumbnail);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.no_thumbnail);
        }
    }

    public String shortViewCount(Long viewCount){
        if(viewCount >= 1000000000){
            return Long.toString(viewCount/1000000000)+"B views";
        }else if(viewCount>=1000000){
            return Long.toString(viewCount/1000000)+"M views";
        }else if(viewCount>=1000){
            return Long.toString(viewCount/1000)+"K views";
        }else {
            return Long.toString(viewCount)+" views";
        }
    }
    public class ViewHolder {
        public YouTubeThumbnailView thumbnailView;
        public TextView videoTime;
        public TextView  viewCount;
        public TextView chanelName;
        public TextView label;
    }

}
