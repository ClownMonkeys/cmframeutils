package dataAdapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cm.cmframeutils.R;

import java.util.ArrayList;

import logutils.LogUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：DataAdapter
 * FileName：DataAdapter.java
 * Date：2015/8/25 03
 * Author：大鹏
 * ClassName:DataAdapter
 **/
public class DataAdapter<T> extends BaseAdapter {
    public static final int TYPEMODEONE = 0;
    public static final int TYPEMODETWO = 1;
    public static final int TYPEMODETHREE = 2;

    private ArrayList<T> mlist;
    private Context mContext;

    LayoutInflater inflater;

    public DataAdapter(Context mContext, ArrayList<T> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mlist == null ? 0 : mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist == null ? null : mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int index = position % 3;
        if (index == 0) {
            return TYPEMODEONE;
        } else if (index <= 1) {
            return TYPEMODETWO;
        } else if (index <= 2) {
            return TYPEMODETHREE;
        } else {
            return TYPEMODEONE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolerOne viewHolerOne = null;
        ViewHolerTwo viewHolerTwo = null;
        ViewHolerThree viewHolerThree = null;
        int type = getItemViewType(position);
        LogUtils.e("this is Position value : "+position);
        if (convertView == null) {
            switch (type) {
                case TYPEMODEONE:
                    convertView = inflater.inflate(R.layout.item_one_layout, parent, false);
                    viewHolerOne = new ViewHolerOne();
                    viewHolerOne.image_one = (ImageView) convertView.findViewById(R.id.image_one);
                    convertView.setTag(viewHolerOne);
                    break;
                case TYPEMODETWO:
                    convertView = inflater.inflate(R.layout.item_two_layout, parent, false);
                    viewHolerTwo = new ViewHolerTwo();
                    viewHolerTwo.item_two_text = (TextView) convertView.findViewById(R.id.item_text2);
                    convertView.setTag(viewHolerTwo);
                    break;
                case TYPEMODETHREE:
                    convertView = inflater.inflate(R.layout.item_three_layout, parent, false);
                    viewHolerThree = new ViewHolerThree();
                    viewHolerThree.item_three_text = (TextView) convertView.findViewById(R.id.item_text3);
                    convertView.setTag(viewHolerThree);
                    break;
            }
        } else {
            switch (type) {
                case TYPEMODEONE:
                    viewHolerOne = (ViewHolerOne) convertView.getTag();
                    break;
                case TYPEMODETWO:
                    viewHolerTwo = (ViewHolerTwo) convertView.getTag();
                    break;
                case TYPEMODETHREE:
                    viewHolerThree = (ViewHolerThree) convertView.getTag();
                    break;
            }
        }
        return convertView;
    }

    public class ViewHolerOne {
        ImageView image_one;
    }

    public class ViewHolerTwo {
        TextView item_two_text;
    }

    public class ViewHolerThree {
        TextView item_three_text;
    }

    public static class ViewHolder {
        // I added a generic return type to reduce the casting noise in client code
        public static <T extends View> T getView(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
