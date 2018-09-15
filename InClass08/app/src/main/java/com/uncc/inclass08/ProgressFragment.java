package com.uncc.inclass08;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgressFragment.OnProgressFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProgressFragment extends Fragment {
    ProgressBar progressBar;
    public static final int REQ_CODE = 100;
    private OnProgressFragmentInteractionListener mListener;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
        ArrayList<String> ingredients = (ArrayList<String>)getArguments().getSerializable("ingredients");
        String dishName = (String) getArguments().getString("dishname");
        StringBuilder sb = new StringBuilder();
        sb.append("i=");
        for (String ingred : ingredients) {
            sb.append(ingred + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        progressBar.setProgress(40);
        String url = "http://www.recipepuppy.com/api/?" + sb.toString() + "&q=" + dishName;
        Log.d("URL:",url);
        new GetAsyncTask(new GetAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Recepie> s) {
                progressBar.setProgress(80);
                progressBar.setProgress(100);
                mListener.onProgressFragmentInteraction(s);
            }
        }).execute(url, "search");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProgressFragmentInteractionListener) {
            mListener = (OnProgressFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProgressFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProgressFragmentInteraction(ArrayList<Recepie> s);
    }

}
