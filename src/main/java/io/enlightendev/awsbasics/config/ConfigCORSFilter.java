package io.enlightendev.awsbasics.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConfigCORSFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigCORSFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        LOG.trace("Adding Access Control Response Headers for Cross Origin Resource Sharing(CORS)");

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, PATCH, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Authorization, User-Agent, Client-ID");

        //NOTE: if we dont define this angular code will not see these headers in header map.
        response.setHeader("Access-Control-Expose-Headers", "RequestID, ResponseID");

        chain.doFilter(req, res);

    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}