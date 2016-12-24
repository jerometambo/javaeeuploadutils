package org.jerometambo.javaeeuploadutils.test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.Part;

/**
 * Impl�mentation de test.
 */
public class TestImplPart implements Part {

  private Map<String, Collection<String>> headers = new HashMap<>();
  private String name;

  @Override
  public InputStream getInputStream() throws IOException {
    return null;
  }

  @Override
  public String getContentType() {
    return null;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getSubmittedFileName() {
    return null;
  }

  @Override
  public long getSize() {
    return 0;
  }

  @Override
  public void write(String fileName) throws IOException {

  }

  @Override
  public void delete() throws IOException {

  }

  @Override
  public String getHeader(String name) {
    return headers.get(name).stream().findFirst().get();
  }

  public void setHeader(String name, String value) {
    headers.put(name, Collections.singletonList(value));
  }

  @Override
  public Collection<String> getHeaders(String name) {
    return headers.get(name);
  }

  @Override
  public Collection<String> getHeaderNames() {
    return headers.keySet().stream().collect(Collectors.toList());
  }

}