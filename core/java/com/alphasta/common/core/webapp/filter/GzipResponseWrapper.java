package com.alphasta.common.core.webapp.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Wraps Response for GZipFilter
 * 
 * @author Matt Raible, cmurphy@intechtual.com
 */
public class GzipResponseWrapper extends HttpServletResponseWrapper {
  /**
   * Log
   */
  private static Log log = LogFactory.getLog(GzipResponseWrapper.class);
  /**
   * Original Response
   */
  protected HttpServletResponse origResponse = null;
  /**
   * Output stream
   */
  protected ServletOutputStream stream = null;
  /**
   * writer
   */
  protected PrintWriter writer = null;
  /**
   * error code
   */
  protected int error = 0;
  /**
   * Constructor
   * @param response Original Response
   */
  public GzipResponseWrapper(HttpServletResponse response) {
    super(response);
    origResponse = response;
  }
  /**
   * create new instance of ServletOutputStream
   */
  public ServletOutputStream createOutputStream() throws IOException {
    return (new GzipResponseStream(origResponse));
  }
  /**
   * close all streams and writers
   */
  public void finishResponse() {
    try {
      if (writer != null) {
        writer.close();
      } else {
        if (stream != null) {
          stream.close();
        }
      }
    } catch (IOException e) {
    }
  }
  /**
   * flush stream
   * @see java.io.OutputStream#flush()
   */
  public void flushBuffer() throws IOException {
    if (stream != null) {
      stream.flush();
    }
  }
  /**
   * return the instance of ServletOutputStream，if not exists, create it.
   */
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException("getWriter() has already been called!");
    }

    if (stream == null) {
      stream = createOutputStream();
    }

    return (stream);
  }
  /**
   * return PrintWriter that decorate OutputStreamWriter
   */
  public PrintWriter getWriter() throws IOException {
    // From cmurphy@intechtual.com to fix:
    // https://appfuse.dev.java.net/issues/show_bug.cgi?id=59
    if (this.origResponse != null && this.origResponse.isCommitted()) {
      try {
        return super.getWriter();
      } catch (IllegalStateException e) {
        log.error("An error occurs when get writer." + e.getMessage());
      }
    }

    if (writer != null) {
      return (writer);
    }

    if (stream != null) {
      throw new IllegalStateException(
          "getOutputStream() has already been called!");
    }

    stream = createOutputStream();
    writer = new PrintWriter(new OutputStreamWriter(stream, origResponse
        .getCharacterEncoding()));

    return (writer);
  }
  /**
   * FIXME:Do nothing yet.
   */
  public void setContentLength(int length) {
    // noop
  }

  /**
   * @see javax.servlet.http.HttpServletResponse#sendError(int,
   *      java.lang.String)
   */
  @Override
  public void sendError(int errorCode, String message) throws IOException {
    super.sendError(errorCode, message);
    this.error = errorCode;

    if (log.isDebugEnabled()) {
      log.debug("sending error: " + error + " [" + message + "]");
    }
  }
}
