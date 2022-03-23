package ru.gordei.firebase2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabase extends AppCompatActivity {

    private EditText answer;
    private DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        myRef = FirebaseDatabase.getInstance().getReference();

        answer = findViewById(R.id.answer_edit_txt);

        findViewById(R.id.credit_btn).setOnClickListener(view -> {
            // .push() - добавить вложенное значение
            if(answer.getText().toString().equals("да"))
                myRef.child("users").child(user.getUid()).child("card_action").child("credit")
                        .child("close").setValue(1);
            else if (answer.getText().toString().equals("нет"))
                myRef.child("users").child(user.getUid()).child("card_action").child("credit")
                        .child("close").setValue(0);
            else
                Toast.makeText(RealtimeDatabase.this, "Ошибка ввода",
                        Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.debit_btn).setOnClickListener(view -> {
            if(answer.getText().toString().equals("да"))
                myRef.child("users").child(user.getUid()).child("card_action").child("debit")
                        .child("close").setValue(1);
            else if (answer.getText().toString().equals("нет"))
                myRef.child("users").child(user.getUid()).child("card_action").child("debit")
                        .child("close").setValue(0);
            else
                Toast.makeText(RealtimeDatabase.this, "Ошибка ввода",
                        Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.events_btn).setOnClickListener(view -> {
            Intent intent = new Intent(RealtimeDatabase.this, Events.class);
            startActivity(intent);
        });
    }
}