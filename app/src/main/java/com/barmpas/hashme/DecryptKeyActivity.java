package com.barmpas.hashme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * DecryptKeyActivity is the activity where the user sets the decryption key
 * and restores the fields from the email.
 * @author Konstantinos Barmpas
 */
public class DecryptKeyActivity extends AppCompatActivity {

    /**
     * EditText for the decryption key
     */
    private EditText key;
    /**
     * Button to launch decryption
     */
    private Button btn_next;
    /**
     * Necessary fields from the email
     */
    public static String english,greek,ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypt_key_layout);

        //For Decryption get necessary fields from the email
        Uri uri = getIntent().getData();
        if (uri!=null) {
            english = uri.getQueryParameter("id");
            greek = uri.getQueryParameter("extra");
            ints = uri.getQueryParameter("ver");
        }

        //Set UI
        key = (EditText) findViewById(R.id.key_txt);
        btn_next=(Button) findViewById(R.id.btn_next);

        //Check and pass the decryption key
        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (key.getText().toString().equals("") || key.getText().toString() == null) {
                    Toast.makeText(DecryptKeyActivity.this, getResources().getString(R.string.type_key), Toast.LENGTH_SHORT).show();
                } else if (key.length()<3 || key.length()>10) {
                    Toast.makeText(DecryptKeyActivity.this, getResources().getString(R.string.key_range), Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(DecryptKeyActivity.this, DecryptionActivity.class);
                    intent.putExtra("key",key.getText().toString());
                    key.setText("");
                    startActivity(intent);
                }
            }
        });
    }
}
