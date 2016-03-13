package com.example.fileexplorer;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ArrayList<String> directoryList = 
			new ArrayList<String>();
	private ArrayList<String> fileList = 
			new ArrayList<String>();
	private File currentDirectory = new File("/sdcard");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		browseTo(currentDirectory);
	}
	private void fill(File[] files) {
		fileList.clear();
		directoryList.clear();
		if (currentDirectory.getParent() != null) {
			fileList.add("[..]");
			directoryList.add("..");
		}
		for (File currentFile : files) {
			if (currentFile.isDirectory()) {
				fileList.add("["+currentFile.getName()+"]");
			} else {
				fileList.add(currentFile.getName());
			}
			directoryList.add(currentFile.getAbsolutePath());
		}
		ListView listView = (ListView)findViewById(R.id.list);
		ArrayAdapter<String> arrayAdapter =
				new ArrayAdapter<String>(getBaseContext(),
						android.R.layout.simple_list_item_1,
						fileList);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, 
					int arg2, long arg3) {
				String selectedString = directoryList.get(arg2);
				if (selectedString.equals("..")) {
					upOneLevel();
				} else {
					File clickedFile = new File(selectedString);
					if (clickedFile != null) {
						browseTo(clickedFile);
					}
				}
			}
		});
	}
	private void browseTo(final File directory) {
		if (directory.isDirectory()) {//폴더인경우
			try {
				if (directory.listFiles() != null) {
					this.currentDirectory = directory;
					fill(directory.listFiles());
				}
			} catch(Exception e) { e.printStackTrace(); }
		} else {//파일인 경우
			
		}
	}
	private void upOneLevel() {
		if (currentDirectory.getParent() != null) {
			this.browseTo(currentDirectory.getParentFile());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
