package co.siempo.phone.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import co.siempo.phone.BuildConfig;
import co.siempo.phone.R;
import co.siempo.phone.activities.CoreActivity;
import co.siempo.phone.helper.ActivityHelper;
import co.siempo.phone.utils.PrefSiempo;

@EFragment(R.layout.fragment_tempo_settings)
public class TempoSettingsFragment extends CoreFragment {

    @ViewById
    Toolbar toolbar;
    @ViewById
    RelativeLayout relHome;
    @ViewById
    RelativeLayout relAppMenu;
    @ViewById
    RelativeLayout relNotification;
    @ViewById
    RelativeLayout relAccount;
    @ViewById
    RelativeLayout relAlphaSettings;
    @ViewById
    TextView titleActionBar;

    public TempoSettingsFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void afterViews() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_blue_24dp);
        toolbar.setTitle(R.string.settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        if (BuildConfig.FLAVOR.equalsIgnoreCase(context.getString(R.string.alpha))) {
            relAlphaSettings.setVisibility(View.VISIBLE);
        } else {
            if (PrefSiempo.getInstance(context).read(PrefSiempo
                    .IS_ALPHA_SETTING_ENABLE, false)) {
                relAlphaSettings.setVisibility(View.VISIBLE);
            } else {
                relAlphaSettings.setVisibility(View.GONE);
            }
        }

    }


    @Click
    void relHome() {
        Log.e("Tab","1");
        ((CoreActivity) getActivity()).loadChildFragment(TempoHomeFragment_.builder()
                .build(), R.id.tempoView);
    }

    @Click
    void relAppMenu()
    {
        Log.e("Tab","2");
        ((CoreActivity) getActivity()).loadChildFragment(AppMenuFragment.newInstance(false), R.id.tempoView);
    }

    @Click
    void relNotification()
    {
        Log.e("Tab","3");
        ((CoreActivity) getActivity()).loadChildFragment(TempoNotificationFragment_.builder().build(), R.id.tempoView);
    }

    @Click
    void relAccount()
    {
        Log.e("Tab","4");
        ((CoreActivity) getActivity()).loadChildFragment(AccountSettingFragment_.builder().build(), R.id.tempoView);
    }

    @Click
    void relAlphaSettings() {
        Log.e("Tab","5");
        new ActivityHelper(context).openSiempoAlphaSettingsApp();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
