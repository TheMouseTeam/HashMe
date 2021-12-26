package com.barmpas.hashme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * EncryptionActivity is the activity that performs the encryption of the text
 * and launches the email.
 * @author Konstantinos Barmpas
 */
public class EncryptionActivity extends AppCompatActivity {

    /**
     * TextInputEditText for the message to encrypt
     */
    private android.support.design.widget.TextInputEditText message_txt;
    /**
     * Button to launch encryption
     */
    private Button encrypt_btn;
    /**
     * TextView to cancel Activity
     */
    private TextView cancel_btn;
    /**
     * Hypster class object
     */
    private HypsterClass hypster;
    /**
     * The encryption key
     */
    public static String encryption_key;
    /**
     * Helper strings for the formation of email
     */
    private String router,vpn,proxy;
    /**
     * Random object
     */
    private Random r;
    /**
     * Fields neccessary to the email
     */
    private String english,greek,ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption_layout);
        setTitle(getResources().getString(R.string.new_message_to_encrypt));

        //Get from intent
        Intent intent = getIntent();
        encryption_key = intent.getStringExtra("key");

        //Set UI
        message_txt = (android.support.design.widget.TextInputEditText) findViewById(R.id.text_edit);
        encrypt_btn = (Button) findViewById(R.id.encrypt_btn);
        cancel_btn = (TextView) findViewById(R.id.cancel_txt);

        //Set onClick Listeners
        encrypt_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (message_txt!=null && encryption_key !=null) {
                    if (message_txt.length()<=50) {
                        r = new Random();
                        //Create Hypster and encrypt message
                        hypster = new HypsterClass(message_txt.getText().toString(), null, null, null, EncryptionActivity.this);
                        hypster.EncodeStart();
                        english = hypster.getEnglish();
                        greek = hypster.getGreek();
                        ints = hypster.getIntString();

                        //For email completion
                        if (english.length() < 75) {
                            char[] router_array = new char[75 - english.length()];
                            for (int i = 0; i < router_array.length; i++) {
                                router_array[i] = (char) (r.nextInt(90 - 65) + 65);
                            }
                            router = String.valueOf(router_array);
                        } else {
                            router = "r";
                        }
                        if (greek.length() < 75) {
                            char[] vpn_array = new char[75 - greek.length()];
                            for (int i = 0; i < vpn_array.length; i++) {
                                vpn_array[i] = (char) (r.nextInt(90 - 65) + 65);
                            }
                            vpn = String.valueOf(vpn_array);
                        } else {
                            vpn = "n";
                        }
                        if (ints.length() < 75) {
                            char[] proxy_array = new char[75 - greek.length()];
                            for (int i = 0; i < proxy_array.length; i++) {
                                if (i % 2 == 0) {
                                    proxy_array[i] = (char) (r.nextInt(49 - 48) + 48);
                                } else {
                                    proxy_array[i] = (char) (35);
                                }
                            }
                            proxy = String.valueOf(proxy_array);
                        } else {
                            proxy = "p";
                        }

                        //Create the Intent
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        //Fill it with Data
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{""});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject));
                        String url = Uri.parse("https://hashme/email").buildUpon().appendQueryParameter("id", english).appendQueryParameter("ver", ints).appendQueryParameter("proxy", proxy).appendQueryParameter("extra", greek).appendQueryParameter("router", router).appendQueryParameter("vpn", vpn).build().toString();
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.hashme_encrypted_email) + "\n" + "\n" + "\n" + url + "\n" + "\n" + "\n" + getResources().getString(R.string.ending) + encryption_key.substring(encryption_key.length() - 2, encryption_key.length()));
                        // Send it off to the Activity-Chooser
                        startActivity(Intent.createChooser(emailIntent, "Send email with..."));
                    }else{
                        Toast.makeText(EncryptionActivity.this,getResources().getString(R.string.chars),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EncryptionActivity.this,getResources().getString(R.string.enter_text),Toast.LENGTH_LONG).show();
                }
            }
        });

        //Return to previous activity
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }


}
