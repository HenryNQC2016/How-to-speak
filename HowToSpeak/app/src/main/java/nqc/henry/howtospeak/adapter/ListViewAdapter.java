package nqc.henry.howtospeak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.model.ListViewItem;


/**
 * Created by Henry on 26-Jul-16.
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {
    private ArrayList<ListViewItem> arrCustomListview;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, int resource, ArrayList<ListViewItem> objects) {
        super(context, resource, objects);
        this.arrCustomListview = objects;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_list_view, parent, false);
            viewHolder.imvIcon = (ImageView) convertView.findViewById(R.id.imvIcon);
            viewHolder.txtText = (TextView) convertView.findViewById(R.id.txtText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ListViewItem listviewItem = arrCustomListview.get(position);
        if (listviewItem != null) {
            viewHolder.txtText.setText(listviewItem.getText());
            //set icon
            viewHolder.imvIcon.setImageResource(listviewItem.getIconRes());
        }

        return convertView;
    }


    public class ViewHolder {
        public ImageView imvIcon;
        public TextView txtText;
    }
}
