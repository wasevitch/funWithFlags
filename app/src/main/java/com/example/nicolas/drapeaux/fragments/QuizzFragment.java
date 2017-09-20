package com.example.nicolas.drapeaux.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicolas.drapeaux.R;

public class QuizzFragment extends Fragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.frag_quizz, vg, false);
        }
}
