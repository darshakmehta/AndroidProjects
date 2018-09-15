package com.uncc.inclass08;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnRecepieFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeDetailsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private OnRecepieFragmentInteractionListener mListener;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ArrayList<Recepie> recepieArrayList = (ArrayList<Recepie>)getArguments().getSerializable("recepieList");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.myrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipiesAdapter(recepieArrayList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return  view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SearchFragment(), "SearchFragment")
                        .commit();

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                ActionBar actionBar = activity.getSupportActionBar();
                actionBar.setTitle("Recipe Puppy");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecepieFragmentInteractionListener) {
            mListener = (OnRecepieFragmentInteractionListener) context;
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

    public interface OnRecepieFragmentInteractionListener {
        void onRecepieFragmentInteraction(Uri uri);
    }
}
