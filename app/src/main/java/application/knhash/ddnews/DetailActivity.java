package application.knhash.ddnews;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    String title, image, pubDate, shortDesc, fullDesc, id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        context = this;

        getNewsData();
        setNewsData();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getNewsData() {
        Bundle data = getIntent().getExtras();
        title = data.getString("title");
        image = data.getString("image_link");
        pubDate = data.getString("pubDate");
        shortDesc = data.getString("shortDesc");
        fullDesc = data.getString("fullDesc");
        id = data.getString("id");
    }

    private void setNewsData() {

        ImageView imageView = (ImageView) findViewById(R.id.imageViewDetail);
        TextView titleView = (TextView) findViewById(R.id.news_title);
        TextView shortDescView = (TextView) findViewById(R.id.short_description);
        TextView fullDescView = (TextView) findViewById(R.id.full_description);



        Picasso.with(context).load(image).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("DEBUG2", "Image Success : " +  image);
            }

            @Override
            public void onError() {
                Log.d("DEBUG2", "Image Failure : " +  image);
            }
        });
        titleView.setText(title);
        shortDescView.setText(shortDesc);
        fullDescView.setText(fullDesc);
    }

}
