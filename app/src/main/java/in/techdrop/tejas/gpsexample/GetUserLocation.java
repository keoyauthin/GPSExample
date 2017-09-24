package in.techdrop.tejas.gpsexample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    if (requestCode == 1) {

      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
          {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

          }

        }

      }

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

        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

          List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

          if (listAddresses != null && listAddresses.size() > 0) {

            Log.i("PlaceInfo", listAddresses.get(0).toString());

            String address = "";

            if (listAddresses.get(0).getSubThoroughfare() != null) {

              address += listAddresses.get(0).getSubThoroughfare() + " ";

            }

            if (listAddresses.get(0).getThoroughfare() != null) {

              address += listAddresses.get(0).getThoroughfare() + ", ";

            }

            if (listAddresses.get(0).getLocality() != null) {

              address += listAddresses.get(0).getLocality() + ", ";

            }

            if (listAddresses.get(0).getPostalCode() != null) {

              address += listAddresses.get(0).getPostalCode() + ", ";

            }

            if (listAddresses.get(0).getCountryName() != null) {

              address += listAddresses.get(0).getCountryName();

            }

            Toast.makeText(GetUserLocation.this, address, Toast.LENGTH_SHORT).show();

          }

        } catch (IOException e) {

          e.printStackTrace();

        }

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

      Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
      LatLng UserLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

      mMap.clear();
      mMap.addMarker(new MarkerOptions().position(UserLocation).title("Your Location"));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(UserLocation));
    }
    else
    {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
      {
        //Ask for Persmission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

      }
      else
      {
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        LatLng UserLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(UserLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UserLocation));
      }
    }

    //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    // Add a marker in Sydney and move the camera
  }
}
