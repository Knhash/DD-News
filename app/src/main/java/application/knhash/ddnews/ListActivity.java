package application.knhash.ddnews;

import android.os.AsyncTask;
import android.os.Bundle;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ListActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        String LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/Headlines/true/android/english/?format=json";

        ArrayList<ListItem> listData1;
        ArrayList<ListItem> listData2;
        ArrayList<ListItem> listData3;
        ArrayList<ListItem> listData4;
        ArrayList<ListItem> listData5;
        ListViewAdapter listViewAdapter;
        ListView listView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView;


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                rootView = inflater.inflate(R.layout.headlines_list, container, false);
                listData1 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.headlines_listview);
                new FetchData().execute();
            }

            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.national_list, container, false);
                listData2 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.national_listview);
                new FetchData().execute();
            }

            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                rootView = inflater.inflate(R.layout.international_list, container, false);
                listData3 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.international_listview);
                new FetchData().execute();
            }

            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                rootView = inflater.inflate(R.layout.business_list, container, false);
                listData4 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.business_listview);
                new FetchData().execute();
            }

            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5){
                rootView = inflater.inflate(R.layout.sports_list, container, false);
                listData5 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.sports_listview);
                new FetchData().execute();
            }

            else{
                rootView = inflater.inflate(R.layout.sports_list, container, false);
                listData5 = new ArrayList<>();
                listView = (ListView) getActivity().findViewById(R.id.sports_listview);
                new FetchData().execute();
            }


            return rootView;
        }

        public class FetchData extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                Boolean success = false;
                StringBuilder data = new StringBuilder("");
                try {
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                        LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/Headlines/true/android/english/?format=json";
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/National/true/android/english/?format=json";

                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/International/true/android/english/?format=json";

                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/Business/true/android/english/?format=json";

                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5){
                        LINK = "http://doordarshan.pointart.mobi/dd_links/links/get_news_feed/Sports/true/android/english/?format=json";

                    }
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
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                        listView = (ListView) getActivity().findViewById(R.id.headlines_listview);
                        listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item, listData1);
                        listViewAdapter.setListData(listData1);
                        listView.setAdapter(listViewAdapter);
                        Log.d("WHERE", "Section 1");
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        listView = (ListView) getActivity().findViewById(R.id.national_listview);
                        listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item, listData2);
                        listViewAdapter.setListData(listData2);
                        listView.setAdapter(listViewAdapter);
                        Log.d("WHERE", "Section 2");
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        listView = (ListView) getActivity().findViewById(R.id.international_listview);
                        listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item, listData3);
                        listViewAdapter.setListData(listData3);
                        listView.setAdapter(listViewAdapter);
                        Log.d("WHERE", "Section 3");
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        listView = (ListView) getActivity().findViewById(R.id.business_listview);
                        listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item, listData4);
                        listViewAdapter.setListData(listData4);
                        listView.setAdapter(listViewAdapter);
                        Log.d("WHERE", "Section 4");
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5){
                        listView = (ListView) getActivity().findViewById(R.id.sports_listview);
                        listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item, listData5);
                        listViewAdapter.setListData(listData5);
                        listView.setAdapter(listViewAdapter);
                        Log.d("WHERE", "Section 5");
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
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
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                        listData1.add(item);
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        listData2.add(item);
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        listData3.add(item);
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        listData4.add(item);
                    }
                    else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5){
                        listData5.add(item);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Headlines";
                case 1:
                    return "National";
                case 2:
                    return "International";
                case 3:
                    return "Business";
                case 4:
                    return "Sports";
            }
            return null;
        }
    }
}