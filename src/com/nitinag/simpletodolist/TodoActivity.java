package com.nitinag.simpletodolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		populateArrayItems();
		todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		setUpListViewListener();
	}
	
	private void readItems(){
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch(IOException e){
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems(){
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void setUpListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View item,
					int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}
			
		});
		
	}


	private void populateArrayItems() {
		readItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	
	public void addTodoItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		todoAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}

}
