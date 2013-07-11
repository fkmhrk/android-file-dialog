package jp.fkmsoft.android.lib.v4;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * This dialog provides file selecting feature. 
 * @author fkm
 *
 */
public class OpenFileDialogFragment extends DialogFragment {
    
    public interface OnOpenFileSelectListener {
        /**
         * This method will be called when user selects the file
         * @param file the selected file
         */
        void onOpenFileSelected(File file);
    }
    
    private static final String ARGS_BASE = "base";
    
    private static final int ID_PATH = 1000;
    private static final int ID_LIST = 1001;

    /**
     * Creates a new DialogFragment
     * @param target a caller fragment, which must implement {@link OnOpenFileSelectListener}
     * @param baseFolder a base folder. this folder will be shown at the first list.
     * @return A DialogFragment
     */
    public static <T extends Fragment & OnOpenFileSelectListener> 
            OpenFileDialogFragment newInstance(T target, File baseFolder) {
        OpenFileDialogFragment fragment = new OpenFileDialogFragment();
        fragment.setTargetFragment(target, 0);

        Bundle args = new Bundle();
        args.putString(ARGS_BASE, baseFolder.getAbsolutePath());
        fragment.setArguments(args);
        return fragment;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String rootFolder = args.getString(ARGS_BASE);
        File folder = new File(rootFolder);
        
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(createView());
        
        refreshList(dialog, folder);
        
        return dialog;
    }

    private View createView() {
        Context context = getActivity();
        
        RelativeLayout layout = new RelativeLayout(context);
        
        TextView pathText = new TextView(context);
        pathText.setSingleLine();
        pathText.setFocusableInTouchMode(true);
        pathText.setId(ID_PATH);
        pathText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        pathText.setEllipsize(TruncateAt.START);
        layout.addView(pathText);
        
        ListView list = new ListView(context);
        list.setId(ID_LIST);
        list.setAdapter(new FileListAdapter(context));
        
        list.setOnItemClickListener(listener);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, ID_PATH);
        layout.addView(list, params);
        
        return layout;
    }
    
    private void refreshList(Dialog dialog, File folder) {
        // set path
        TextView pathText = (TextView) dialog.findViewById(ID_PATH);
        pathText.setText(folder.getAbsolutePath());
        
        ListView list = (ListView) dialog.findViewById(ID_LIST);
        FileListAdapter adapter = (FileListAdapter) list.getAdapter();
        
        List<File> files = FileDialogUtil.getFiles(folder);
        adapter.clear();
        adapter.setHasParent(folder.getParentFile() != null);
        for (File f : files) {
            adapter.add(f);
        }
        
        adapter.notifyDataSetChanged();
    }
    
    private OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> list, View view, int position, long id) {
            File file = (File) list.getItemAtPosition(position);
            if (file.isFile()) {
                OnOpenFileSelectListener target = (OnOpenFileSelectListener) getTargetFragment();
                dismiss();
                target.onOpenFileSelected(file);
                return;
            }
            Dialog dialog = getDialog();
            refreshList(dialog, file);
        }
    };
    
    
}
