package fr.quentin.jeanpetebonbon.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Exception handler filter.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
  private static final StackWalker WALKER =
      StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

  private static StackWalker.StackFrame caller() {
    return WALKER.walk(stream ->
        stream
            .filter(frame -> !frame.getClassName().equals(ExceptionHandlerFilter.class.getName()))
            .findFirst()
            .orElseThrow()
    );
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws IOException {
    try {
      filterChain.doFilter(request, response);
    }
    catch (Exception ex) {
      Map<String, Object> res = new LinkedHashMap<>();
      res.put("errors", HttpStatus.UNAUTHORIZED.value());
      res.put("status", ex.getMessage());

      String jsonString = new ObjectMapper().writeValueAsString(res);

      StackWalker.StackFrame caller = caller();
      log.error("{} : Exception with {}", caller, ex.getMessage());

      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getOutputStream().println(jsonString);
    }
  }
}
