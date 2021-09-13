package com.example.routingandfilteringgateway.filters.pre;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Now you can see how to filter requests through your proxy service.
 *
 * Zuul has (4) four standard filter types:
 *     1. pre filters run before the request is routed.
 *     2. route filters can handle the actual routing of the request.
 *     3. post filters run after the request has been routed.
 *     4. error filters run if an error occurs in the course of handling the request.
 *
 * You are going to write a pre filter.
 * Spring Cloud Netflix picks up, as a filter,
 * any @Bean that extends com.netflix.zuul.ZuulFilter
 * and is available in the application context.
 *
 *
 */
public class SimpleFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

    // ZuulFilter classes implement (4) four methods:

    // 1. filterType(): Returns a String that stands for the type of the filter — in this case, pre. (It would be route for a routing filter.)
    @Override
    public String filterType() {
      return "pre";
    }

    // 2. filterOrder(): Gives the order in which this filter is to be run, relative to other filters.
    @Override
    public int filterOrder() {
      return 1;
    }

    // 3. shouldFilter(): Contains the logic that determines when to run this filter (this particular filter is always run).
    @Override
    public boolean shouldFilter() {
      return true;
    }

    // 4. run(): Contains the functionality of the filter.
    @Override
    public Object run() {
      /*
      Zuul filters store request and state information in
       (and share it by means of) the RequestContext.
       */
      RequestContext ctx = RequestContext.getCurrentContext();

      // You can use that to get at the `HttpServletRequest`.
      HttpServletRequest request = ctx.getRequest();

      // then log the HTTP method and URL of the request before it is sent on its way.
      log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

      return null;
    }

}
