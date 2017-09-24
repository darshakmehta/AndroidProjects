package mad.com.dynamic_layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by darsh on 9/24/2017.
 */

public class listitemUI extends LinearLayout {

    public TextView first, last, age;
    public listitemUI(Context context) {
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listitem,this);
        this.first = (TextView) findViewById(R.id.tv1);
        this.last = (TextView) findViewById(R.id.tv2);
        this.age = (TextView) findViewById(R.id.tv3);
    }
}
