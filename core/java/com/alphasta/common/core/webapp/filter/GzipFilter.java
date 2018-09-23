package com.alphasta.common.core.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.filter.ExportFilterUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 用于使用gzip压缩输出内容的filter. Filter that compresses output with gzip 
 * (assuming that browser supports gzip). Code from <a
 * href="http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html">
 * http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html</a>.
 * 
 * &copy; 2003 Jayson Falkner You may freely use the code both commercially and
 * non-commercially.
 * 
 * @author Matt Raible
 * 
 * @web.filter name="compressionFilter"
 */
public class GzipFilter extends OncePerRequestFilter {
  /**
   * Log
   */
  private static transient Log log = LogFactory.getLog(GzipFilter.class);

  /**
   * @see OncePerRequestFilter#doFilterInternal( HttpServletRequest,
   *      HttpServletResponse, FilterChain)
   */
  public void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    if (isGZIPSupported(request)) {
      if (log.isDebugEnabled()) {
        log.debug("GZIP supported, compressing response");
      }

      GzipResponseWrapper wrappedResponse = new GzipResponseWrapper(response);

      chain.doFilter(request, wrappedResponse);
      wrappedResponse.finishResponse();

      return;
    }

    chain.doFilter(request, response);
  }

  /**
   * 测试是否支持GZIP filter
   * @param req The current user request
   * @return boolean indicating GZIP support
   */
  private boolean isGZIPSupported(HttpServletRequest req) {
    // disable gzip filter for exporting from ecside
    HttpServletRequestContext context = new HttpServletRequestContext(req);
    boolean isExported = ExportFilterUtils.isExported(context);
    if (isExported) {
      log.debug("detected excel export, disabling filter...");
      return false;
    }

    String browserEncodings = req.getHeader("accept-encoding");
    boolean supported = ((browserEncodings != null) && (browserEncodings
        .indexOf("gzip") != -1));

    String userAgent = req.getHeader("user-agent");

    if ((userAgent != null) && userAgent.startsWith("httpunit")) {
      log.debug("httpunit detected, disabling filter...");

      return false;
    } else {
      return supported;
    }
  }
}
