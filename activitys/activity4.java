package com.example.myapplication;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity4 extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);
        t=(TextView) findViewById(R.id.textView);
        String checkedMark = "\u2713";
        t.setText(checkedMark);
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
                //Statement statement = connection.createStatement();
                String query = "delete from recieve_9443244862";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
               // ResultSet resultSet = statement.executeQuery("delete from recieve_9443244862");
                preparedStmt.execute();
                connection.close();

            } catch (Exception e) {
                System.out.print(e.toString());
            }
            return null;
        }
        protected void onPostExecute(Void aVoid) {
            MainActivity4 m = new MainActivity4();
            Thread t = new Thread((Runnable) m);
            t.start();
            super.onPostExecute(aVoid);

        }
    }
    public void run() {
        try {
            Thread.sleep(5*60*10*100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}
