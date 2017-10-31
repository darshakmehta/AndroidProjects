package mad.com.multifragmentdemo;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    public FirstFragment fragment;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnRecyclerFragmentInteractionListener mListener2;
    private ArrayList<String> data = new ArrayList<String>();
    private int count = 1;
    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_first, container, false);
        ImageView addButton = (ImageView) view.findViewById(R.id.addImage);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv;
                EditText e;

                if (count <= 5) {
                    if (Integer.parseInt(v.getTag().toString()) == 1) {
                        ViewGroup vg = (ViewGroup) v.getParent();
                        EditText editText = (EditText) vg.getChildAt(0);
                        if ((editText.getText().toString().equals(""))) {
                            Toast.makeText(getActivity(), "Keyword cannot be empty", Toast.LENGTH_LONG).show();
                        } else {
                            data.add(editText.getText().toString());
                            for(String s: data)
                            Log.d("demo",s);

                            if (count < 5) {
                                mAdapter = new IngredientsAdapter(data,getActivity());
                                mRecyclerView.setAdapter(mAdapter);
                                iv = (ImageView) v;
                                iv.setTag(0);
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                                count++;
                            } else {
                                mAdapter = new IngredientsAdapter(data,getActivity());
                                mRecyclerView.setAdapter(mAdapter);
                                iv = (ImageView) v;
                                iv.setTag(0);
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                            }
                        }
                    } else {
                        if (count == 1) {
                            data.removeAll(data);
                            mAdapter = new IngredientsAdapter(data,getActivity());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        ViewGroup vg = (ViewGroup) v.getParent();
                        EditText editText = (EditText) vg.getChildAt(0);
                        for(String s: data)
                            Log.d("demo",s+"9090");
                        data.remove(editText.getText().toString());
                        for(String s: data)
                            Log.d("demo",s+"0909");
                        mAdapter = new IngredientsAdapter(data,getActivity());
                        mRecyclerView.setAdapter(mAdapter);
                        //parent_layout.removeView(vg);
                        if (count != 1)
                            count--;
                    }
                } else {
                    Toast.makeText(getActivity(), "Can add only 5 Ingredients", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        data.add("asd");
        data.add("sad");
        mAdapter = new IngredientsAdapter(data,getActivity());
        mRecyclerView.setAdapter(mAdapter);
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

    public interface OnFragmentInteractionListener {
        public void gotoNextFragment();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) getActivity().findViewById(R.id.dishName);
                String s = e.getText().toString();
                ArrayList<String> list = new ArrayList<String>();
                /*fragment = new FirstFragment();
                Bundle args = new Bundle();
                args.putString("key",s);
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();*/
                if (!s.isEmpty() && (list.size() != 0)) {

                } else if (list.size() == 0) {
                    Toast.makeText(getActivity(), "Please add atleast one Ingredient", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Please add a Dish", Toast.LENGTH_LONG).show();
                }
                mListener1.onTextChanged(s, list);
            }
        });
    }

    public interface OnFragmentTextChange{
        void onTextChanged(String text, ArrayList<String> list);
    }

    public interface OnRecyclerFragmentInteractionListener {
        void onRecyclerFragmentInteraction();
    }



}
