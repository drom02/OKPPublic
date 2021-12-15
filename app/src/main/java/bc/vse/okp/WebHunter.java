package bc.vse.okp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;

@SuppressWarnings("deprecation")
public class WebHunter extends AsyncTask<String, Void, JSONObject>{
    String link;

    @Override
    protected JSONObject doInBackground(String... arg) {
        JSONObject obj = new JSONObject();
        String oldDate = arg[0];
        Document opendataPraha = null;
        try {
            opendataPraha = Jsoup.connect("https://opendata.praha.eu/dataset/ipr-kulturni_zarizeni__body").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(opendataPraha != null){
            Elements lastUpdateDate = opendataPraha.select("#content > div.row.wrapper > div > article > div > section.additional-info > table > tbody > tr:nth-child(3) > td > span");
            link = lastUpdateDate.text();
            System.out.println(link);
            if(!(oldDate.equals(link))){
                try {
                    Elements linkElement = opendataPraha.select("#dataset-resources > ul > li:nth-child(3) > div > ul > li:nth-child(2) > a");
                    String linkData = linkElement.attr("href");
                    Document jsonLink = Jsoup.connect(linkData).ignoreContentType(true).get();
                    Elements jEl = jsonLink.select("body");
                    String json = jEl.text();
                    obj = new JSONObject(json);

                    try {
                        Log.d("OKP WebHunter", obj.toString());

                    } catch (Throwable t) {
                        Log.e("OKP WebHunter", "Malformed json: \"" + json + "\"");
                    }


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
        }else{
                link = oldDate;
            }

        }
        return obj;

    }
    public String out(){
        return link;
    }

    @Override
    protected void onPostExecute(JSONObject res) {


    }



}
