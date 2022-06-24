package com.mpas.mvp.merchant1.view;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.mpas.mvp.R;
import com.mpas.mvp.ui.main.CpmFragment;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

}