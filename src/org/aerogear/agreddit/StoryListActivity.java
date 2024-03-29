package org.aerogear.agreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class StoryListActivity extends FragmentActivity
        implements StoryListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        if (findViewById(R.id.story_detail_container) != null) {
            mTwoPane = true;
            ((StoryListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.story_list))
                    .setActivateOnItemClick(true);
        }
    }


    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(StoryDetailFragment.ARG_ITEM_ID, id);
            StoryDetailFragment fragment = new StoryDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.story_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, StoryDetailActivity.class);
            detailIntent.putExtra(StoryDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
