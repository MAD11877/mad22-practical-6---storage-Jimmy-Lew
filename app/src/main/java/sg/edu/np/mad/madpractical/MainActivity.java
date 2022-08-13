package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent INTENT = getIntent();

        String name = INTENT.getStringExtra("name");
        String description = INTENT.getStringExtra("description");
        int id = INTENT.getIntExtra("id", 0);
        boolean followed = INTENT.getBooleanExtra("followed", false);

        user = new User(name, description, id, followed);
        NoArgFunction<String> followText = () -> user.followed ? "Unfollow" : "Follow";

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(user.name);

        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(user.description);

        Button followButton = findViewById(R.id.followButton);
        followButton.setText(followText.toString());

        followButton.setOnClickListener(view -> {
            user.followed = !user.followed;
            Button followButton1 = (Button) view;
            followButton1.setText(followText.toString());
            Toast.makeText(getApplicationContext(), user.followed ? "Followed" : "Unfollowed",Toast.LENGTH_SHORT).show();

            Intent data = new Intent();
            data.putExtra("followed", user.followed);
            new DBHandler(getApplicationContext()).updateUser(user);
        });

        Button messageButton = findViewById(R.id.messageButton);
        messageButton.setOnClickListener(view -> {
            Intent MessageGroup = new Intent(getApplicationContext(), MessageGroup.class);
            startActivity(MessageGroup);
        });
    }

    interface NoArgFunction<R> {
        R apply();
    }
}