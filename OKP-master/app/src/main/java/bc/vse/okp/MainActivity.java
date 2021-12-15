package bc.vse.okp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.cocoahero.android.geojson.GeoJSONObject;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.io.IOException;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            this.prepareJson();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void goToMap(View view) throws JSONException {
       Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void goToFilters(View view) throws JSONException {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);

    }

    public void prepareJson() throws JSONException, IOException, ExecutionException, InterruptedException {
        WebHunter spidey = new WebHunter();
        DataManip dat = new DataManip();
        File fil = new File(this.getFilesDir()+"/GeoData.json");
        SharedPreferences shaPref = this.getSharedPreferences("lastJsonUpdate", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = shaPref.edit();

       @SuppressWarnings("deprecation") JSONObject jso = spidey.execute(shaPref.getString("lastDate","lol")).get();
       String testString = spidey.out();
       prefEditor.putString("lastDate", testString);
       prefEditor.commit();
      // GeoJSONObject test = dat.GeoDatManip(this);
        if(jso.length()==0){
            if(fil.exists()){
                System.out.println("Doesn|t exist");
            }else{
                GeoJSONObject jsoBack = dat.geoDatManip(this);
                dat.saveGeoToMemory(this,jsoBack.toJSON());
            }

        }else{
            dat.saveGeoToMemory(this,jso);

        }


    }
}
