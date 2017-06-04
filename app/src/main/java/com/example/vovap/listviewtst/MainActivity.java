package com.example.vovap.listviewtst;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.layout.simple_gallery_item;

public class MainActivity extends Activity {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listView == null) {
            listView = (ListView) findViewById(R.id.listView);
        }

        adapter = new ArrayAdapter<>(this
                , simple_gallery_item
                , listItems);
        listView.setAdapter(adapter);

    }

    public void onPressAdd(View view) {
        TextView textView = (TextView) findViewById(R.id.editText2);
        String itemString = textView.getText().toString();
        textView.setText(null);

        listItems.add(itemString);
        adapter.notifyDataSetChanged();
    }

    public void onPressDeleteAll(View view){
        listItems.removeAll(listItems);
        adapter.notifyDataSetChanged();
    }


}
