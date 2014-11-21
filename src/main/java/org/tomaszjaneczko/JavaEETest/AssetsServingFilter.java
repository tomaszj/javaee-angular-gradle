package org.tomaszjaneczko.JavaEETest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter used to render main index.html in servlet set up in /webapp folder.
 *
 * All requests that don't fetch .js, .css or images, go to the same file,
 * which is the entry point to single-page applications.
 *
 * Assumption here is that assets servlet is set up correctly and already
 * lets through all API requests to respective Resources.
 *
 * Filter is set up with @WebFilter annotation
 */
@WebFilter(filterName = "AssetsServingFilter", urlPatterns = "/*")
public class AssetsServingFilter implements Filter {

    /**
     * List of prefixes that should be let through.
     */
    public static final List<String> ALLOWED_PREFIXES = Arrays.asList(
            "/api/",
            "/js/",
            "/css/",
            "/img/"
    );

    /**
     * List of full paths that should be let through, including the root.
     */
    public static final List<String> ALLOWED_PATHS = Arrays.asList(
            "/favicon.ico",
            "/"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (matchesAllowedPaths(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            // Request should be forwarded to the root.
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    public boolean matchesAllowedPaths(String path) {
         return ALLOWED_PREFIXES.stream().anyMatch(path::startsWith)
            || ALLOWED_PATHS.stream().anyMatch(path::equals);
    }
}
