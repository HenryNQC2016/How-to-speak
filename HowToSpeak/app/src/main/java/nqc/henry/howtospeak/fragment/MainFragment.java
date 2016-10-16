package nqc.henry.howtospeak.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nqc.henry.howtospeak.R;
import nqc.henry.howtospeak.adapter.ViewPagerAdapter;

/**
 * Created by Henry on 15-Oct-16.
 */
public class MainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeFragment homeFragment;
    private TopicsFragment topicsFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        homeFragment = new HomeFragment();
        topicsFragment = new TopicsFragment();

        adapter.addFragment(homeFragment, getString(R.string.VIDEOS));
        adapter.addFragment(topicsFragment, getString(R.string.TOPICS));
        viewPager.setAdapter(adapter);
    }
}
