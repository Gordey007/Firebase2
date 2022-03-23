package ru.gordei.firebase2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Events extends AppCompatActivity {

    private FirebaseAnalytics analytics;
    private int amount_credits = 0;
    private int sum_credit = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        analytics = FirebaseAnalytics.getInstance(this);

        Button shareStories = findViewById(R.id.share_stories_btn);
        Button linkCard = findViewById(R.id.link_card_btn);
        Button takeLoan = findViewById(R.id.take_loan);

        shareStories.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "story");
            bundle.putString("history_name", "quiz");
            analytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
            Toast.makeText(Events.this, "Вы поделились историей", Toast.LENGTH_SHORT)
                    .show();
        });

        linkCard.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type_card", "credit card");
            analytics.logEvent("link_card", bundle);
            Toast.makeText(Events.this, "Вы привязали карту", Toast.LENGTH_SHORT)
                    .show();
        });

        takeLoan.setOnClickListener(v -> {
            // Запуск DebugView - adb shell setprop debug.firebase.analytics.app ru.gordei.firebase2
            analytics.setUserProperty("amount_credits", String.valueOf(++amount_credits));

            sum_credit = amount_credits * 1000000;
            Bundle bundle = new Bundle();
            bundle.putString("sum_credit", String.valueOf(sum_credit));
            analytics.logEvent("sum_credit", bundle);

            Toast.makeText(Events.this, "Количество кредитов: " + amount_credits,
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(Events.this, "Общая сумма кредитов: " + sum_credit,
                    Toast.LENGTH_SHORT).show();
        });
    }
}