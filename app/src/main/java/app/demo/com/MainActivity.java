package app.demo.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import app.demo.com.network.ApiHelper;
import app.demo.com.network.RequestCallBack;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String JSON_KEY = "json";
    public SharedPreferences settings;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_click_me);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(JSON_KEY, "");
        if (StringUtils.isEmpty(value)) {
            String url = "http://10.16.6.35:8080/geoserverV2/Flygeo/ows";
            ApiHelper.getInstance().getJson(this, url, "WFS", "1.0.0", "GetFeature", "Flygeo:zones", "1", "application/json",
                    new RequestCallBack() {
                        @Override
                        public void onResponse(Object response) {
                            if (response != null) {
                                String json = "";
                                try {
                                    // We need an Editor object to make preference changes.
                                    // All objects are from android.context.Context
                                    json = ((ResponseBody) response).string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    json = "";
                                }
                                editor.putString(JSON_KEY, json);
                                // Commit the edits!
                                editor.commit();
                            }
                        }
                    }
            );
        }
    }
}

