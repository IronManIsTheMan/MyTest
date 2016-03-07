package clinic.bd.com.tclinic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import clinic.bd.com.tclinic.R;
import cn.bmob.v3.Bmob;


public class MainActivity extends Activity {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "bb232aa97e59bbc0b6d8dc5d663701ec");

        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CustomerListActivity.class);
                startActivity(intent);
            }
        });
    }
}
