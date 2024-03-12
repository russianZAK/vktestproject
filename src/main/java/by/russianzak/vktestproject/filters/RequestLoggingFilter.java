package by.russianzak.vktestproject.filters;

import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import by.russianzak.vktestproject.audit.RequestAudit;
import by.russianzak.vktestproject.audit.RequestAuditLogServiceImpl;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

  private final RequestAuditLogServiceImpl auditLogService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    filterChain.doFilter(request, response);

    RequestAudit audit = new RequestAudit();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    LocalDateTime currentTime = LocalDateTime.now();

    Map<String, String[]> parameterMap = request.getParameterMap();
    List<String> parameters = new ArrayList<>();
    for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
      String key = entry.getKey();
      String value = StringUtils.arrayToCommaDelimitedString(entry.getValue());
      parameters.add(key + "=" + value);
    }

    @SuppressWarnings("unchecked")
    Map<String, String> pathVariables = (Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
    List<String> pathVariablesList = new ArrayList<>();
    if (pathVariables != null && !pathVariables.isEmpty()) {
      for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
        pathVariablesList.add(entry.getKey() + "=" + entry.getValue());
      }
    }

    int httpStatusCode = response.getStatus();
    audit.setTimeStamp(LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    audit.setUsername(authentication != null ? authentication.getName() : "anonymous");
    audit.setIsAuthenticated(authentication != null && authentication.isAuthenticated());
    audit.setRequestURL(request.getRequestURI());
    audit.setRequestParams(parameters);
    audit.setPathVariables(pathVariablesList);
    audit.setHTTPMethod(request.getMethod());
    audit.setAccess(httpStatusCode == HttpServletResponse.SC_FORBIDDEN ? "Denied" : "Allowed");
    auditLogService.saveAuditLog(audit);
  }
}

