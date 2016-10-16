package nqc.henry.howtospeak.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.adapter.ListViewAdapter;
import nqc.henry.howtospeak.model.ListViewItem;

/**
 * Created by Henry on 15-Oct-16.
 */
public class TopicsFragment extends Fragment {
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private ArrayList<ListViewItem> arrListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.lvMenu);
        arrListView = new ArrayList<>();
        listViewAdapter = new ListViewAdapter(getContext(), R.layout.item_list_view, arrListView);
        listView.setAdapter(listViewAdapter);
        setListView();
    }

    public void setListView() {
        arrListView.add(new ListViewItem(getString(R.string.speaking), R.drawable.ic_speaking));
        arrListView.add(new ListViewItem(getString(R.string.listening), R.drawable.ic_listening));
        arrListView.add(new ListViewItem(getString(R.string.pronunciation), R.drawable.ic_pronunciation));
        arrListView.add(new ListViewItem(getString(R.string.blog), R.drawable.ic_blog));
        arrListView.add(new ListViewItem(getString(R.string.dictionary), R.drawable.ic_dictionary));
        listViewAdapter.notifyDataSetChanged();
    }
}
