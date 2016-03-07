package clinic.bd.com.tclinic.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import clinic.bd.com.tclinic.R;
import clinic.bd.com.tclinic.bean.CustomerBean;
import cn.bmob.v3.listener.SaveListener;


public class CustomerAddActivity extends BaseActivity {

    private EditText customerName;
    private EditText age;
    private EditText telNumber;
    private RadioGroup genderGroup;
    private EditText customerDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);

        findViews();

        Button btn = (Button) findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateAllFields()) {
                    CustomerBean customerBean = new CustomerBean();
                    customerBean.setName(customerName.getText().toString());
                    customerBean.setAge(Integer.valueOf(age.getText().toString()));
                    if (genderGroup.getCheckedRadioButtonId() == R.id.radioFemale) {
                        customerBean.setGender(0);
                    } else {
                        customerBean.setGender(1);
                    }
                    customerBean.setTelNumber(telNumber.getText().toString());
                    if (TextUtils.isEmpty(customerDesp.getText())) {
                        customerBean.setDesp("Empty");
                    } else {
                        customerBean.setDesp(customerDesp.getText().toString());
                    }

                    customerBean.save(CustomerAddActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(CustomerAddActivity.this, "Customer Info is saved.", Toast.LENGTH_SHORT).show();
                            clearEditTextViews();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(CustomerAddActivity.this, "Fail to Customer Info. Error = " + s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("Clinic", "Customer = " + customerBean);
                }
            }
        });
    }

    private void findViews() {
        customerName = (EditText) findViewById(R.id.customer_name);
        age = (EditText) findViewById(R.id.customer_age);
        telNumber = (EditText) findViewById(R.id.customer_tel);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        customerDesp = (EditText) findViewById(R.id.customer_desp);
    }

    private void clearEditTextViews() {
        customerName.setText("");
        age.setText("");
        telNumber.setText("");
        customerDesp.setText("");

        customerName.requestFocus();
    }

    private boolean validateAllFields() {
        boolean result = validateField(customerName, "Customer Name cannot be empty.");
        result &= validateField(age, "Age cannot be empty.");
        result &= validateField(telNumber, "Phone Number cannot be empty.");
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
