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
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.BreakIterator;

public class MainActivity3 extends AppCompatActivity {

    TextView t,t1;

    Button b1;
    String str;
    String[] arr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        t =(TextView) findViewById(R.id.textView3) ;
        t1 = (TextView) findViewById(R.id.textView4);
        Intent intent = getIntent();
        str = intent.getStringExtra("message_key");
        arr = str.split("\n");
        t1.setText(arr[0]);
        t.setText(arr[2]);
        b1=(Button) findViewById(R.id.button5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Async().execute();
            }
        });
    }
    class Async extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            BreakIterator e = null;
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.71.234:3306/atm", "andro"
                        ,"Mathan@22");
                Statement statement = connection.createStatement();
                String query = "update atm set balance="+arr[2]+" where atm_upi='atm01@hdfcbank'";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.execute();
                connection.close();
                Connection connection1 = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.71.234:3306/upi", "andro"
                        ,"Mathan@22");
                Statement statement1 = connection1.createStatement();
                String balance = null;
                String query1 = "select * from account where upi_id='mathan@hdfcbank'";
                ResultSet resultSet = statement1.executeQuery(query1);
                while(resultSet.next()) {
                     balance = resultSet.getString("account_balance");
                }
                connection.close();
                connection1.close();
                Connection connection2 = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.71.234:3306/upi", "andro"
                        ,"Mathan@22");
                int balance1 = Integer.parseInt(balance);
                int balance2 = Integer.parseInt(arr[2]);
                String query2 = "update account set account_balance="+(balance1-balance2)+" where upi_id='mathan@hdfcbank'";
                PreparedStatement preparedStmt2 = connection2.prepareStatement(query2);
                preparedStmt2.execute();
                connection2.close();
            } catch (Exception e1) {
                e.setText(e1.toString());
            }
            return null;
        }
        protected void onPostExecute(Void aVoid) {
            show();
            super.onPostExecute(aVoid);

        }
    }
    private void show() {
        Intent i = new Intent(getApplicationContext(),MainActivity4.class);
        startActivity(i);
    }
}
