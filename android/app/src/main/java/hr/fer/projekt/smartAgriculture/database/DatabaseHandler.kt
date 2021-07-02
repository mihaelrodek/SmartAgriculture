package hr.fer.projekt.smartAgriculture.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import hr.fer.projekt.smartAgriculture.model.TaskModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TasksDatabase"
        private const val TABLE_TASKS = "TasksTable"

        private const val KEY_ID = "_id"
        private const val KEY_TASK = "task"
        private const val KEY_USER = "user"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TASKS_TABLE = ("CREATE TABLE $TABLE_TASKS(" +
                "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_TASK TEXT, " +
                "$KEY_USER TEXT)")
        db?.execSQL(CREATE_TASKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS)
        onCreate(db)
    }

    fun addTask(task: TaskModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TASK, task.task)
        contentValues.put(KEY_USER, task.user)

        val success = db.insert(TABLE_TASKS, null, contentValues)

        getTasks(" ").forEach{
            Log.d("EEEEEE", it.task)
        }

        db.close()
        return success
    }

    fun getTasks(user: String): ArrayList<TaskModel> {
        val taskList: ArrayList<TaskModel> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_TASKS" +
                " WHERE $TABLE_TASKS.$KEY_USER=\"$user\""

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var task: String
        var user: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                task = cursor.getString(cursor.getColumnIndex(KEY_TASK))
                user = cursor.getString(cursor.getColumnIndex(KEY_USER))

                val task = TaskModel(taskId = id, task = task, user = user)
                taskList.add(task)
            } while (cursor.moveToNext())
        }

        return taskList
    }

    fun updateTask(task: TaskModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TASK, task.task)
        contentValues.put(KEY_USER, task.user)

        val success = db.update(TABLE_TASKS, contentValues, KEY_ID + "=" + task.taskId, null)

        db.close()
        return success
    }

    fun deleteTask(task: TaskModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.taskId)

        val success = db.delete(TABLE_TASKS, KEY_ID + "=" + task.taskId, null)

        db.close()
        return success
    }
}
