package com.glib.core.json;

import com.glib.core.collection.SelfTruncatingSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GGson {
  private static Gson GSON = new GsonBuilder().registerTypeAdapter(SelfTruncatingSet.class, new SelfTruncatingSet.GsonSerializer()).create();
  private static Gson DEFAULT_GSON = new Gson();

  public static Gson gson() {
    return GSON;
  }

  public static Gson defaultGson() {
    return DEFAULT_GSON;
  }

}
