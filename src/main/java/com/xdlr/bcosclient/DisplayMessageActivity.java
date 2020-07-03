package com.example.mytestapplication;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytestapplication.autoconfigure.Web3jManager;

import org.fisco.bcos.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Web3jManager web3jManager = new Web3jManager(getApplicationContext());

        Web3j web3j = web3jManager.genWeb3j();
        if (web3j == null) {
            Toast.makeText(this, "get web3j failed!", Toast.LENGTH_LONG);
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

}
