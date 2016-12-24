package org.jerometambo.javaeeuploadutils;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * @author jerometambo <br>
 *         Utils dealing with file paths and names.
 */
public class FilePathUtils {

    /**
     * @return filename from file full path.
     */
    public String getFileNameFromFullPath(String path) {
        return path != null && path.trim().isEmpty() ? null : genererateFileName(path);
    }

    protected String genererateFileName(String path) {
        if (isPathWindowsAndFileSystemNotWindows(path)) {
            return fileNameWithSeparator(path, "\\");
        } else if (isPathNotWindowsAndFileSystemWindows(path)) {
            return fileNameWithSeparator(path, "/");
        }
        return Paths.get(path).getFileName().toString();
    }

    protected String fileNameWithSeparator(String path, String separator) {
        return path.substring(path.lastIndexOf(separator) + 1, path.length());
    }

    /**
     * Unix client and Windows server.
     */
    private boolean isPathNotWindowsAndFileSystemWindows(String path) {
        return isFileSystemWindows() && !isWindowsPath(path);
    }

    /**
     * Windows client and Unix server.
     */
    protected boolean isPathWindowsAndFileSystemNotWindows(String path) {
        return !isFileSystemWindows() && isWindowsPath(path);
    }

    /**
     * @return {@code true} if it represents a Window path, {@code false}
     *         otherwise.
     */
    public boolean isWindowsPath(String path) {
        return path.contains(":") && path.contains("\\");
    }

    /**
     * @return {@code true} if the FS is Windows type, {@code false} otherwise.
     */
    public boolean isFileSystemWindows() {
        return FileSystems.getDefault().getClass().getName().contains("Windows");
    }

    protected String recupererFullPathDepuisContentFileName(String path) {
        return path.substring("filename=\"".length(), path.length() - 1);
    }
}
