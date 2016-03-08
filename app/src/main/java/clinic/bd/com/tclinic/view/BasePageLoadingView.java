package clinic.bd.com.tclinic.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

import clinic.bd.com.tclinic.R;

public class BasePageLoadingView extends RelativeLayout {

	TextView textView;//小贴士
	
    public BasePageLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getUi(context);
    }

    public BasePageLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getUi(context);
    }

    public BasePageLoadingView(Context context) {
        super(context);
        getUi(context);
    }

    private void getUi(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_loading, this, true);
        textView = (TextView)view.findViewById(R.id.tips);
    }
}
