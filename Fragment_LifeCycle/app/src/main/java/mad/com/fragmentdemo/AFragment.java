package mad.com.fragmentdemo;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {

    public AFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("demo","AFragment: onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("demo","AFragment: onResume");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo","AFragment: onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("demo","AFragment: onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("demo","AFragment: onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("demo","AFragment: onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("demo","AFragment: onDestroy");
    }
}
