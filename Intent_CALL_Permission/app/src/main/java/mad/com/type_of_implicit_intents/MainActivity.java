package mad.com.type_of_implicit_intents;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uncc.edu"));
        //startActivity(intent);

        intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:7042362921"));
        if(isPermissionGranted()){
            callAction();
        }

    }

    public boolean isPermissionGranted() {
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                Log.d("demo","Permission is granted");
                return true;
            } else{
                Log.d("demo","Permission is denied");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
                return false;
            }
        } else{ // permission is granted on SDK < 23
            Log.d("demo","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permission is granted",Toast.LENGTH_LONG).show();
                    callAction();
                }else {
                    Toast.makeText(getApplicationContext(), "Permission is denied",Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void callAction() {
        startActivity(intent1);
    }
}
