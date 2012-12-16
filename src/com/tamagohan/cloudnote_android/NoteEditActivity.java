package com.tamagohan.cloudnote_android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteEditActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Log.v("EXAMPLE", "onCreate of noteEditActivity was called.");
        setContentView(R.layout.activity_note_edit);
		EditText title = (EditText) findViewById(R.id.note_title);
		EditText body  = (EditText) findViewById(R.id.note_body);
		Button editButton = (Button) findViewById(R.id.note_update);
		Button backButton = (Button) findViewById(R.id.note_back);
		final Bundle extras  = getIntent().getExtras();
		
		title.setText(extras.getString("TITLE"));
		body .setText(extras.getString("BODY"));
		
		backButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			finish();
   			}
   		});
   	}
}