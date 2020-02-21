package com.example.jsonimagefetching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

class CustomAdapter extends BaseAdapter {
    Context applicationContext;
    int simple;
    List<ModelClass> s;
    LayoutInflater layoutInflater;

    public CustomAdapter(Context applicationContext, int simple, List<ModelClass> s) {
        this.applicationContext = applicationContext;
        this.simple = simple;
        this.s = s;
    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int i) {
        return s.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.simple, viewGroup, false);
        }
        ImageView img;
        TextView textView;
        img = view.findViewById(R.id.imgView);
        textView = view.findViewById(R.id.textView);
        textView.setText(s.get(i).getName());
//        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
//    Picasso.with(applicationContext).load(s.get(i).getImg()).into(img);///here i use Picasso
        // library by add a dependency like = "implementation 'com.squareup.picasso:picasso:2.5.2'"
        ImageLoader.getInstance().displayImage(s.get(i).getImg(), img); // Default options will be used or use oporer pisasso library
        return view;
    }
}
