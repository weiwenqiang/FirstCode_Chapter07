package com.example.chapter07.contacts;

import java.util.ArrayList;
import java.util.List;

import com.example.chapter07.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestContacts extends Activity {
	ListView contactsView;
	ArrayAdapter<String> adapter;
	List<String> contactsList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b1_contacts);
		contactsView = (ListView) findViewById(R.id.contacts_listview);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
		contactsView.setAdapter(adapter);
		readContacts();
	}
	private void readContacts(){
		Cursor cursor = null;
		try {
			//查询联系人数据
			cursor = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			while(cursor.moveToNext()){
				//获取联系人姓名
				String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				//获取联系人手机号
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactsList.add(displayName+"\n"+number);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cursor != null){
				cursor.close();
			}
		}
	}
}
