package com.app;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.view.View;
import android.content.Intent;
import com.app.showLoginUI;

public class MainActivity extends Activity {
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		
		
		showLoginUI.showLoginUI(this);
		
		
        
    }
	
}
