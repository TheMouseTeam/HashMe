package com.barmpas.hashme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * EncryptionKeyActivity is the activity where the user sets the encryption key.
 * @author Konstantinos Barmpas
 */
public class EncryptionKeyActivity extends AppCompatActivity {

    /**
     * EditText for the encryption key
     */
    private EditText key;
    /**
     * Button to launch encryption
     */
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption_key_activity);

        //Set the UI
        key = (EditText) findViewById(R.id.key_txt);
        btn_next=(Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (key.getText().toString().equals("") || key.getText().toString()==null) {
                    Toast.makeText(EncryptionKeyActivity.this, getResources().getString(R.string.type_key), Toast.LENGTH_SHORT).show();
                }else if (key.length()<3 || key.length()>10) {
                        Toast.makeText(EncryptionKeyActivity.this, getResources().getString(R.string.key_range), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(EncryptionKeyActivity.this, EncryptionActivity.class);
                    intent.putExtra("key",key.getText().toString());
                    key.setText("");
                    startActivity(intent);
                }
            }
        });
    }
}
