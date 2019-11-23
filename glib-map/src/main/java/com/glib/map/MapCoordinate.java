package com.glib.map;

import com.glib.core.model.Coordinate;
import com.google.android.gms.maps.model.LatLng;

// A Serializable alternative to LatLng
public class MapCoordinate extends Coordinate {
  public MapCoordinate(double latitude, double longitude) {
    super(latitude, longitude);
  }

  public LatLng toLatLng() {
    return new LatLng(getLatitude(), getLongitude());
  }
}
