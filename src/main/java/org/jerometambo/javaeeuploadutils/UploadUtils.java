package org.jerometambo.javaeeuploadutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jerometambo.javaeeuploadutils.exception.UploadException;

/**
 * @author jerometambo <br>
 *         Utils to deal with Java EE file uploads.
 */
public class UploadUtils {

    public final String CONTENT_DISPOSITION = "content-disposition";
    private final Logger logger = LogManager.getLogger("UploadUtils");
    private final FilePathUtils filePathUtils = new FilePathUtils();

    /**
     * @return the file {@link Part}.
     * @throws UploadException
     */
    public Part getFilePart(HttpServletRequest req, String attribute) throws UploadException {
        try {
            return hasParts(req) ? retrieveFilePart(req, attribute) : null;
        } catch (Exception e) {
            throw new UploadException("Problem occured while getting the file part: " + attribute, e);
        }
    }

    protected Part retrieveFilePart(HttpServletRequest req, String attribute) throws IOException, ServletException {
        List<Part> parts = req.getParts().stream().filter(part -> part.getSubmittedFileName() != null
                && part.getContentType() != null && attribute.equals(part.getName())).collect(Collectors.toList());
        if (parts.size() > 1) {
            logger.warn("More than one filepart found for: " + attribute);
        }
        return parts.get(0);
    }

    /**
     * @return {@code true} is request contains {@link Part}, {@code false}
     *         otherwise.
     */
    public boolean hasParts(HttpServletRequest req) {
        Collection<Part> parts = null;
        try {
            parts = req.getParts();
        } catch (IOException | ServletException e) {
            logger.debug("Technical problem occured while trying to parse the request parts", e);
        }
        return parts != null & !parts.isEmpty();
    }

    /**
     * @return first filename (fullpath) found in request if founded,
     *         {@code null} otherwise.
     */
    public String getFileName(final Part part) {
        final String partHeader = part.getHeader(CONTENT_DISPOSITION);
        String contentFilename = Arrays.asList(partHeader.split(";")).stream().map(header -> header.trim())
                .filter(header -> header.startsWith("filename")).findFirst().orElse(null);
        if (contentFilename == null) {
            return null;
        }
        return filePathUtils
                .getFileNameFromFullPath(filePathUtils.recupererFullPathDepuisContentFileName(contentFilename));
    }

    /**
     * @return {@code null} i there is not any {@link Part} regarding this
     *         attribute in request, the uploaded file otherwise.
     * @throws UploadException
     */
    public File generateFile(HttpServletRequest req, String attribute) throws UploadException {
        Part filePart = getFilePart(req, attribute);
        if (filePart == null) {
            return null;
        }
        String fileName = getFileName(filePart);
        if (fileName == null) {
            return null;
        }
        return readFile(attribute, filePart, new File(fileName));
    }

    protected File readFile(String attribute, Part filePart, File file) throws UploadException {
        try (OutputStream out = new FileOutputStream(file); InputStream filecontent = filePart.getInputStream();) {
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            throw new UploadException("Error while reading file " + attribute, e);
        }
        return file;
    }

}
