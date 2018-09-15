package com.uncc.hw02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by gaurav on 9/14/2017.
 */

public class ListItemUI extends LinearLayout {
    public TextView first, last, phone;
    public ImageView contactImage;

    public ListItemUI(Context context) {
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context) { LayoutInflater inflater =
        (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listitem, this);
        this.contactImage = (ImageView) findViewById(R.id.contactImage);
        this.first = (TextView) findViewById(R.id.tv1);
        this.last = (TextView) findViewById(R.id.tv2);
        this.phone = (TextView) findViewById(R.id.tv3);
    }

}

