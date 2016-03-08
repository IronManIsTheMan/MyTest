package clinic.bd.com.tclinic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import clinic.bd.com.tclinic.R;


public class FooterView extends LinearLayout {

    private View view;

    public FooterView(Context context) {
        super(context);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.list_footer, this);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    public void showMoreText() {
        view.findViewById(R.id.moreTxt).setVisibility(View.VISIBLE);
        view.findViewById(R.id.endTxt).setVisibility(View.GONE);
        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        view.findViewById(R.id.tipTextView).setVisibility(View.GONE);
    }

    public void showProgressBar() {
        view.findViewById(R.id.endTxt).setVisibility(View.GONE);
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.tipTextView).setVisibility(View.VISIBLE);
        view.findViewById(R.id.moreTxt).setVisibility(View.GONE);
    }

    public void showEnd() {
        view.findViewById(R.id.endTxt).setVisibility(View.VISIBLE);
        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        view.findViewById(R.id.tipTextView).setVisibility(View.GONE);
        view.findViewById(R.id.moreTxt).setVisibility(View.GONE);
    }
}
