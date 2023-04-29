package com.example.javaht.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaht.InfoCharacter;
import com.example.javaht.ItemAdapter;
import com.example.javaht.R;

public class ListItemsFragment extends Fragment {

    public ListItemsFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_items, container, false);
        recyclerView = view.findViewById(R.id.rcklistOfItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ItemAdapter(view.getContext(), InfoCharacter.getInstance().getCharacter().getItemStorage()));

        return view;
    }
}
