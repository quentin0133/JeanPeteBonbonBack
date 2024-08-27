package fr.dawan.jeanpetebonbon.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                  @NotNull FilterChain filterChain) throws IOException {
    try {
      filterChain.doFilter(request, response);
    }
    catch (Exception ex) {
      String errorMessage = "%s (error catched in ExceptionHandlerFilter)".formatted(ex.getMessage());
      Map<String, Object> res = new LinkedHashMap<>();
      res.put("errors", HttpStatus.UNAUTHORIZED);
      res.put("status", errorMessage);

      String jsonString = new ObjectMapper().writeValueAsString(res);

      System.err.println(errorMessage);

      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getOutputStream().println(jsonString);
    }
  }
}
