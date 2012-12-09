package com.tamagohan.cloudnote_android;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteListAdapter extends ArrayAdapter {  
      
    private ArrayList items;  
    private LayoutInflater inflater;  
      
    public NoteListAdapter(Context httpPostHandler, int textViewResourceId,  ArrayList items) {  
    	super(httpPostHandler, textViewResourceId, items);  
    	this.items = items;  
    	this.inflater = (LayoutInflater) httpPostHandler  
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }  
      
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
    	// ビューを受け取る  
    	View view = convertView;  
    	if (view == null) {  
    		// 受け取ったビューがnullなら新しくビューを生成  
    		view = inflater.inflate(R.layout.note_row, null);  

    		// 表示すべきデータの取得  
    		ArrayList<String> item = (ArrayList<String>) items.get(position);
    		if (item != null) {  
    			TextView title = (TextView)view.findViewById(R.id.title);
    			TextView body  = (TextView)view.findViewById(R.id.body);
      
    			// titleをビューにセット  
    			if (title != null) {  
    				title.setText(item.get(0));  
    			}  
      
    			// テキストをビューにセット  
    			if (body != null) {  
    				body.setText(item.get(1));  
    			}  
    		}  
    		return view;  
    	}
    	return view;
    }
}