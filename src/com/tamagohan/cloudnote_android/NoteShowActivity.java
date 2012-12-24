package com.tamagohan.cloudnote_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NoteShowActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        Log.v("EXAMPLE", "onCreate was called.");
        setContentView(R.layout.activity_note_show);
        TextView title = (TextView) findViewById(R.id.note_title);
		TextView body  = (TextView) findViewById(R.id.note_body);
		Button editButton = (Button) findViewById(R.id.note_edit);
		Button backButton = (Button) findViewById(R.id.note_back);
		final Bundle extras  = getIntent().getExtras();
		
		title.setText(extras.getString("TITLE"));
		body .setText(extras.getString("BODY"));
		
		editButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			Intent intent = new Intent(NoteShowActivity.this, NoteEditActivity.class);
    			intent.putExtra("TITLE", extras.getString("TITLE"));
    			intent.putExtra("BODY",  extras.getString("BODY"));
    			intent.putExtra("ID",    extras.getString("ID"));
    			view.getContext().startActivity(intent);
   			}
   		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			finish();
   			}
   		});
   	}
}