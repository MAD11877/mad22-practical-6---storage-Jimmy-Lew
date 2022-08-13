package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance("https://madpractical-9255b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        findViewById(R.id.loginButton).setOnClickListener(view -> mDatabase.child("Users").child("mad").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) return;
            DataSnapshot snapshot = task.getResult();

            String username = (String) snapshot.child("username").getValue();
            String password = String.valueOf((long) snapshot.child("password").getValue());

            Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);

            EditText usernameEditText = findViewById(R.id.usernameEditText);
            EditText passwordEditText = findViewById(R.id.passwordEditText);

            boolean isValid = username != null
                    && username.equals(usernameEditText.getText().toString())
                    && password.equals(passwordEditText.getText().toString());

            if (!isValid) return;
            startActivity(listActivity);
        }));
    }
}