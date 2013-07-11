package jp.fkmsoft.android.lib.v4;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestFileDialogUtil {
    @Test
    public void test_0000_srcFileList() {
        File srcFolder = new File("src/main/java/jp/fkmsoft/android/lib/v4");
        File parent = new File("src/main/java/jp/fkmsoft/android/lib");
        File[] files = srcFolder.listFiles();
        
        List<File> fileForList = FileDialogUtil.getFiles(srcFolder);
        Assert.assertEquals(files.length + 1, fileForList.size());
        Assert.assertEquals(parent.getAbsolutePath(), fileForList.get(0).getAbsolutePath());
    }
    
    @Test
    public void test_0001_root() {
        File srcFolder = new File("/");
        File[] files = srcFolder.listFiles();
        
        List<File> fileForList = FileDialogUtil.getFiles(srcFolder);
        Assert.assertEquals(files.length, fileForList.size());
    }
}
