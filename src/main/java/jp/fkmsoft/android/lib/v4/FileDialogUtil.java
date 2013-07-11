package jp.fkmsoft.android.lib.v4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FileDialogUtil {
    static List<File> getFiles(File folder) {
        File[] listFiles = folder.listFiles();
        if (listFiles == null) { 
            listFiles = new File[0];
        }
        
        List<File> result;
        
        File parent = folder.getParentFile();
        if (parent == null) {
            result = new ArrayList<File>(listFiles.length);
        } else {
            result = new ArrayList<File>(listFiles.length + 1);
            result.add(parent);
        }
        
        for (File f : listFiles) {
            result.add(f);
        }
        return result;
    }
}
