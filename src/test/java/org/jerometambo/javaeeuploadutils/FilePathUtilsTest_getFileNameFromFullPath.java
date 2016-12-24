package org.jerometambo.javaeeuploadutils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link FilePathUtils#getFileNameFromFullPath(String)}.
 */
public class FilePathUtilsTest_getFileNameFromFullPath {

    private final FilePathUtils filePathUtils = new FilePathUtils();

    @Test
    public void testWindows() {
        if (filePathUtils.isFileSystemWindows()) {
            test("D:\\dossier\\dossier2\\lefichier.txt");
        }
    }

    @Test
    public void testLinux() {
        if (!filePathUtils.isFileSystemWindows()) {
            test("/d/dossier/dossier2/lefichier.txt");
        }
    }

    protected void test(String path) {
        Assert.assertEquals("lefichier.txt", filePathUtils.getFileNameFromFullPath(path));
    }

}