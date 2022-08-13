package sg.edu.np.mad.madpractical;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<User> users;
    ListActivity activity;

    public UserAdapter(ArrayList<User> users, ListActivity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(
            R.layout.user,
            parent,
            false
        );

        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

        holder.nameTextView.setText(user.name);
        holder.descriptionTextView.setText(user.description);

        holder.profileImageViewExpanded.setVisibility(
                user.name.endsWith("7")
                    ? View.VISIBLE
                    : View.GONE
        );

        holder.imageView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Profile");
            builder.setMessage("MADness");
            builder.setPositiveButton("View", (dialogInterface, i) -> {
                Intent mainActivity = new Intent(activity, MainActivity.class);
                mainActivity.putExtra("name", user.name);
                mainActivity.putExtra("description", user.description);
                mainActivity.putExtra("id", user.id);
                mainActivity.putExtra("followed", user.followed);

                activity.startActivity(mainActivity);
            });

            builder.setNegativeButton("Close", (dialogInterface, i) -> {});
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, descriptionTextView;
        ImageView profileImageViewExpanded, imageView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);
            profileImageViewExpanded = view.findViewById(R.id.profileImageViewExpanded);
            imageView = view.findViewById(R.id.profileImageView);
        }
    }
}


