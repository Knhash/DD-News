package application.knhash.ddnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shashank on 14-Jul-16.
 */
public class ListViewAdapter extends ArrayAdapter<ListItem> {

    View row;
    private LayoutInflater mInflater;
    private Context context;
    private int resource;
    private ArrayList<ListItem> listData = new ArrayList<>();

    public ListViewAdapter(Context context, int resource, ArrayList<ListItem> listData) {
        super(context, resource, listData);
        this.context = context;
        this.listData = listData;
        this.resource = resource;
    }

    public void setListData(ArrayList<ListItem> listData) {
        this.listData = listData;
        notifyDataSetChanged();
        Log.d("DEBUG", "setListData");
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.newsTitle = (TextView) row.findViewById(R.id.news_title);
            holder.pubDate = (TextView) row.findViewById(R.id.publish_date);
            row.setTag(holder);

        }else {
            holder = (ViewHolder) row.getTag();
        }

        holder.newsTitle.setText(listData.get(position).getTitle());
        holder.pubDate.setText(listData.get(position).getPublishDate());


        final ListItem item = listData.get(position);

        ImageView imageView = (ImageView)(row.findViewById(R.id.imageView));
        Picasso.with(context).load(item.getImage()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("DEBUGP", "Success: "+position);
            }

            @Override
            public void onError() {
                Log.d("DEBUG", "Error: "+position);
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent startDetailsActivity = new Intent(context, DetailActivity.class);
                    startDetailsActivity.putExtra("title", item.getTitle());
                    startDetailsActivity.putExtra("image_link", item.getImage());
                    startDetailsActivity.putExtra("pubDate", item.getPublishDate());
                    startDetailsActivity.putExtra("shortDesc", item.getShortDesc());
                    startDetailsActivity.putExtra("fullDesc", item.getFullDesc());
                    startDetailsActivity.putExtra("id", item.getId());
                    context.startActivity(startDetailsActivity);
            }
        });

        return row;
    }

    static class ViewHolder {
        TextView newsTitle;
        TextView pubDate;
    }
}
