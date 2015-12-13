package com.qioixiy.bisheecu;

import android.os.Bundle;
import com.qioixiy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	public static final String TAG = "LoginActivity";
	private Button httpsButton;
	private HttpsAsyncTask httpsTask;
	private Handler handler;
	private TextView debugView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		debugView = (TextView) findViewById(R.id.debugView);
		httpsButton = (Button) findViewById(R.id.login);
		httpsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runHttpsConnection();
			}
		});

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					debugView.setText(debugView.getText()
							+ msg.getData().getString("html") + "\n");
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					String message = "test";
					intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
					startActivity(intent);
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	private void runHttpsConnection() {
		if (httpsTask == null || httpsTask.getStatus() == Status.FINISHED) {
			httpsTask = new HttpsAsyncTask(handler);
			EditText username = (EditText) findViewById(R.id.accountEdittext);
			EditText password = (EditText) findViewById(R.id.pwdEdittext);

			String params0 = username.getText().toString();
			String params1 = password.getText().toString();
			Log.d(TAG, params0);
			Log.d(TAG, params1);
			httpsTask.execute(params0, params1);
		}
	}
}