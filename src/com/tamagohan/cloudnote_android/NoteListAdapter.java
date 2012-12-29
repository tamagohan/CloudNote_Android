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
      
    private ArrayList items;  
    private LayoutInflater inflater;  
      
    public NoteListAdapter(Context httpPostHandler, int textViewResourceId,  ArrayList items) {  
    	super(httpPostHandler, textViewResourceId, items);  
    	Log.d("tmp", "cccccccccccc------------");
    	this.items = items;
    	this.inflater = (LayoutInflater) httpPostHandler  
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }
    
          
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {
    	Log.d("tmp", "ddddddddd------------");
    	// ビューを受け取る  
    	View view = convertView;  
    	if (view == null) {
    		Log.d("tmp", "eeeeeeee------------");
    		// 受け取ったビューがnullなら新しくビューを生成  
    		view = inflater.inflate(R.layout.note_row, null);  
    		
    		// 背景画像をセットする
    		view.setBackgroundResource(R.drawable.back_ground);

    		// 表示すべきデータの取得  
    		final ArrayList<String> item = (ArrayList<String>) items.get(position);
    		if (item != null) {  
    			TextView title   = (TextView)view.findViewById(R.id.title);
    			TextView body    = (TextView)view.findViewById(R.id.body);
    			TextView id      = (TextView)view.findViewById(R.id.note_id);
    			ImageView image  = (ImageView)view.findViewById(R.id.note_icon);
    			
    			title.setTypeface(Typeface.DEFAULT_BOLD);
      
    			// titleをビューにセット  
    			if (title != null) {  
    				title.setText(item.get(0));  
    			}  
      
    			// テキストをビューにセット  
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
    	return view;
    }
}