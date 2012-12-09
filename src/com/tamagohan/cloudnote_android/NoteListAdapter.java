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
    	// �r���[���󂯎��  
    	View view = convertView;  
    	if (view == null) {  
    		// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�  
    		view = inflater.inflate(R.layout.note_row, null);  

    		// �\�����ׂ��f�[�^�̎擾  
    		ArrayList<String> item = (ArrayList<String>) items.get(position);
    		if (item != null) {  
    			TextView title = (TextView)view.findViewById(R.id.title);
    			TextView body  = (TextView)view.findViewById(R.id.body);
      
    			// title���r���[�ɃZ�b�g  
    			if (title != null) {  
    				title.setText(item.get(0));  
    			}  
      
    			// �e�L�X�g���r���[�ɃZ�b�g  
    			if (body != null) {  
    				body.setText(item.get(1));  
    			}  
    		}  
    		return view;  
    	}
    	return view;
    }
}