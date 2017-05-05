package io.excitinglab.xtasker;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;


import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    DatabaseHelper db;
    private ListView mListView;
    private int selectedID;
    Intent intent;

    private ArrayAdapter<String> mAdapter;
    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        selectedID = intent.getIntExtra("id",-1);

        getSupportActionBar().setTitle(db.getListByID(selectedID).getName());

        mListView = (ListView) findViewById(R.id.listView);

        populateListView(selectedID);
    }

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//    }

    private void populateListView(int id) {

        db = new DatabaseHelper(this);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.addAll(db.getAllTasks(db.getListByID(id)));

        ArrayList<String> listData = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            listData.add(tasks.get(i).getName());
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent editScreenIntent = new Intent(ListViewActivity.this, EditListActivity.class);
            editScreenIntent.putExtra("id", selectedID);
            startActivityForResult(editScreenIntent, REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(db.getListByID(selectedID).getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        else {
            toastMessage("successfully deleted");
            finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
