package com.example.demodb;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText etName, etNumber;
    Button btnAdd;
    ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        btnAdd = findViewById(R.id.btn_add);
        lvContacts = findViewById(R.id.lv_contacts);

    }

    public void addContact(View view) {
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();

        if(name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Incomplete details.", Toast.LENGTH_SHORT).show();
        } else {
            if(dbHelper.addContact(name, number)) {
                Toast.makeText(this, name + " is added.", Toast.LENGTH_SHORT).show();
                loadContacts();
            } else {
                Toast.makeText(this, "Error while adding.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadContacts() {
        ArrayList<String> contacts = dbHelper.getAllContacts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        lvContacts.setAdapter(adapter);
    }
}