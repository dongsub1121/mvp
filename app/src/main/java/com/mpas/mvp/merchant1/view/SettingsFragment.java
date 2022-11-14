package com.mpas.mvp.merchant1.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.preference.PreferenceFragmentCompat;

import com.mpas.mvp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

}