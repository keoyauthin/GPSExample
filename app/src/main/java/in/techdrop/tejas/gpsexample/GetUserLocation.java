package in.techdrop.tejas.gpsexample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetUserLocation extends FragmentActivity implements OnMapReadyCallback
{

  LocationManager locationManager;
  LocationListener locationListener;
  private GoogleMap mMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_user_location);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.getuserlocmap);
    mapFragment.getMapAsync(this);

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
      }
      locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
     }

  }

  @Override
  public void onMapReady(GoogleMap googleMap)
  {
    mMap = googleMap;

    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    locationListener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {

        Log.i("Location", location.toString());
        //Toast.makeText(getApplicationContext(),location.toString(),Toast.LENGTH_SHORT).show();

        LatLng UserLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(UserLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserLocation,10));

      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      @Override
      public void onProviderEnabled(String provider) {

      }

      @Override
      public void onProviderDisabled(String provider) {

      }
    };

    if (Build.VERSION.SDK_INT < 23)
    {
      locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    else
    {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //Ask for Persmission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

      }
      else
      {
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        LatLng UserLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(UserLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserLocation,10));
      }
    }

    //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    // Add a marker in Sydney and move the camera
  }
}
