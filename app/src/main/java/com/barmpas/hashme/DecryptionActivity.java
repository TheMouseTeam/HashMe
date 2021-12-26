package com.barmpas.hashme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.barmpas.hashme.DecryptKeyActivity.english;
import static com.barmpas.hashme.DecryptKeyActivity.greek;
import static com.barmpas.hashme.DecryptKeyActivity.ints;

/**
 * DecryptionActivity is the activity that performs the decryption of the text.
 * @author Konstantinos Barmpas
 */
public class DecryptionActivity extends AppCompatActivity {

    /**
     * TextView to display the message
     */
    private TextView message_txt;
    /**
     * TextView for cancel activity
     */
    private TextView cancel_btn;
    /**
     * Hypster class object
     */
    private HypsterClass hypster;
    /**
     * The decryption key
     */
    private String decrypt_key;
    /**
     * The decoded message
     */
    private String decoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypt_layout);
        setTitle(getResources().getString(R.string.decrypted_message));

        //Get decryption key from intent
        Intent intent = getIntent();
        decrypt_key=intent.getStringExtra("key");

        //Set UI
        message_txt = (TextView) findViewById(R.id.text_edit);
        cancel_btn = (TextView) findViewById(R.id.cancel_txt);

        //Create Hypster Class and decode the text
        hypster = new HypsterClass(null, english, greek, ints,DecryptionActivity.this);
        decoded=hypster.DecodeStart(decrypt_key);
        message_txt.setText(decoded);

        //Finish the Activity
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
