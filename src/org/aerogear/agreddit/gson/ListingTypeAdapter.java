package org.aerogear.agreddit.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.aerogear.agreddit.reddit.Listing;
import org.aerogear.agreddit.reddit.ListingData;
import org.aerogear.agreddit.reddit.T3;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ListingTypeAdapter implements InstanceCreator<Listing>,
		JsonDeserializer<Listing> {

	public Listing deserialize(JsonElement element, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		Listing listing = new Listing();
		if (element.getAsJsonObject().get("after") != null)
			listing.setAfter(element.getAsJsonObject().get("after")
					.getAsString());
		if (element.getAsJsonObject().get("before") != null)
			listing.setBefore(element.getAsJsonObject().get("before")
					.getAsString());
		listing.setKind(element.getAsJsonObject().get("kind").getAsString());
		listing.setData(getListingData(element.getAsJsonObject().get("data")
				.getAsJsonObject()));

		return listing;
	}

	private ListingData getListingData(JsonObject element) {
		ListingData data = new ListingData();

		data.setModhash(element.get("modhash").getAsString());
		data.setChildren(getChildren(element.get("children").getAsJsonArray()));

		return data;
	}

	private List<T3> getChildren(JsonArray element) {
		List<T3> children = new ArrayList<T3>(element.size());
		for (int i = 0; i < element.size(); i++) {
			children.add(getChild(element.get(i).getAsJsonObject()));
		}

		return children;
	}

	private T3 getChild(JsonObject element) {
		T3 child = new T3();
		element = element.get("data").getAsJsonObject();
		if (element == null) {
			return child;
		}
		if (element.get("title") != null)
			child.setTitle(element.get("title").getAsString());
		if (element.get("url") != null)
			child.setUrl(element.get("url").getAsString());
		if (element.get("score") != null)
			child.setScore(element.get("score").getAsLong());
		return child;
	}

	public Listing createInstance(Type arg0) {
		return new Listing();
	}

}
