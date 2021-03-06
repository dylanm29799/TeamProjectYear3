package com.example.teamprojectyear3;
//Code from https://www.simplifiedcoding.net/json-parsing-in-android/
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PHPActivity2 extends AppCompatActivity {
    ListView listView;
    String GradInfo;
    String text2;
    String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityphp);
         GradInfo = getIntent().getStringExtra("EXTRA_SESSION_ID");
        Log.i("MyJAG", "This" + GradInfo);
        listView = (ListView) findViewById(R.id.listview);

        getJSON("http://phpmyadminnci.gearhostpreview.com/jsongrad.php");



       // TextView mEdit = (TextView) findViewById(R.id.mEdit);
        //input = mEdit.getText().toString();
        //Log.i("MyJdadaAG", "This" + input);



    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    public void loadIntoListView(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);
       // GraduationView g = new GraduationView(GradInfo);
       //GradInfo =  g.getGradInfo();
      //  Toast.makeText(yourclass, "Choose Radio Button Please", Toast.LENGTH_SHORT).show();

       // input = mEdit.getText().toString();

        ArrayList<Graduation> gradList = new ArrayList<>();

        for(int d = 0; d< jsonArray.length(); d++){
            JSONObject obj = jsonArray.getJSONObject(d);
            Log.i("MyJ44", "This" + GradInfo);
            String FPCHS = obj.getString("FPCHS");
            String maleGrad = obj.getString("Male " + GradInfo);
            String femaleGrad = obj.getString("Female " + GradInfo);

            Graduation Name = new Graduation (FPCHS, maleGrad, femaleGrad);

            gradList.add(Name);




        }


        ListView listView = findViewById(R.id.listview);
        GraduationListAdapter adapter = new GraduationListAdapter(this, R.layout.adapter_view_layout, gradList);
        listView.setAdapter(adapter);



    }

}
