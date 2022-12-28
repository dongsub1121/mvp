package com.mpas.mvp.management.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager.OnPreferenceTreeClickListener;

import com.mpas.mvp.R;

public class SettingMainFragment extends PreferenceFragmentCompat implements OnPreferenceTreeClickListener {

    SettingActivity settingActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        settingActivity = (SettingActivity) getActivity();
        Log.e("onAttach","start");
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.e("SettingMainFragment","start");
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference preference = findPreference("merchant_management");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                Log.e("gggg","ggggggggg");
                //navController.navigate(R.id.action_settingMainFragment_to_settingMerchantHomeFragment);

                return false;
            }
        });
    }


}