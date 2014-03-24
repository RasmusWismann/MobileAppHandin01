package dk.itu.rwis.apphandin01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookDAO {

	public final static String TABLE_NAME = "Books";
	
	public final static String ID_COL = "_id";
	public final static String TITLE_COL = "Title";
	public final static String AUTHOR_COL = "Author";
	public final static String PAGES_COL = "Pages";
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public BookDAO(Context context) {
		helper = new DBHelper(context);
	}
	
	public void open() {
		db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
	
	public void addBook(String title, String author, int pages) {
		ContentValues values =  new ContentValues();
		values.put(TITLE_COL, title);
		values.put(AUTHOR_COL, author);
		values.put(PAGES_COL, pages);
		db.insert(TABLE_NAME, null, values);
	}
	
	public void deleteBook(long id) {
		db.delete(TABLE_NAME,  ID_COL + " = ?", new String[] { String.valueOf(id) });
	}
		
	public Cursor getAllBooks() {
		String query = "SELECT _id as _id, * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(query, null);
		return cursor;
	}
}
