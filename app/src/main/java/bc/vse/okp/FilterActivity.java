package bc.vse.okp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class FilterActivity extends AppCompatActivity {

    private Integer[] idList = {R.id.checkBox1,R.id.checkBox2, R.id.checkBox4,R.id.checkBox5,R.id.checkBox6,R.id.checkBox8, R.id.checkBox9,R.id.checkBox10,R.id.checkBox11,R.id.checkBox12,R.id.checkBox13 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
         SharedPreferences shaPref = this.getSharedPreferences("FilterPref",Context.MODE_PRIVATE);
        Integer ide = 1;
        for(Integer inde : idList){

            if(shaPref.getBoolean("check"+ide.toString(),false) == true){
                CheckBox  check = findViewById(inde);
                check.setChecked(true);
            }
            ide++;

        }



    }

    public void saveFilters(){
        SharedPreferences shaPref = this.getSharedPreferences("FilterPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = shaPref.edit();

        Integer i = 1;
        for(Integer id : idList){
            CheckBox  check = findViewById(id);
            if(check.isChecked()){
                prefEditor.putBoolean("check"+i.toString(),true );
            }else{
                prefEditor.putBoolean("check"+i.toString(),false);
            }
            i++;
        }


        prefEditor.apply();


    }
    public void saveFiltersButton(View view){
        this.saveFilters();
        Intent intent = new Intent(FilterActivity.this, MainActivity.class);
        startActivity(intent);
    }




    public void goMapButton(View view) {
        this.saveFilters();
        Intent intent = new Intent(FilterActivity.this,MapsActivity.class);
        startActivity(intent);


    }
}