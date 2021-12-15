package bc.vse.okp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.cocoahero.android.geojson.FeatureCollection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMyLocationButtonClickListener

         {

    private GoogleMap mMap;

             private ActivityResultLauncher<String> requestPermissionLauncher =
                     registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                         if (isGranted) {
                             this.permission();
                         } else {

                         }
                     });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.0755,14.4378), 10));

                try {
                    this.loadFilteredMarkers();
                } catch (JSONException e) {
                }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
           // mMap.setMyLocationEnabled(true);
           // mMap.setOnMyLocationButtonClickListener(this);
           // mMap.setOnMyLocationClickListener(this);
            this.permission();
        } else if (1>2) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);

        }


    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, MoreInformationActivity.class);
        Bundle bund = new Bundle();
        bund.putInt("ID", (Integer) marker.getTag());
        intent.putExtras(bund);
       // FeatureCollection cultureCollection = this.dataTest();
       // cultureCollection.getFeatures().get((Integer) marker.getTag());
        startActivity(intent);

    }


    public void permission(){
        Integer finePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(!(finePermission == -1)){
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);


        }

    }
            /**
             @Override
             public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

                 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
             }


        public FeatureCollection dataTest ()  {
            DataManip manip = new DataManip();
            GeoJSONObject geo = null;
            try {
                geo = manip.GeoDatManip(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FeatureCollection feat = (FeatureCollection) geo;
            return feat;
         }
             */
         public void loadFilteredMarkers() throws JSONException{
        DataManip dat = new DataManip();
             ArrayList<Integer> hermes = new ArrayList<>();

             SharedPreferences sharedPref = this.getSharedPreferences("FilterPref",Context.MODE_PRIVATE);

             Integer[] identi = {1,2,4,5,6,8,9,10,11,12,13};
             Integer ide = 1;
             for(Integer inde : identi){
                 if(sharedPref.getBoolean("check"+ide.toString(),false) == true){
                     hermes.add(inde);
                 }
                 ide++;

             }



             Integer i = 0;
             FeatureCollection features = (FeatureCollection) dat.loadGeoFromMemory(this);
             while(i < features.getFeatures().size()) {
                 JSONObject jsonTemp = (JSONObject) features.getFeatures().get(i).toJSON();
                 JSONObject jsonGeometry = (JSONObject) jsonTemp.get("geometry");
                 JSONArray jsonTempArray = (JSONArray) jsonGeometry.get("coordinates");
                 System.out.println(jsonTempArray);
                 JSONObject jsonProperties = (JSONObject) jsonTemp.get("properties");
                 Integer id = (Integer) jsonProperties.get("TYP");
                 if(hermes.contains(id)){
                     String nazevObjektu = (String) jsonProperties.get("NAZEV");
                    Double v = (Double) jsonTempArray.get(1);
                     Double v1 = (Double) jsonTempArray.get(0);
                     LatLng tempo = new LatLng(v, v1);
                     Double color;
                     switch(id) {
                         case 1:
                             color = 210.0;
                             break;
                         case 2:
                             color = 240.0;
                             break;
                         case 4:
                             color = 180.0;
                             break;
                         case 5:
                             color = 120.0;
                             break;
                         case 6:
                             color = 300.0;
                             break;
                         case 8:
                             color = 30.0;
                             break;
                         case 9:
                             color = 0.0;
                             break;
                         case 10:
                             color = 330.0;
                             break;
                         case 11:
                             color = 270.0;
                             break;
                         case 12:
                             color = 60.0;
                             break;
                         case 13:
                             color = 20.0;
                             break;
                         default:
                             color = 0.0;
                             break;
                     }
                     Marker mark = mMap.addMarker(new MarkerOptions()
                                                                    .position(tempo).title(nazevObjektu)
                     .icon(BitmapDescriptorFactory.defaultMarker(color.floatValue())));
                     mark.setTag(jsonProperties.get("OBJECTID"));

                 }

                 i++;
             }

         }



}
