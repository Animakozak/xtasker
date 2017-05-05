package io.excitinglab.xtasker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import io.excitinglab.xtasker.TodayFragment;

import static java.sql.Types.NULL;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = DatabaseHelper.class.getName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TaskManager";

    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_LISTS = "lists";
//    private static final String TABLE_SUBTASKS = "subtasks";

    private static final String LIST_ID = "id";
    private static final String LIST_NAME = "name";

    private static final String TASK_ID = "id";
    private static final String TASK_NAME = "name";
    private static final String TASK_STATUS = "status";
    private static final String TASK_DEADLINE = "deadline";
    private static final String TASK_REMINDER = "reminder";
    private static final String TASK_NOTE = "note";
    private static final String TASK_P_ID = "p_id";

//    private static final String SUBTASK_ID = "id";
//    private static final String SUBTASK_NAME = "name";
//    private static final String SUBTASK_STATUS = "status";
//    private static final String SUBTASK_P_ID = "p_id";

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE "
            + TABLE_TASKS + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_NAME + " TEXT, " + TASK_STATUS + " INTEGER," + TASK_DEADLINE
            + " DATETIME, " + TASK_REMINDER + " DATETIME, "
            + TASK_NOTE + " TEXT, " + TASK_P_ID + " INTEGER);";

    private static final String CREATE_TABLE_LISTS = "CREATE TABLE " + TABLE_LISTS
            + "(" + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LIST_NAME + " TEXT)";

//    private static final String CREATE_TABLE_SUBTASKS = "CREATE TABLE "
//            + TABLE_SUBTASKS + "(" + SUBTASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + SUBTASK_STATUS + " INTEGER," + SUBTASK_NAME + " TEXT, "
//            + SUBTASK_P_ID + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_LISTS);
//        db.execSQL(CREATE_TABLE_SUBTASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBTASKS);

        onCreate(db);
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    // ------------------------ "lists" table methods ----------------//

    public int createList(Lists list) {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = list.getName();
        String selectQuery = "SELECT  * FROM " + TABLE_LISTS + " WHERE " + LIST_NAME + " = " + "\"" + name + "\"";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) list.setName(changeList(list));
        else list.setName(list.getName());

        ContentValues values = new ContentValues();
        values.put(LIST_NAME, list.getName());
        long list_id = db.insert(TABLE_LISTS, null, values);

        if (list_id == -1) {
            return -1;
        } else {

        }

        return getList(list.getName()).getId();
    }

    private String changeList(Lists list) {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = list.getName();
        String tempName = name;

        for (int i = 1; i < 1000000000; i++) {
            String selectQuery = "SELECT  * FROM " + TABLE_LISTS + " WHERE " + LIST_NAME + " = " + "\"" + tempName + "\"";
            Cursor c = db.rawQuery(selectQuery, null);
            Log.e("CHANGE LIST", selectQuery);

            if (c.moveToFirst()) {
                Lists t = new Lists();
                t.setId(c.getInt((c.getColumnIndex(LIST_ID))));
                t.setName(c.getString(c.getColumnIndex(LIST_NAME)));
                tempName = name + " " + i;
            }
            else {
                i = i-1;
                return name + " " + i;
            }
            c.close();
        }
        return name;
    }

    public Lists getList(String name) {
        String selectQuery = "SELECT  * FROM " + TABLE_LISTS +
                " WHERE " + LIST_NAME + " = " + "\"" + name + "\"";

//        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Lists t = new Lists();
        if (c.moveToFirst()) {
            t.setId(c.getInt((c.getColumnIndex(LIST_ID))));
            t.setName(c.getString(c.getColumnIndex(LIST_NAME)));
        }
        return t;
    }

    public Lists getListByID(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_LISTS +
                " WHERE " + LIST_ID + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Lists t = new Lists();
        if (c.moveToFirst()) {
            t.setId(c.getInt((c.getColumnIndex(LIST_ID))));
            t.setName(c.getString(c.getColumnIndex(LIST_NAME)));
        }
        return t;
    }

    public List<Lists> getAllLists() {
        List<Lists> lists = new ArrayList<Lists>();
        String selectQuery = "SELECT  * FROM " + TABLE_LISTS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Lists t = new Lists();
                t.setId(c.getInt((c.getColumnIndex(LIST_ID))));
                t.setName(c.getString(c.getColumnIndex(LIST_NAME)));

                lists.add(t);
            } while (c.moveToNext());
        }
        return lists;
    }

    public boolean updateList(Lists list) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LIST_NAME, list.getName());

        long i = db.update(TABLE_LISTS, values, LIST_ID + " = ?",
                new String[]{String.valueOf(list.getId())});
        if (i == -1){
            return false;
        }
        else {
            return true;
        }

    }

    public void deleteList(Lists list) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Task> tasks = getAllTasks(list);

        for (Task t : tasks) {
            deleteTask(t.getId());
        }

        db.delete(TABLE_LISTS, LIST_ID + " = ?",
                new String[]{String.valueOf(list.getId())});
    }



    // ------------------------ "tasks" table methods ----------------//

    public long createTask(Task task, int list_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getName());
        values.put(TASK_STATUS, task.getStatus());
        values.put(TASK_DEADLINE, task.getDeadline());
        values.put(TASK_REMINDER, task.getReminder());
        values.put(TASK_NOTE, task.getNote());
        values.put(TASK_P_ID, list_id);

        // insert row
        long task_id = db.insert(TABLE_TASKS, null, values);

        // add parent id !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        return task_id;
    }

    public Task getTask(long task_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE "
                + TASK_ID + " = " + task_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Task t = new Task();
        t.setId(c.getInt(c.getColumnIndex(TASK_ID)));
        t.setName((c.getString(c.getColumnIndex(TASK_NAME))));

        return t;
    }

    public List<Task> getAllTasks(Lists list) {
        List<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT " + TABLE_TASKS + "." + TASK_ID + ", " +
                TABLE_TASKS + "." + TASK_NAME + ", " +
                TABLE_TASKS + "." + TASK_STATUS + ", " +
                TABLE_TASKS + "." + TASK_DEADLINE + ", " +
                TABLE_TASKS + "." + TASK_REMINDER + ", " +
                TABLE_TASKS + "." + TASK_NOTE + " FROM " +
                TABLE_TASKS + ", " + TABLE_LISTS + " WHERE " +
                TABLE_TASKS + "." + TASK_P_ID + " = " + list.getId();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Task t = new Task();
                t.setId(c.getInt(c.getColumnIndex(TASK_ID)));
                t.setName(c.getString(c.getColumnIndex(TASK_NAME)));
                t.setStatus(c.getInt(c.getColumnIndex(TASK_STATUS)));
                t.setDeadline(c.getLong(c.getColumnIndex(TASK_DEADLINE)));
                t.setReminder(c.getLong(c.getColumnIndex(TASK_REMINDER)));
                t.setNote(c.getString(c.getColumnIndex(TASK_NOTE)));

                tasks.add(t);
            } while (c.moveToNext());
        }

        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getName());
        values.put(TASK_STATUS, task.getStatus());
        values.put(TASK_DEADLINE, task.getDeadline());
        values.put(TASK_REMINDER, task.getReminder());
        values.put(TASK_NOTE, task.getNote());

        return db.update(TABLE_TASKS, values, TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    public void deleteTask(long task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, TASK_ID + " = ?",
                new String[] { String.valueOf(task_id) });
    }



    // ------------------------ "subtasks" table methods ----------------//



}
