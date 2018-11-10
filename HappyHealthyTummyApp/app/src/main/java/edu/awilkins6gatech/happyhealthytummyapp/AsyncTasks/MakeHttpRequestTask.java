package edu.awilkins6gatech.happyhealthytummyapp.AsyncTasks;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MakeHttpRequestTask extends AsyncTask<String, Integer, JSONObject[]> {
    ArrayList<JSONObject> listOfResponses = new ArrayList<>();

    @Override
    protected JSONObject[] doInBackground(String ...requests){
        for (String request : requests) {
            listOfResponses.add(makeHttpRequest(request));
        }
        JSONObject[] responses = new JSONObject[requests.length];
        listOfResponses.toArray(responses);
        return responses;
    }

    private static JSONObject makeHttpRequest(String request){
        JSONObject response = null;
        try {
            URL url = new URL(request);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            String content = "";
            while ((inputLine = in.readLine()) != null) {
                content = content + inputLine;
            }
            in.close();
            con.disconnect();
            response = new JSONObject(content);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
