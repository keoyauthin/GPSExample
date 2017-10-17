package in.techdrop.tejas.gpsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  Button btnMaps , btnGetUserLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnMaps = (Button) findViewById(R.id.btnGmap);
    btnGetUserLocation = (Button) findViewById(R.id.btnGetUserLocation);
  }

  void btnMaps(View v)
  {
    Intent i = new Intent(getApplicationContext(),MapsActivity.class);
    startActivity(i);
  }

  void getUserLocation(View v)
  {
    Intent i = new Intent(getApplicationContext(),GetUserLocation.class);
    startActivity(i);
  }

  void getNearByLocation(View v)
  {
    Intent i = new Intent(getApplicationContext(),NearByLocation.class);
    startActivity(i);
  }
}
