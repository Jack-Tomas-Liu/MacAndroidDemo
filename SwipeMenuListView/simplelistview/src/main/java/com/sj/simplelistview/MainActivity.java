package com.sj.simplelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SlideCustomListView listView;
    private MyAdapter myAdapter;
    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new MyAdapter();
        for (int i = 0; i < 10; i++) {
            data.add("A");
        }
        listView = (SlideCustomListView) findViewById(R.id.listview);
        listView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(MainActivity.this,
                        R.layout.listview_item, null);
                viewHolder.textView = (TextView) convertView
                        .findViewById(R.id.textview);
                viewHolder.deleTextView = (TextView) convertView
                        .findViewById(R.id.delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(data.get(position));
            viewHolder.deleTextView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    data.remove(position);
                    myAdapter.notifyDataSetChanged();
                    listView.getChildAt(position).scrollTo(0, 0);
                }
            });
            return convertView;
        }

        class ViewHolder {
            private TextView textView;
            private TextView deleTextView;
        }
    }
}

