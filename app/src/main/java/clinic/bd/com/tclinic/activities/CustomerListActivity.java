package clinic.bd.com.tclinic.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import clinic.bd.com.tclinic.R;
import clinic.bd.com.tclinic.adapter.CustomerListAdapter;
import clinic.bd.com.tclinic.bean.CustomerBean;
import clinic.bd.com.tclinic.view.FooterView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class CustomerListActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private ListView customerListView;
    private List<CustomerBean> customerList = new ArrayList<CustomerBean>();
    private CustomerListAdapter adapter;
    private FooterView footerView;
    private static final int PAGE_LIMIT = 20;
    private int curLoadedNum = 0;

    private int visibleLastIndex = 0;   //最后的可视项索引
    private boolean needToQueryMore = true;
    private View loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        customerListView = (ListView) findViewById(R.id.customer_list);
        adapter = new CustomerListAdapter(customerList, this);
        customerListView.setAdapter(adapter);

        View headerView = this.getLayoutInflater().inflate(R.layout.customer_list_header, null);

        TextView textView = (TextView) headerView.findViewById(R.id.headerTxt);
        textView.setText("Customer Info");
        customerListView.addHeaderView(headerView);
        headerView.setTag("NoClick");

        footerView = new FooterView(this);
        customerListView.addFooterView(footerView);
        footerView.showMoreText();
        footerView.setTag("NoClick");

        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() != null && view.getTag().equals("NoClick")) {
                    return;
                }
                CustomerBean customerBean = (CustomerBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.setClass(CustomerListActivity.this, CustomerModifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", customerBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        customerListView.setOnScrollListener(this);

        loadingPage = findViewById(R.id.page_loading);
    }

    @Override
    protected void onResume() {
        super.onResume();

        needToQueryMore = true;

        loadingPage.setVisibility(View.VISIBLE);

        queryListData();
    }

    private void queryListData() {
        BmobQuery<CustomerBean> query = new BmobQuery<CustomerBean>();
        //查询playerName叫“比目”的数据
//        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(PAGE_LIMIT);
        query.setSkip(curLoadedNum);
        //执行查询方法
        query.findObjects(this, new FindListener<CustomerBean>() {
            @Override
            public void onSuccess(List<CustomerBean> object) {
                Log.d("Clinic", "Total Data = " + object.size());
                customerList.addAll(object);
                adapter.notifyDataSetChanged();

                if (curLoadedNum > 0) {
                    customerListView.setSelection(visibleLastIndex);
                }

                curLoadedNum += object.size();

                if (object.size() < PAGE_LIMIT) {
                    footerView.showEnd();
                    needToQueryMore = false;
                } else {
                    footerView.showMoreText();
                }

                loadingPage.setVisibility(View.GONE);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && visibleLastIndex == view.getAdapter().getCount() && needToQueryMore)
        {
            footerView.showProgressBar();
            queryListData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        Log.e("Clinic", "firstVisiableItem = "
//                + firstVisibleItem + " visiableItemCount = "
//                + visibleItemCount + " totalItemCount ="
//                + totalItemCount + " adapterCount = " + view.getAdapter().getCount());
        visibleLastIndex = firstVisibleItem + visibleItemCount;
    }
}
