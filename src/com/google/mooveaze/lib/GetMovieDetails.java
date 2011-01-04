package com.google.mooveaze.lib;

import android.os.AsyncTask;
import com.google.mooveaze.model.Movie;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class GetMovieDetails extends AsyncTask<Movie, Void, Void> {
    private static final String DETAILS_URL = "http://www.redbox.com/api/Product/GetDetail/";

    @Override
    protected Void doInBackground(Movie... movies) {
        Log.debug("retrieving movie detail from " + DETAILS_URL);

        try {
            JSONObject json = new JSONObject();
            json.put("productType", "1");
            json.put("id", movies[0].getId());
            json.put("descCharLimit", "2000");

            RestClient.Header[] headers = {
                    new RestClient.Header("Cookie", "RB_2.0=1"),
                    new RestClient.Header("__K", RedboxKey.get20key()),
                    new RestClient.Header("X-Requested-With", "XMLHttpRequest")
            };

            RestClient.Response response = new RestClient().post(DETAILS_URL, json.toString(), headers);
            Log.debug("" + response.statusCode);
            Log.debug(response.entity);
        }
        catch(Exception e) {
            Log.error(e);
        }

        return null;
    }
}
