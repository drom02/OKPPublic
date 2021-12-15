package bc.vse.okp;

import android.app.Activity;
import android.content.Context;


import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


public class DataManip {

    public GeoJSONObject geoDatManip(Activity act) throws JSONException {
        String json = null;
        try {
            InputStream is = act.getAssets().open("GeoData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        GeoJSONObject geoJSON = GeoJSON.parse(json);

        return geoJSON;


    }
    public GeoJSONObject loadGeoFromMemory (Context context) throws JSONException {
        String json = null;
        try {
            InputStream is = context.openFileInput("GeoData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        GeoJSONObject geoJSON = GeoJSON.parse(json);

        return geoJSON;


    }
    public void saveGeoToMemory (Context context, JSONObject geo) throws JSONException {
        try {
            String userString = geo.toString();
            File file = new File(context.getFilesDir(),"GeoData.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
