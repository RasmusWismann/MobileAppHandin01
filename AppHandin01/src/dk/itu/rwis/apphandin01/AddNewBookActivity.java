package dk.itu.rwis.apphandin01;

import dk.itu.rwis.apphandin01.db.BookDAO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewBookActivity extends Activity {
	
	private Button cancelButton;
	private Button confirmButton;
	
	private EditText titleField;
	private EditText authorField;
	private EditText pagesField;
	
	private BookDAO bookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_book);
		
		cancelButton = (Button) findViewById(R.id.button_cancelNewBook);
		confirmButton = (Button) findViewById(R.id.button_addNewBook);
		titleField = (EditText) findViewById(R.id.editText_title);
		authorField = (EditText) findViewById(R.id.editText_author);
		pagesField = (EditText) findViewById(R.id.editText_pages);
		
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bookAdapter = new BookDAO(AddNewBookActivity.this);
				bookAdapter.open();
				
				String title = titleField.getText().toString();
				String author = authorField.getText().toString();
				
				int pages = 0;
				if (pagesField.getText().toString().isEmpty() || pagesField.getText().toString() == null) {
					pages = -1;
				}
				else {
					pages = Integer.parseInt(pagesField.getText().toString());
				}
				
				
				if (title.isEmpty() || title == null) {
					Context context = AddNewBookActivity.this.getApplicationContext();
					CharSequence text = "Enter a title";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else if (author.isEmpty() || author == null) {
					Context context = AddNewBookActivity.this.getApplicationContext();
					CharSequence text = "Enter an author";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else if (pages < 1) {
					Context context = AddNewBookActivity.this.getApplicationContext();
					CharSequence text = "Number of pages must be positive";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else {
					bookAdapter.addBook(title, author, pages);
					bookAdapter.close();
					Intent intent = new Intent();
					intent.putExtra(BrowseBooksActivity.ADD_NEW_BOOK_RESULT, title);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
		
		
	}

}
