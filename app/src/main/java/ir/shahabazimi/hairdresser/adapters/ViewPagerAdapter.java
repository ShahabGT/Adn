package ir.shahabazimi.hairdresser.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;
    private FragmentManager fm;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fm = fragmentManager;
        fragments = new ArrayList<>();
    }
    //    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//        fragments = new ArrayList<>();
//        this.fm = fm;
//    }

    public void removeAll(){
        fm.getFragments().clear();
        fragments.clear();
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return fragments.get(position);
//    }

//    @Override
//    public int getCount() {
//        return fragments.size();
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
