package com.uncc.hw07;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int tabscount;

        public PagerAdapter(FragmentManager fm, int tabscount) {
            super(fm);
            this.tabscount = tabscount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    FriendsFragment friendsfragment = new FriendsFragment();
                    return friendsfragment;
                case 1:
                    AddFriendFragment addnewfriend = new AddFriendFragment();
                    return addnewfriend;
                case 2:
                    RequestPendingFragment requestPending = new RequestPendingFragment();
                    return requestPending;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabscount;
        }
    }