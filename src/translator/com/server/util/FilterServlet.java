package translator.com.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FilterServlet implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Processing Request");

//		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(baos);
		
		HttpServletResponse wrappedResp = new HttpServletResponseWrapper(res){
			@Override
			public PrintWriter getWriter() throws IOException {
				return pw;
			}
			
			@Override
			public ServletOutputStream getOutputStream(){
				return new ServletOutputStream(){
					@Override
					public void write(int b) throws IOException {
						baos.write(b);
					}
				};
			}
		};

		chain.doFilter(request, wrappedResp);
//		pw.close();
//		baos.close();
		
		System.out.println("processing Response: " + new String(baos.toByteArray(), "UTF-8"));
		response.getOutputStream().write(baos.toByteArray());
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init");
	}
}
