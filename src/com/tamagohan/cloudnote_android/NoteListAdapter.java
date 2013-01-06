package com.tamagohan.cloudnote_android;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteListAdapter extends ArrayAdapter<Object> {  
      
    @SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> items;  
    private LayoutInflater inflater;  
      
    @SuppressWarnings("unchecked")
	public NoteListAdapter(Context httpPostHandler, int textViewResourceId,  @SuppressWarnings("rawtypes") ArrayList items) {  
    	super(httpPostHandler, textViewResourceId, items);  
    	this.items = items;
    	this.inflater = (LayoutInflater) httpPostHandler  
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }
    
    public void debug_items(){
    	for (int i = 0; i < items.size(); i++) {
    		@SuppressWarnings({ "unused", "rawtypes" })
			ArrayList tmp_item = (ArrayList) items.get(i);
    	}
    }
     
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {
    	// ビューを受け取る  
    	View view = convertView;  
    	// 受け取ったビューがnullなら新しくビューを生成  
    	view = inflater.inflate(R.layout.note_row, null);  
    		
    	// 背景画像をセットする
    	view.setBackgroundResource(R.drawable.back_ground);

    	// 表示すべきデータの取得  
    	@SuppressWarnings("unchecked")
		final ArrayList<String> item = (ArrayList<String>) items.get(position);
    	if (item != null) {  
    		TextView title   = (TextView)view.findViewById(R.id.title);
    		TextView body    = (TextView)view.findViewById(R.id.body);
    		ImageView image  = (ImageView)view.findViewById(R.id.note_icon);

    		title.setTypeface(Typeface.DEFAULT_BOLD);

    		// titleをビューにセット  
    		if (title != null) {  
    			title.setText(item.get(0));  
  			}  
      
    		// bodyをビューにセット  
    		if (body != null) {  
    			body.setText(item.get(1));  
   			}
    			
    		image.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View view) {
    				Intent intent = new Intent(view.getContext(), NoteShowActivity.class);
    				intent.putExtra("TITLE", item.get(0));
    				intent.putExtra("BODY",  item.get(1));
    				intent.putExtra("ID",    item.get(2));
    				view.getContext().startActivity(intent);
    			}
    		});
    	}  
   		return view;  
    }
}