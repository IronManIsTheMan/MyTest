package clinic.bd.com.tclinic.activities;

import android.content.Intent;
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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;


public class CustomerModifyActivity extends BaseActivity {

    private EditText customerName;
    private EditText age;
    private EditText telNumber;
    private RadioGroup genderGroup;
    private EditText customerDesp;
    private CustomerBean customerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_customer);

        customerBean = (CustomerBean) this.getIntent().getSerializableExtra("customer");

        findViews();

        refreshViews(customerBean);

        Button btn = (Button) findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateAllFields()) {
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

                    customerBean.update(CustomerModifyActivity.this, customerBean.getObjectId(),
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(CustomerModifyActivity.this, "Customer Info is updated.", Toast.LENGTH_SHORT).show();

                                    Intent resultData = new Intent();
                                    Bundle bdle = new Bundle();
                                    bdle.putSerializable("bean", customerBean);
                                    resultData.putExtras(bdle);
                                    setResult(0, resultData);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(CustomerModifyActivity.this, "Fail to update Customer Info. Error = " + s, Toast.LENGTH_SHORT).show();
                                }
                            });
                    Log.d("Clinic", "Customer = " + customerBean);
                }
            }
        });

        BmobQuery<CustomerBean> query = new BmobQuery<CustomerBean>();
        query.getObject(this, customerBean.getObjectId(), new GetListener<CustomerBean>() {

            @Override
            public void onSuccess(CustomerBean object) {
                refreshViews(object);
            }

            @Override
            public void onFailure(int code, String arg0) {
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

    private void refreshViews(CustomerBean customerBean) {
        if (customerBean != null) {
            customerName.setText(customerBean.getName());
            age.setText(String.valueOf(customerBean.getAge()));
            telNumber.setText(customerBean.getTelNumber());
            customerDesp.setText(customerBean.getDesp());
            if (customerBean.getGender() == 0) {
                genderGroup.check(R.id.radioFemale);
            } else {
                genderGroup.check(R.id.radioMale);
            }
        }
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
