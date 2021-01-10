package com.r.newsapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.r.newsapp.MainActivity;
import com.r.newsapp.R;

import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

public class Settings_frag extends PreferenceFragmentCompat {
    EditTextPreference language, country;
    Preference about;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        language = (EditTextPreference) findPreference("lang");
        language.setSummary(Locale.getDefault().getLanguage().toUpperCase());
        country = findPreference("country");
        country.setSummary(countryCode.toUpperCase());
        about = findPreference("about");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("News App")
                        .setMessage("A Simple News app to keep you updated!")
                        .setCancelable(true);
                AlertDialog alert = alertDialog.create();
                alert.show();
                return true;
            }
        });
    }
}
