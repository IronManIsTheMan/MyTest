package clinic.bd.com.tclinic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import clinic.bd.com.tclinic.R;
import clinic.bd.com.tclinic.bean.CustomerBean;

public class CustomerListAdapter extends BaseAdapter {

    private List<CustomerBean> customerBeans;
    private Context context;

    public CustomerListAdapter(List<CustomerBean> customerBeans, Context context) {
        this.customerBeans = customerBeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return customerBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return customerBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.customer_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.phoneNum = (TextView) convertView.findViewById(R.id.phoneNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(customerBeans.get(position).getName());
        viewHolder.phoneNum.setText(customerBeans.get(position).getTelNumber());

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView phoneNum;
    }
}
