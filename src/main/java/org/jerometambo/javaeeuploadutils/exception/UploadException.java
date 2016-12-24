package org.jerometambo.javaeeuploadutils.exception;

/**
 * @author jerometambo Exception thrown while dealing with file uploads.
 */
public class UploadException extends Exception {

    public UploadException(Exception e) {
        super(e);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }

}