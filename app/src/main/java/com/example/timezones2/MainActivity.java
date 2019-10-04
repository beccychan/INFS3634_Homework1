package com.example.timezones2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> idAdapter;
    SimpleDateFormat sdf;
    Date resultDateS, resultDateNY, resultDateP, resultDateL, resultDateB;
    ImageView newYork;
    ConstraintLayout Sydney, NY, London, Beijing, Paris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sydney = findViewById(R.id.Sydney);
        NY = findViewById(R.id.NewYork);
        London = findViewById(R.id.London);
        Beijing = findViewById(R.id.Beijing);
        Paris = findViewById(R.id.Paris);

        TextView SydneyText = Sydney.findViewById(R.id.city_name);
        SydneyText.setText(R.string.syd);

        TextView NYText = NY.findViewById(R.id.city_name);
        NYText.setText(R.string.ny);

        TextView LText = London.findViewById(R.id.city_name);
        LText.setText(R.string.london);

        TextView BeijingText = Beijing.findViewById(R.id.city_name);
        BeijingText.setText(R.string.beijing);

        TextView ParisText = Paris.findViewById(R.id.city_name);
        ParisText.setText(R.string.paris);

        ImageView SydneyImage = Sydney.findViewById(R.id.city_pic);
        SydneyImage.setImageResource(R.drawable.sydney);

        ImageView NYImage = NY.findViewById(R.id.city_pic);
        NYImage.setImageResource(R.drawable.newyork);

        ImageView LImage = London.findViewById(R.id.city_pic);
        LImage.setImageResource(R.drawable.london);

        ImageView BeijingImage = Beijing.findViewById(R.id.city_pic);
        BeijingImage.setImageResource(R.drawable.beijing);

        ImageView ParisImage = Paris.findViewById(R.id.city_pic);
        ParisImage.setImageResource(R.drawable.paris);


        // String[] idArray = TimeZone.getAvailableIDs();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timezones();
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    private void timezones() {
        long milliseconds;
        sdf = new SimpleDateFormat("HH:mm:ss");

        TextView timezoneSydney = Sydney.findViewById(R.id.timezone);
        TextView timezoneNewYork = NY.findViewById(R.id.timezone);
        TextView timezoneParis = Paris.findViewById(R.id.timezone);
        TextView timezoneLondon = London.findViewById(R.id.timezone);
        TextView timezoneBeijing = Beijing.findViewById(R.id.timezone);

        milliseconds = getGMTtime();

        TimeZone timezoneS = TimeZone.getTimeZone("Australia/Sydney");
        resultDateS = getTimeZoneTime(timezoneS, milliseconds);
        timezoneSydney.setText("" + sdf.format(resultDateS));

        TimeZone timezoneNY = TimeZone.getTimeZone("America/New_York");
        resultDateNY = getTimeZoneTime(timezoneNY, milliseconds);
        timezoneNewYork.setText("" + sdf.format(resultDateNY));

        TimeZone timezoneP = TimeZone.getTimeZone("Europe/Paris");
        resultDateP = getTimeZoneTime(timezoneP, milliseconds);
        timezoneParis.setText("" + sdf.format(resultDateP));

        TimeZone timezoneL = TimeZone.getTimeZone("Europe/London");
        resultDateL = getTimeZoneTime(timezoneL, milliseconds);
        timezoneLondon.setText("" + sdf.format(resultDateL));

        TimeZone timezoneB = TimeZone.getTimeZone("Etc/GMT+8");
        resultDateB = getTimeZoneTime(timezoneB, milliseconds);
        timezoneBeijing.setText("" + sdf.format(resultDateB));
    }

    private long getGMTtime() {
        Calendar current;
        long milliseconds;
        current = Calendar.getInstance();

        milliseconds = current.getTimeInMillis();

        TimeZone tzCurrent = current.getTimeZone();
        int offset = tzCurrent.getRawOffset();
        if (tzCurrent.inDaylightTime(new Date())) {
            offset = offset + tzCurrent.getDSTSavings();
        }

        milliseconds = milliseconds - offset;

        return milliseconds;
    }
    
    private Date getTimeZoneTime(TimeZone timezone, long milliseconds) {
        Date resultDate;
        String TimeZoneName = timezone.getDisplayName();
        int TimeZoneOffset = timezone.getRawOffset() / (60 * 1000);
        int hrs = TimeZoneOffset / 60;
        int mins = TimeZoneOffset & 60;

        milliseconds = milliseconds + timezone.getRawOffset();
        resultDate = new Date(milliseconds);
        milliseconds = 0;
        return resultDate;
    }




}
