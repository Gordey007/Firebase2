package ru.gordei.firebase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAnalytics analytics;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        analytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_edit_txt);
        password = findViewById(R.id.password_edit_txt);

        findViewById(R.id.sign_in_btn).setOnClickListener(this);
        findViewById(R.id.registration_btn).setOnClickListener(this);

        // Есле уже авторизован
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(Authentication.this,
                    RealtimeDatabase.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.sign_in_btn)
            signing(email.getText().toString(), password.getText().toString());
        else if (view.getId() == R.id.registration_btn)
            registration(email.getText().toString(), password.getText().toString());
    }

    public void signing(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
                task -> {
            if(task.isSuccessful()) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "signin");
                bundle.putString("client_name", "Gordei");
                analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

                Toast.makeText(Authentication.this, "Aвторизация успешна",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Authentication.this,
                        RealtimeDatabase.class);

                startActivity(intent);
            }
            else
                Toast.makeText(Authentication.this, "Aвторизация провалена",
                        Toast.LENGTH_SHORT).show();
        });
    }

    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                task -> {
            if(task.isSuccessful()) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "registration");
                bundle.putString("client_name", "Gordei");
                analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                Toast.makeText(Authentication.this, "Регистрация успешна",
                        Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(Authentication.this, "Регистрация провалена",
                        Toast.LENGTH_SHORT).show();
        });
    }
}