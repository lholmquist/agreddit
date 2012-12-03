package org.aerogear.agreddit;

import java.net.MalformedURLException;
import java.net.URL;

import org.aerogear.agreddit.gson.ListingTypeAdapter;
import org.aerogear.agreddit.reddit.Listing;
import org.aerogear.android.Pipeline;
import org.aerogear.android.impl.pipeline.PipeConfig;
import org.aerogear.android.pipeline.Pipe;

import android.app.Application;

import com.google.gson.GsonBuilder;

public class StoryListApplication extends Application {

	Pipeline pipeline ;
	
	@Override
	public void onCreate() {
		super.onCreate();
		URL redditURL;
		try {
			redditURL = new URL("http://www.reddit.com/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		pipeline = new Pipeline(redditURL);
		
		PipeConfig config = new PipeConfig(redditURL, Listing.class);
		config.setGsonBuilder(new GsonBuilder().registerTypeAdapter(Listing.class, new ListingTypeAdapter()));
		config.setEndpoint(".json");
		
		pipeline.pipe(Listing.class, config);
		
	}
	
	public Pipe<Listing> getListing() {
		return pipeline.get(Listing.class.getSimpleName().toLowerCase());
	}
	
}
