package com.example.se2_clermont;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button buttonCalc;
    private EditText input;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        button = (Button) findViewById(R.id.button);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                       Network thread = new Network();
                       Thread backgroundThread = new Thread(thread);
                       backgroundThread.start();

            }
        });


    }
    class Network implements Runnable {

        public BufferedReader reader;
        public PrintWriter writer;
        public Socket socket;

        public void disconnectFromServer() throws IOException {
            reader.close();
            writer.close();
            socket.close();
        }
        public void getDataWithString(String message) throws IOException {
            try {  writer.println(message);
                String response = reader.readLine();
                output.setText(response);}
            catch(Exception ex){
                System.out.println("Error "+ex);
            }
            finally{ disconnectFromServer();}

        }
        public void run() {

            try {
                socket = new Socket( "se2-isys.aau.at", 53212);
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                getDataWithString(input.getText().toString());
            } catch (Exception ex) {
                System.out.println("Error "+ex);
            }
        }
    }
}