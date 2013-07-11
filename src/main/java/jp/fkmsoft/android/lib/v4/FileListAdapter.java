package jp.fkmsoft.android.lib.v4;

import java.io.File;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<File> {

    private boolean hasParent;
    
    public FileListAdapter(Context context) {
        super(context, 0);
    }

    public void setHasParent(boolean value) {
        hasParent = value;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = getContext();
            
            RelativeLayout layout = new RelativeLayout(context);
            TextView nameText = new TextView(context);
            nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            layout.addView(nameText);
            
            convertView = layout; 
            convertView.setTag(new ViewHolder(nameText));
        }
        File file = getItem(position);
        
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (position == 0 && hasParent) {
            holder.nameText.setText("..");
        } else {
            holder.nameText.setText(file.getName());
        }
        return convertView;
    }
    
    private static class ViewHolder {
        public TextView nameText;
        
        private ViewHolder(TextView nameText) {
            this.nameText = nameText;
        }
    }
    
    

}
