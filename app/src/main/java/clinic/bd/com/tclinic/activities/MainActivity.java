package clinic.bd.com.tclinic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import clinic.bd.com.tclinic.R;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends Activity {

    private Button loginBtn;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "");

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateAllFields()) {
                    BmobUser bu2 = new BmobUser();
                    bu2.setUsername(userName.getText().toString());
                    bu2.setPassword(password.getText().toString());
                    bu2.login(MainActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, CustomerListActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            Toast.makeText(MainActivity.this, "Login failure with Error = " + msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        BmobUser currentUser = BmobUser.getCurrentUser(this);
        if (currentUser != null) {
            Intent intent = new Intent();
            intent.setClass(this, CustomerListActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private boolean validateAllFields() {
        boolean result = validateField(userName, "Customer Name cannot be empty.");
        result &= validateField(password, "Age cannot be empty.");
        return result;
    }

    private boolean validateField(EditText editText, String errorMsg) {
        if (editText.getText().toString().trim().equals("")){
            editText.setError(errorMsg);
            return false;
        }
        return true;
    }
}
