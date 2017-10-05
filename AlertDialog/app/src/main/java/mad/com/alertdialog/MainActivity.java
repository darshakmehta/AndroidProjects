package mad.com.alertdialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alert1, alert2, alert3,alert4;
    private String []colors;
    private int selected;
    private boolean []colorList;
    private StringBuilder sb;
    private AlertDialog.Builder builder1,builder2,builder3,builder4;
    private ProgressDialog progress;
    private TextView selectedColors;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorList = new boolean[4];
        selectedColors = (TextView) findViewById(R.id.colorsList);
        button = (Button) findViewById(R.id.selectButton);
        button.setEnabled(false);
        colors = getResources().getStringArray(R.array.colors);

        builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Pick a color")
                .setMessage("Are you sure?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert2.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("demo","Clicked Cancel");
                    }
                });

        alert1 = builder1.create();
        findViewById(R.id.alertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.show();
            }
        });

        builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Pick a color")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;
                        colorList[which] = true;
                        Log.d("demo","Selected Color is " + colors[which]);
                        alert3.show();
                    }
                });
        alert2 = builder2.create();

        builder3 = new AlertDialog.Builder(this);
        builder3.setTitle("Do you want to change?")
                .setCancelable(false)
                .setSingleChoiceItems(colors, selected , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;
                        for(int i =0; i<4; i++)
                            colorList[i] = false;
                        colorList[which] = true;
                        Log.d("demo","Changed color is "+colors[which]);
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert4.show();
                    }
                });
        alert3 = builder3.create();

        builder4 = new AlertDialog.Builder(this);
        builder4.setTitle("Do you want to add more favourite colors?")
                .setMultiChoiceItems(colors,colorList , new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            colorList[which] = true;
                            Log.d("demo",colors[which] + "is true");
                        }else{
                            colorList[which] = false;
                            Log.d("demo",colors[which] + "is false");
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sb = new StringBuilder("Selected Colors:\n\n\n");
                        for (int i = 0; i < 4; i++)
                            if(colorList[i])
                            sb.append(colors[i]+"\n");
                        button.setEnabled(true);
                    }
                });

        alert4 = builder4.create();

        progress = new ProgressDialog(this);
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        findViewById(R.id.selectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progress.dismiss();
                        selectedColors.setText(sb.toString());
                    }
                }, 3000);
            }
        });

    }
}
