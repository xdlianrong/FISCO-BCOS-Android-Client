package com.example.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.autoconfigure.Web3jManager;

import org.fisco.bcos.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.mytestapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Web3jManager web3jManager = new Web3jManager(getApplicationContext());
                Web3j web3j = web3jManager.genWeb3j();

                if (web3j == null) {
                    Toast.makeText(MainActivity.this, "get web3j failed!", Toast.LENGTH_LONG).show();
                    return;
                }

                BigInteger blockNumber;
                try {
                    blockNumber = web3j.getBlockNumber().send().getBlockNumber();
                    // get the Intent that started this activity and extract the string
                    Intent intent = getIntent();
                    String message = intent.getStringExtra(String.valueOf(blockNumber));
                    // capture the layout's TextView and set the string as its text
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    called when user click on the Send button
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
