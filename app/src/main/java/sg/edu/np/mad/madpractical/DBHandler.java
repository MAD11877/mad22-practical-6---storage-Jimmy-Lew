package sg.edu.np.mad.madpractical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    private final String TABLE = " USERS";

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCommand = "CREATE TABLE" + TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, description STRING, followed BOOLEAN)";
        sqLiteDatabase.execSQL(createCommand);

        String Data = "INSERT INTO users (id, name, description, followed) VALUES ";

        for (int i = 1; i <= 20; i++) {

            String nameSuffix = String.valueOf((int) Math.round(Math.random() * 1000000));
            String descSuffix = String.valueOf((int) Math.round(Math.random() * 100000000));

            int id = (int) Math.round(Math.random() * 1000000);
            String name = String.format("Name%s", nameSuffix);
            String desc = String.format("Description %s", descSuffix);
            boolean followed = Math.random() > 0.5;

            Data += String.format("(%s, \"%s\", \"%s\", %s),", id, name, desc, followed);
        }

        sqLiteDatabase.execSQL(Data.substring(0, Data.length() - 1));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM" + TABLE, null);

        while (cursor.moveToNext()){
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            boolean follow = Boolean.parseBoolean(cursor.getString((int)cursor.getColumnIndex("followed")));

            User user = new User(name, description, id, follow) ;
            userList.add(user);
        }

        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        int refId = user.id;

        String UPDATE = "UPDATE users SET followed = \"" + user.followed + "\" WHERE id \"" + refId + "\"";

        sqLiteDatabase.execSQL(UPDATE);
        sqLiteDatabase.close();
    }
}
