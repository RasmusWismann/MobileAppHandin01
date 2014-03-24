package dk.itu.rwis.apphandin01;

import dk.itu.rwis.apphandin01.db.BookDAO;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;



public class BookDetailsActivity extends Activity {

	private TextView titleValue;
	private TextView authorValue;
	private TextView pagesValue;
	
	private Button returnButton;
	private Button deleteButton;
	private BookDAO bookAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		
		titleValue = (TextView) findViewById(R.id.textView_details_titleValue);
		authorValue = (TextView) findViewById(R.id.textView_details_authorValue);
		pagesValue = (TextView) findViewById(R.id.textView_details_pagesValue);
		
		returnButton = (Button) findViewById(R.id.button_details_goBack);
		deleteButton = (Button) findViewById(R.id.button_details_delete);

		titleValue.setText(getIntent().getStringExtra("TITLE"));
		authorValue.setText(getIntent().getStringExtra("AUTHOR"));
		pagesValue.setText(getIntent().getStringExtra("PAGES"));
		
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bookAdapter = new BookDAO(BookDetailsActivity.this);
				bookAdapter.open();
				
				int id = (int) getIntent().getLongExtra("ID", -1);
				bookAdapter.deleteBook(id);
				
				Intent intent = new Intent();
				String title = titleValue.getText().toString();
				intent.putExtra(BrowseBooksActivity.DELETE_BOOK_RESULT, title);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
	}

}
