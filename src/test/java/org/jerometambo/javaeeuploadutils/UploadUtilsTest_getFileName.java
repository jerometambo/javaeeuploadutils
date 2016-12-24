package org.jerometambo.javaeeuploadutils;

import org.jerometambo.javaeeuploadutils.test.TestImplPart;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link UploadUtils#getFileName(javax.servlet.http.Part)}.
 */
public class UploadUtilsTest_getFileName {

    private final FilePathUtils filePathUtils = new FilePathUtils();
    private final UploadUtils uploadUtils = new UploadUtils();

    @Test
    public void testGetFileNameOk_Windows() {
        if (filePathUtils.isFileSystemWindows()) {
            test("form-data; name=\"valeur\"; filename=\"E:\\Users\\toto\\Desktop\\fichier.gif\"");
        }
    }

    @Test
    public void testGetFileNameOk_Linux() {
        if (!filePathUtils.isFileSystemWindows()) {
            test("form-data; name=\"valeur\"; filename=\"/e/Users/toto/Desktop/fichier.gif\"");
        }
    }

    protected void test(String metadata) {
        TestImplPart part = instancierPart(metadata);
        Assert.assertEquals("fichier.gif", uploadUtils.getFileName(part));
    }

    @Test
    public void testGetFileNameNok() {
        TestImplPart part = instancierPart("form-data; name=\"valeur\"");
        Assert.assertNull(uploadUtils.getFileName(part));
    }

    protected TestImplPart instancierPart(String content) {
        TestImplPart part = new TestImplPart();
        part.setHeader(uploadUtils.CONTENT_DISPOSITION, content);
        return part;
    }

}