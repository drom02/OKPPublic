package bc.vse.okp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.GeoJSONObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MoreInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more_information);
        Intent inta = getIntent();
        Bundle input = inta.getExtras();
        try {
            displayInfo(input.getInt("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void displayInfo(Integer inte) throws JSONException {
        String phone ;
        Integer psc ;
        String ico ;
        Integer capacity ;
        String address  ;
        String district ;
        String barrier ;
        String webAddress ;
        String name ;

        DataManip manip = new DataManip();
            GeoJSONObject geo = null;
            try {
                geo = manip.loadGeoFromMemory(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FeatureCollection feat = (FeatureCollection) geo;

            JSONObject obj = feat.getFeatures().get((inte)-1).toJSON();
            JSONObject objPro = (JSONObject) obj.get("properties");

            if(!(objPro.isNull("NAZEV"))){
                name = (String) objPro.get("NAZEV");
                TextView nameTextView = findViewById(R.id.nameContent);
                nameTextView.setText(name);
            }else{
                TextView hiddenView = findViewById(R.id.namePlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.nameContent);
                hiddenView2.setVisibility(View.GONE);
            }

            if(!(objPro.isNull("WWW"))){
                webAddress = (String) objPro.get("WWW");
                TextView webTextView = findViewById(R.id.wwwContent);
               webTextView.setText(webAddress);

            }else{
                TextView hiddenView = findViewById(R.id.wwwPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.wwwContent);
                hiddenView2.setVisibility(View.GONE);
            }

            if(!(objPro.isNull("BEZBARIER"))){
                barrier = (String) objPro.get("BEZBARIER");
                TextView barrierTextView = findViewById(R.id.accessibilityContent);
                barrierTextView.setText(barrier);
            }else{
                TextView hiddenView = findViewById(R.id.accessibilityPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.accessibilityContent);
                hiddenView2.setVisibility(View.GONE);
            }

            if(!(objPro.isNull("KATUZE_NAZEV"))){
                district = (String) objPro.get("KATUZE_NAZEV");
                TextView districtTextView = findViewById(R.id.districtContent);
                districtTextView.setText(district);
            }else{
                TextView hiddenView = findViewById(R.id.districtPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.districtContent);
                hiddenView2.setVisibility(View.GONE);
            }
            if(!(objPro.isNull("ADRESA"))){
                address = (String) objPro.get("ADRESA");
                TextView addressTextView = findViewById(R.id.addressContent);
                addressTextView.setText(address);
            }else{
                TextView hiddenView = findViewById(R.id.addressPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.addressContent);
                hiddenView2.setVisibility(View.GONE);
            }
            if(!(objPro.isNull("TELEFON"))){
                 phone = (String) objPro.get("TELEFON");
                TextView phoneTextView = findViewById(R.id.phoneContent);
                phoneTextView.setText(phone);
            }else{
               TextView hiddenView = findViewById(R.id.phonePlate);
               hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.phoneContent);
                hiddenView2.setVisibility(View.GONE);
            }
            if(!objPro.isNull("ICO") ){
                 ico = (String) objPro.get("ICO");
                TextView icoTextView = findViewById(R.id.icoContent);
                icoTextView.setText(ico);
            }else{
            TextView hiddenView = findViewById(R.id.icoPlate);
            hiddenView.setVisibility(View.GONE);
            TextView hiddenView2 = findViewById(R.id.icoContent);
            hiddenView2.setVisibility(View.GONE);
            }
            if(!objPro.isNull("PSC")){
                 psc = (Integer) objPro.get("PSC");
                TextView pscTextView = findViewById(R.id.pscContent);
                pscTextView.setText(psc.toString());
             }else{
                TextView hiddenView = findViewById(R.id.pscPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.pscContent);
                hiddenView2.setVisibility(View.GONE);
            }
            if(!objPro.isNull("KAPACITA_NUM")){
                capacity = (Integer) objPro.get("KAPACITA_NUM");
                TextView capacityTextView = findViewById(R.id.capacityContent);
                capacityTextView.setText(capacity.toString());
            }else{
                TextView hiddenView = findViewById(R.id.capacityPlate);
                hiddenView.setVisibility(View.GONE);
                TextView hiddenView2 = findViewById(R.id.capacityContent);
                hiddenView2.setVisibility(View.GONE);
            }

















    }
}