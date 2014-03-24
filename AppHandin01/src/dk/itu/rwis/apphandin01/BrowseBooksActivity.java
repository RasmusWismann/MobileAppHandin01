package dk.itu.rwis.apphandin01;

import dk.itu.rwis.apphandin01.db.BookDAO;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class BrowseBooksActivity extends ListActivity {
	
	private BookDAO bookAdapter;
	private SimpleCursorAdapter cursorAdapter;
	private Button newBookButton;
	private Cursor cursor;
	private long currentId;
	
	public static final int ADD_NEW_BOOK = 1;
	public static final int BOOK_DETAILS = 2;
	public static final String DELETE_BOOK_RESULT = "result";
	public static final String ADD_NEW_BOOK_RESULT = "result";
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_books);
		
		bookAdapter = new BookDAO(this);
		bookAdapter.open();
		cursor = bookAdapter.getAllBooks();
		
		String[] fromColumns = { "Title", "Author" };
		int[] toViews = { android.R.id.text1, android.R.id.text2 };
		
		cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, fromColumns, toViews, 0);
		setListAdapter(cursorAdapter);
		
		newBookButton = (Button) findViewById(R.id.newBook);
		
		newBookButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BrowseBooksActivity.this, AddNewBookActivity.class);
				startActivityForResult(intent, ADD_NEW_BOOK);
			}
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		currentId = id;
		
		String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
		String author = cursor.getString(cursor.getColumnIndexOrThrow("Author"));
		String pages = cursor.getString(cursor.getColumnIndexOrThrow("Pages"));
		
		Intent intent = new Intent(BrowseBooksActivity.this, BookDetailsActivity.class);
		
		intent.putExtra("TITLE", title);
		intent.putExtra("AUTHOR", author);
		intent.putExtra("PAGES", pages);
		intent.putExtra("ID", id);
		
		startActivityForResult(intent, BOOK_DETAILS);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_NEW_BOOK) {
			if (resultCode == RESULT_OK) {
				Cursor newCursor = bookAdapter.getAllBooks();
				cursor = newCursor;
				cursorAdapter.changeCursor(newCursor);
			
				
				String title = data.getStringExtra(ADD_NEW_BOOK_RESULT);
				Context context = BrowseBooksActivity.this.getApplicationContext();
				CharSequence text = "Book added: " + title;
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
		
		if (requestCode == BOOK_DETAILS) {
			if (resultCode == RESULT_OK) {
				Cursor newCursor = bookAdapter.getAllBooks();
				cursor = newCursor;
				cursorAdapter.changeCursor(newCursor);
				
				String title = data.getStringExtra(DELETE_BOOK_RESULT);
				Context context = BrowseBooksActivity.this.getApplicationContext();
				CharSequence text = "Book deleted: " + title;
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
	}


}
