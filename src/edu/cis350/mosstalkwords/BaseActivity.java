package edu.cis350.mosstalkwords;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseActivity extends Activity{

	Button login;

	EditText n;
	EditText e;
	//store name and email
	SharedPreferences userSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);

		n = (EditText)findViewById(R.id.baseName);
		e = (EditText)findViewById(R.id.baseEmail);

		userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);

		n.setText(userSettings.getString("Name", ""));
		e.setText(userSettings.getString("Email address for reports", ""));
	}

	public void login(View v) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = userSettings.edit();

		String name = n.getText().toString();
		String email = e.getText().toString();

		if(validName(name) && validEmailAdress(email))
		{
			editor.putString("userName", name);
			editor.putString("userEmail", email);
			editor.commit();

			//Intent userEntry = new Intent(this, WelcomeActivity.class);

			Intent gotoMainMenu = new Intent(this, WelcomeActivity.class);
			startActivity(gotoMainMenu);
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("Your input")
			.setMessage("Please enter a user name and a valid email address.")
			.setPositiveButton("OK", null)
			.show();
		}
	}
	
	private boolean validName(String name){
		if(name == null)
			return false;
		return !name.equals("");
	}
	
	private boolean validEmailAdress(String mail){
		if(mail == null)
			return false;
		return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
	}
}
