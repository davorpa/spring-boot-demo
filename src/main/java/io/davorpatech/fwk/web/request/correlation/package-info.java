/**
 * In a distributed system architecture (microservice architecture), it is
 * highly difficult to understand a single end to end customer transaction
 * flow through the various components.
 *
 * <p>
 * Here are some the general challenges:
 *
 * <ul>
 * <li>It becomes challenging to understand the end-to-end behavior of a
 * client request entering the application.
 * <li>Aggregation: Consolidating logs from multiple components and making
 * sense out of these logs is difficult, if not impossible.
 * <li>Cyclic dependencies on services, course of events and asynchronous
 * requests are not easily deciphered.
 * <li>While troubleshooting a request, the diagnostic context of the logs
 * are very important to get to the root of the problem.
 * </ul>
 *
 *
 * <h2><a id="solution">Solution</a></h2>
 *
 * <p>
 * A Correlation ID is a unique identifier that is added to the very first
 * interaction (incoming request) to identify the context and is passed to
 * all components that are involved in the transaction flow.
 * Correlation ID becomes the glue that binds the transaction together and
 * helps to draw an overall picture of events.
 *
 * <p>
 * So basically, this Request Correlation feature allows to uniquely
 * identify and track your request by passing {@code X-Request-Id} and
 * {@code X-Session-Id} headers across remote calls.
 *
 * <ul>
 * <li>The Request Id is meant to track a single request across multiple
 * collaborating service calls.
 * <li>The Session Id is meant to track multiple requests made by a user
 * across an application.
 * </ul>
 *
 * <p>
 * For example, if a user logs into an application, requests an object from
 * the server, then saves the modified version of the object, the "find"
 * request and the "save" request will have different request ids, but the
 * same session id.
 *
 *
 * <h2><a id="usage">Usage</a></h2>
 *
 * <p>
 * To use this featured component, annotate every Spring Boot / Cloud
 * Application with {@code @EnableRequestCorrelation} annotation. That's it.
 *
 * <pre>{@code
 * @EnableRequestCorrelation
 * @SpringBootApplication
 * public class Application {
 *
 * }
 * }</pre>
 *
 * <p>
 * The annotation will auto register a
 * {@link io.davorpatech.fwk.web.request.correlation.RequestCorrelationFilter
 * servlet filter} that will process any inbound request and correlate it
 * with unique identifiers.
 *
 * <p>
 * Also, you can retrieve the current request ids programmatically within
 * any request bound thread using any method provided by
 * {@link io.davorpatech.fwk.web.request.correlation.RequestCorrelationUtils
 * RequestCorrelationUtils}.
 *
 *
 * <h2><a id="configuration">Configuration Options</a></h2>
 *
 * <p>
 * You can configure following options:
 *
 * <pre>{@code
 * ## sets the position in the filter chain for the Request Correlation Filter (Ordered.HIGHEST_PRECEDENCE by default)
 * request.correlation.filter-order=102
 * ## sets the starting position for the filter order. Defaults to "zero"
 * request.correlation.filter-order-from=highest_precedence
 * ## enables the session correlation identification (false by default)
 * request.correlation.enable-sessions=true
 * ## sets the header name to be used for request identification (X-Request-Id by default)
 * request.correlation.request-header-name=X-Request-Id
 * ## sets the header name to be used for session identification (X-Session-Id by default)
 * request.correlation.session-header-name=X-Session-Id
 *
 * ## enables the RestTemplate header propagation (true by default)
 * request.correlation.client.http.enabled=true
 * ## enables the Feign header propagation (true by default)
 * request.correlation.client.feign.enabled=true
 *
 * ## enables the MDC logging propagation (true by default)
 * request.correlation.logging.enabled=true
 * ## sets the MDC key to be used by logging context for request identification (httpRequestId by default)
 * request.correlation.logging.request-mdc-key=httpRequestId
 * ## sets the MDC key to be used by logging context for session identification (httpSessionId by default)
 * request.correlation.logging.session-mdc-key=httpSessionId
 * }</pre>
 *
 * <p>
 * Note that the above example shows a configuration that will put the
 * Request Correlation filter after the Spring Session filter (at highest
 * precedence + 102). Failing to set these values will result in the Request
 * Correlation filter getting the wrong request object, since the Spring
 * Session filter hasn't run yet, and the ids will be wrong.
 *
 *
 * <h2><a id="propagation">Propagation</a></h2>
 *
 * <p>
 * Besides that you will also have transparent integration with following:
 *
 * <ul>
 * <li>{@link org.springframework.web.client.RestTemplate RestTemplate}: any
 * Spring configured {@code RestTemplate} will be automatically populated
 * with the correlation ids.
 * <li><a href="https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/">Feign clients</a>:
 * similarly a request interceptor is being registered for Feign clients.
 * <li>Zuul proxy: any configured route will be also 'enriched' with the
 * identifiers.
 * <li>Mapped Diagnostic Context (MDC): any MDC logging context will be
 * populated with the identifiers.
 * </ul>
 *
 *
 * <h2><a id="use-cases">Use Cases</a></h2>
 *
 * <p>
 * The extension itself simply gives you means to propagate the information.
 * How are you going to use it is up to you?
 *
 * <p>
 * For instance, you can achieve that by registering
 * {@link io.davorpatech.fwk.web.request.correlation.RequestCorrelationInterceptor
 * RequestCorrelationInterceptor} bean. The {@literal RequestCorrelationInterceptor}
 * gives you only an entry point so that any following operation would be able to
 * access the correlation identifier.
 * You may also use Spring's {@link org.springframework.web.servlet.HandlerInterceptor
 * HandlerInterceptor} and set the value there.
 *
 * <h3><a id="log-correlation">Log Correlation</a></h3>
 *
 * <p>
 * Log correlation is the ability to track disparate events through different
 * parts of the application.
 * Having a Correlation ID provides more context making it easy to build rules
 * for reporting and analysis.
 *
 * <p>
 * For example, you can apply this information to your logging {@code MDC} map
 * using an interceptor like this:
 *
 * <pre>{@code
 * @Bean
 * public RequestCorrelationInterceptor correlationLoggingInterceptor() {
 *     return new RequestCorrelationInterceptor() {
 *         @Override
 *         public void afterCorrelationSet(final RequestCorrelation requestCorrelation)
 *         {
 *             MDC.put("httpSessionId", requestCorrelation.getSessionId());
 *             MDC.put("httpRequestId", requestCorrelation.getRequestId());
 *         }
 *
 *         @Override
 *         public void cleanUp(final RequestCorrelation requestCorrelation)
 *         {
 *             MDC.remove("httpSessionId");
 *             MDC.remove("httpRequestId");
 *         }
 *     };
 * }
 * }</pre>
 *
 * <p>
 * or, for a zero-config experience, enable the {@code request.correlation.logging.enabled}
 * property (enabled by default). You can use it for example with logback
 * by overridden the {@code logging.pattern.level} property...
 *
 * <pre>{@code
 * request.correlation.logging.enabled=true
 * request.correlation.logging.request-mdc-key=correlationId
 * logging.pattern.level=%5p [correlationId:%X{correlationId:-}]
 * }</pre>
 *
 * you will get the following log
 *
 * <pre>{@code
 * 2023-05-29 07:57:48.009  INFO [correlationId:674f1042-a767-4d64-a209-9f0ab1753a78] 31162 --- [nio-8888-exec-1] ...
 * }</pre>
 *
 * <h3><a id="troubleshooting-errors">Troubleshooting Errors</a></h3>
 *
 * <p>
 * For troubleshooting an errors, Correlation ID is a great starting point
 * to trace the workflow of a transaction.
 *
 * If you are using {@literal Vnd.errors} you can use that as your log ref
 * value with something like this:
 *
 * <pre>{@code
 * @ExceptionHandler
 * public ResponseEntity error(Exception ex) {
 *     final VndError vndError = new VndError(
 *             RequestCorrelationUtils.getCurrentRequestId(),
 *             ex.getMessage());
 *
 *     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
 *             .header(HttpHeaders.CONTENT_TYPE, "application/vnd.error+json")
 *             .body(vndError);
 * }
 * }</pre>
 *
 * <h3><a id="observability">Secondary reporting/observer systems</a></h3>
 *
 * <p>
 * Using Correlation ID helps secondary systems to correlate data without
 * application context.
 *
 * <p>
 * Some examples - generating metrics based on tracing data, integrating
 * runtime/system diagnostics etc. For example, feeding AppInsights data
 * and correlating it to infrastructure issues.
 *
 * <p>
 * To get that, you can use Spring Boot Actuator's audits when you implement
 * a custom {@code AuditEventRepository}.
 */
package io.davorpatech.fwk.web.request.correlation;
