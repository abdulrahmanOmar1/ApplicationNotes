package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Date;

public class AddTask extends AppCompatActivity {

    private EditText Name;
    private EditText description;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_task);
        setupSharedPrefs ();
        Name = findViewById (R.id.Name);
        description = findViewById (R.id.description);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences (this);
        editor = prefs.edit ();
    }
    public void onClickaddTask(View view) {
        String name = Name.getText().toString();
        String des = description.getText().toString();

        Gson gson = new Gson ();
        String str = prefs.getString ("data", "");
        Task[] arrtask = gson.fromJson (str, Task[].class);

        if(name.equals("")) {
            Name.setHint ("Please Enter The Task's Name");
            Name.setHintTextColor(Color.parseColor ("#FF8166"));
        }else if(description.equals("")) {
            description.setText ("Please Enter The Description");
            description.setHintTextColor (Color.parseColor ("#F00000"));
        } else {
            if (arrtask == null) {
                arrtask = new Task[1];
                arrtask[0] = new Task (name, des);

            } else {
                Task[] newTasks = new Task[arrtask.length + 1];
                for (int i = 0; i < arrtask.length; i++) {
                    newTasks[i] = arrtask[i];
                }
                 newTasks[arrtask.length] = new Task (name,  des);
                arrtask = newTasks;
            }
                String jsonTasks = gson.toJson (arrtask);
                editor.putString ("data", jsonTasks);
                editor.commit ();

                Intent intent = new Intent (this, ToDoTask.class);
                Name.setText ("");
                description.setText("");
                startActivity(intent);


        }
   }

    public void clickback(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}

