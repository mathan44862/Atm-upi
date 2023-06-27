package com.example.myapplication;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<String> a = new ArrayList<>();
    ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        l = (ListView) findViewById(R.id.listview);
        new Async().execute();
       
    }
    class Async extends AsyncTask<Void, Void, Void> {
        @Override

        protected Void doInBackground(Void... voids) {

            try

            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.71.234:3306/upi", "andro"
                        ,"Mathan@22");
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from recieve_9443244862");

                while(resultSet.next()) {

                     a.add(resultSet.getString(2)+"\n"+resultSet.getString(1)+"\n"+resultSet.getString(3));

                }
                connection.close();

            } catch (Exception e) {
                System.out.print(e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            show();
            super.onPostExecute(aVoid);

        }
    }
    private void show() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.listview,R.id.textView2,a);
        l.setAdapter(arrayAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("message_key", a.get(i));
                startActivity(intent);
            }
        });
    }
}
