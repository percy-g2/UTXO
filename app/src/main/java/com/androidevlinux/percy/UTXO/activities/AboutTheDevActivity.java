package com.androidevlinux.percy.UTXO.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.androidevlinux.percy.UTXO.BuildConfig;
import com.androidevlinux.percy.UTXO.R;
import com.jaredrummler.android.device.DeviceName;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

/**
 * Created by Ryan on 3/22/2018.
 */

public class AboutTheDevActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceName.DeviceInfo deviceInfo = DeviceName.getDeviceInfo(AboutTheDevActivity.this);
        AboutView view = AboutBuilder.with(this)
                .setPhoto(R.mipmap.ic_photo)
                .setCover(R.mipmap.background_material_design)
                .setName("Prashant Gahlot")
                .setSubTitle("Android Developer")
                .addLinkedInLink("prashant-gahlot-37914988")
                .setBrief("I love everything related to open source software and block chain ;)")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGitHubLink("percy-g2")
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .setAppIcon(R.mipmap.ic_launcher)
                .addEmailLink("contact.prashantgahlot@gmail.com")
                .addFacebookLink("percy215")
                .addUpdateAction()
                .addFeedbackAction("contact.prashantgahlot@gmail.com")
                .addHelpAction(view1 -> {
                    Intent intent = new Intent(Intent.ACTION_SEND ,Uri.parse("mailto:"));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact.prashantgahlot@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "UTXO SUPPORT");
                    intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n\n------------------------------\nApp Version: " + BuildConfig.VERSION_NAME +
                            " (" + BuildConfig.VERSION_CODE + ")\nDevice Market Name: "
                            + deviceInfo.marketName + "\nModel: " + deviceInfo.model +
                            "\nManufacturer: " + deviceInfo.manufacturer);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .build();
        setContentView(view);
    }
}
