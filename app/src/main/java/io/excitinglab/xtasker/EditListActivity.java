package io.excitinglab.xtasker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditListActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnDel, btnSave;
    private EditText editText;
    Intent intent;
    int selectedID;
    Lists list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        Intent intent = getIntent();
        selectedID = intent.getIntExtra("id",-1);

        editText = (EditText) findViewById(R.id.editText);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel= (Button) findViewById(R.id.btnDel);
        mDatabaseHelper = new DatabaseHelper(this);

        getSupportActionBar().setTitle("Edit list");

        list = new Lists();
        list = mDatabaseHelper.getListByID(selectedID);
        editText.setText(list.getName());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editText.getText().toString();
                if(!item.equals("")){
                    Lists list;
                    list = mDatabaseHelper.getListByID(selectedID);
                    list.setName(item);
                    mDatabaseHelper.updateList(list);
                    toastMessage(mDatabaseHelper.getListByID(selectedID).getName());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    finish();
                } else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteList(mDatabaseHelper.getListByID(selectedID));
                editText.setText("");
                toastMessage("removed from database");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                Intent intent = new Intent();
                intent.putExtra("name", "deleted");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
