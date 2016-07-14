package application.knhash.ddnews;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    String LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/Headlines/true/android/english/?format=json";

    ArrayList<ListItem> listData;
    ListViewAdapter listViewAdapter;
    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        listData = new ArrayList<>();
        listView = (ListView) findViewById(R.id.main_list);

        new FetchData().execute();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchData extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean success = false;
            StringBuilder data = new StringBuilder("");
            try {
                URL url = new URL(LINK);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    data.append(line);
                }

                if(inputStream != null) {
                    inputStream.close();
                    parseResult(data.toString());
                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                listViewAdapter = new ListViewAdapter(context, R.layout.list_item, listData);
                listViewAdapter.setListData(listData);
                listView.setAdapter(listViewAdapter);
            }
            else {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String data) {
        try {
            JSONArray results = new JSONArray(data);

            for(int i = 0; i<results.length(); ++i) {
                JSONObject object= results.getJSONObject(i);
                ListItem item = new ListItem();

                item.setImage(object.get("image_link").toString());
                item.setTitle(object.get("news_title").toString());
                item.setFullDesc(object.get("full_description").toString());
                item.setShortDesc(object.get("short_description").toString());
                item.setPublishDate(object.get("publish_date").toString());
                item.setId(object.get("id").toString());
                Log.d("DEBUG1", "News image : " +  item.getImage());
                listData.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
