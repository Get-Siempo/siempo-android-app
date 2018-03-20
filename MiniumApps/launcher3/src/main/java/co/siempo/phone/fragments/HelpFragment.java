package co.siempo.phone.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.siempo.phone.BuildConfig;
import co.siempo.phone.R;
import co.siempo.phone.activities.CoreActivity;
import co.siempo.phone.event.CheckVersionEvent;
import co.siempo.phone.service.ApiClient_;

/**
 * Created by hardik on 5/1/18.
 */


public class HelpFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtSendFeedback;
    private TextView txtPrivacyPolicy;
    private TextView txtFaq;
    private TextView txtVersionValue;
    private View view;
    private String TAG = "HelpFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help, container, false);
        initView();
        return view;
    }

    private void initView() {
        toolbar = view.findViewById(R.id.toolbar);

        txtSendFeedback = view.findViewById(R.id.txtSendFeedback);
        txtSendFeedback.setOnClickListener(this);

        txtPrivacyPolicy = view.findViewById(R.id.txtPrivacyPolicy);
        txtPrivacyPolicy.setOnClickListener(this);

        txtFaq = view.findViewById(R.id.txtFaq);
        txtFaq.setOnClickListener(this);

        txtVersionValue = view.findViewById(R.id.txtVersionValue);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_blue_24dp);
        toolbar.setTitle(R.string.help);
        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color
                .colorAccent));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        String version = "";
        if (BuildConfig.FLAVOR.equalsIgnoreCase(getActivity().getString(R.string.alpha))) {
            version = "Siempo version : ALPHA-" + BuildConfig.VERSION_NAME;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(getActivity().getString(R.string.beta))) {
            version = "Siempo version : BETA-" + BuildConfig.VERSION_NAME;
        }
        txtVersionValue.setText("" + version);


        txtVersionValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpgradeVersion();
            }
        });

    }

    void txtSendFeedback() {
        ((CoreActivity) getActivity()).loadChildFragment(FeedbackFragment_.builder()
                .build(), R.id.helpView);
    }

    void txtFaq() {
        ((CoreActivity) getActivity()).loadChildFragment(FaqFragment_.builder()
                .build(), R.id.helpView);
    }

    void txtPrivacyPolicy() {
        ((CoreActivity) getActivity()).loadChildFragment(PrivacyPolicyFragment_.builder()
                .build(), R.id.helpView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSendFeedback:
                txtSendFeedback();
                break;
            case R.id.txtFaq:
                txtFaq();
                break;
            case R.id.txtPrivacyPolicy:
                txtPrivacyPolicy();
                break;
            default:
                break;
        }
    }


    public void checkUpgradeVersion() {
        Log.d(TAG, "Active network..");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context
                        .CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (BuildConfig.FLAVOR.equalsIgnoreCase(getString(R.string.alpha))) {
                ApiClient_.getInstance_(getActivity())
                        .checkAppVersion(CheckVersionEvent.ALPHA);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(getString(R.string.beta))) {
                ApiClient_.getInstance_(getActivity())
                        .checkAppVersion(CheckVersionEvent.BETA);
            }
        } else {
            Log.d(TAG, getString(R.string.nointernetconnection));
        }

    }

}
