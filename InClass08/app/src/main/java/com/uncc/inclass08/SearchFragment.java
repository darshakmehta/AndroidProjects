package com.uncc.inclass08;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.uncc.inclass08.R.layout.fragment_search;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentTextChange} interface
 * to handle interaction events.
 */
public class SearchFragment extends Fragment {

    private OnFragmentTextChange mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> data = new ArrayList<String>();
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(fragment_search, container, false);
        ImageView addButton = (ImageView) view.findViewById(R.id.addImageMain);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view_first);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new IngredientsAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() <= 4) {
                    if (Integer.parseInt(v.getTag().toString()) == 1) {
                        ViewGroup vg = (ViewGroup) v.getParent();
                        EditText editText = (EditText) vg.getChildAt(0);
                        if ((editText.getText().toString().equals(""))) {
                            Toast.makeText(getActivity(), "Ingredients cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            data.add(editText.getText().toString());
                            editText.setText("");
                            mAdapter = new IngredientsAdapter(data);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                } else {
                    ViewGroup vg = (ViewGroup) v.getParent();
                    EditText editText = (EditText) vg.getChildAt(0);
                    editText.setText("");
                    Toast.makeText(getActivity(), "Can add only 5 Ingredients", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;

    }

    OnFragmentTextChange mListener1;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("demo","AFragment: onAttach");

        try{
            mListener1 = (OnFragmentTextChange) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = data;
                EditText e = (EditText) getActivity().findViewById(R.id.dishName);
                String dishName = e.getText().toString();
                if (!dishName.isEmpty() && (list.size() != 0)) {
                    ProgressFragment ldf = new ProgressFragment ();
                    FragmentManager fm=getFragmentManager();
                    android.app.FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putSerializable("ingredients", list);
                    args.putString("dishname", dishName);
                    ldf.setArguments(args);
                    ft.replace(R.id.container, ldf);
                    ft.addToBackStack(null);
                    ft.commit();
                }else if (dishName.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter dish", Toast.LENGTH_SHORT).show();
                } else if (list.size() == 0) {
                    Toast.makeText(getActivity(), "Please add atleast one Ingredient", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public interface OnFragmentTextChange{
        void onTextChanged(String text, ArrayList<String> list);
    }

}
