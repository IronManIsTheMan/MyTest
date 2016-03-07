package clinic.bd.com.tclinic.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import clinic.bd.com.tclinic.R;
import clinic.bd.com.tclinic.adapter.CustomerListAdapter;
import clinic.bd.com.tclinic.bean.CustomerBean;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class CustomerListActivity extends BaseActivity {

    private ListView customerListView;
    private List<CustomerBean> customerList = new ArrayList<CustomerBean>();
    private CustomerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        customerListView = (ListView) findViewById(R.id.customer_list);
        adapter = new CustomerListAdapter(customerList, this);
        customerListView.setAdapter(adapter);
        TextView textView = new TextView(this);
        textView.setText("Customer Info");
        customerListView.addHeaderView(textView);
        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerBean customerBean = (CustomerBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.setClass(CustomerListActivity.this, CustomerModifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", customerBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BmobQuery<CustomerBean> query = new BmobQuery<CustomerBean>();
        //查询playerName叫“比目”的数据
//        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(200);
        //执行查询方法
        query.findObjects(this, new FindListener<CustomerBean>() {
            @Override
            public void onSuccess(List<CustomerBean> object) {
                Log.d("Clinic", "Total Data = " + object.size());
                customerList.clear();
                customerList.addAll(object);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                Log.d("Clinic", "Fail to query. error = " + msg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent();
            intent.setClass(CustomerListActivity.this, CustomerAddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CustomerListActivity.this.finish();
                                }})
                    .setNegativeButton("No", null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
