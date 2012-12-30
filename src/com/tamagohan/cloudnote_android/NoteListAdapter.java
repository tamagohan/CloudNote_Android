package com.tamagohan.cloudnote_android;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteListAdapter extends ArrayAdapter {  
      
    public ArrayList items;  
    private LayoutInflater inflater;  
      
    public NoteListAdapter(Context httpPostHandler, int textViewResourceId,  ArrayList items) {  
    	super(httpPostHandler, textViewResourceId, items);  
    	Log.d("tmp", "cccccccccccc------------");
    	this.items = items;
    	this.inflater = (LayoutInflater) httpPostHandler  
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }
    
    public void debug_items(){
    	for (int i = 0; i < items.size(); i++) {
    		ArrayList tmp_item = (ArrayList) items.get(i);
    		Log.d("tmp", (String) tmp_item.get(0));
    	}
    }
     
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {
    	Log.d("tmp", "ddddddddd------------");
    	Log.d("tmp", Integer.toString(position));
    	// �r���[���󂯎��  
    	View view = convertView;  
    	Log.d("tmp", "eeeeeeee------------");
    	// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�  
    	view = inflater.inflate(R.layout.note_row, null);  
    		
    	// �w�i�摜���Z�b�g����
    	view.setBackgroundResource(R.drawable.back_ground);

    	// �\�����ׂ��f�[�^�̎擾  
    	final ArrayList<String> item = (ArrayList<String>) items.get(position);
    	if (item != null) {  
    		TextView title   = (TextView)view.findViewById(R.id.title);
    		TextView body    = (TextView)view.findViewById(R.id.body);
    		TextView id      = (TextView)view.findViewById(R.id.note_id);
    		ImageView image  = (ImageView)view.findViewById(R.id.note_icon);

    		title.setTypeface(Typeface.DEFAULT_BOLD);

    		// title���r���[�ɃZ�b�g  
    		if (title != null) {  
    			Log.d("tmp", item.get(0));
    			title.setText(item.get(0));  
  			}  
      
    		// �e�L�X�g���r���[�ɃZ�b�g  
    		if (body != null) {  
    			body.setText(item.get(1));  
   			}
    			
    		image.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View view) {
    				Intent intent = new Intent(view.getContext(), NoteShowActivity.class);
    				intent.putExtra("TITLE", item.get(0));
    				intent.putExtra("BODY",  item.get(1));
    				intent.putExtra("ID",  item.get(2));
    				view.getContext().startActivity(intent);
    			}
    		});
    	}  
   		return view;  
    }
}