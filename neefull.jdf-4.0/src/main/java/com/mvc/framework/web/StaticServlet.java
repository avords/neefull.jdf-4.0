package com.mvc.framework.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * Handle static resources
 */
public class StaticServlet extends HttpServlet {
	private static final long MAX_AGE = 12 * 60 * 60 * 1000L/* milliseconds */;
    private static final String CACHE_CTRL = "public, max-age="  + (MAX_AGE / 1000L);


	/**
	 * @author pubx
	 */
	public static interface LookupResult {
		void respondGet(HttpServletResponse resp) throws IOException;
		void respondHead(HttpServletResponse resp);
		long getLastModified();
	}

	public static class Error implements LookupResult {
		private final int statusCode;
		private final String message;

		public Error(int statusCode, String message) {
			this.statusCode = statusCode;
			this.message = message;
		}

		public long getLastModified() {
			return -1;
		}

		public void respondGet(HttpServletResponse resp) throws IOException {
			resp.sendError(statusCode, message);
		}

		public void respondHead(HttpServletResponse resp) {
			throw new UnsupportedOperationException();
		}
	}

	public static class StaticFile implements LookupResult {
		private final long lastModified;
		private final String mimeType;
		private final int contentLength;
		private final boolean acceptsDeflate;
		private final URL url;

		public StaticFile(long lastModified, String mimeType, int contentLength, boolean acceptsDeflate, URL url) {
			this.lastModified = lastModified;
			this.mimeType = mimeType;
			this.contentLength = contentLength;
			this.acceptsDeflate = acceptsDeflate;
			this.url = url;
		}

		public long getLastModified() {
			return lastModified;
		}

		protected boolean willDeflate() {
			return acceptsDeflate && deflatable(mimeType) && contentLength >= DEFLATETHRESHOLD;
		}

		protected void setHeaders(HttpServletResponse resp) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(mimeType);
			resp.setHeader("Cache-Control", CACHE_CTRL);
			resp.setDateHeader("Expires", System.currentTimeMillis() + MAX_AGE);
			resp.setDateHeader("Last-Modified", lastModified);
			if (contentLength >= 0 && !willDeflate()) {
				resp.setContentLength(contentLength);
			}
		}

		public void respondGet(HttpServletResponse resp) throws IOException {
			setHeaders(resp);
			final OutputStream os;
			if (willDeflate()) {
				resp.setHeader("Content-Encoding", "gzip");
				os = new GZIPOutputStream(resp.getOutputStream(), BUFFER_SIZE);
			} else {
				os = resp.getOutputStream();
			}

			transferStreams(url.openStream(), os);
		}

		public void respondHead(HttpServletResponse resp) {
			if (willDeflate()) {
				throw new UnsupportedOperationException();
			}
			setHeaders(resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		lookup(req).respondGet(resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			lookup(req).respondHead(resp);
		} catch (UnsupportedOperationException e) {
			super.doHead(req, resp);
		}
	}

	@Override
	protected long getLastModified(HttpServletRequest req) {
		return lookup(req).getLastModified();
	}

	protected LookupResult lookup(HttpServletRequest req) {
		LookupResult r = (LookupResult) req.getAttribute("lookupResult");
		if (r == null) {
			r = lookupNoCache(req);
			req.setAttribute("lookupResult", r);
		}
		return r;
	}

	protected LookupResult lookupNoCache(HttpServletRequest req) {
		final String path = getPath(req);
		if (isForbidden(path)) {
			return new Error(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
		}
		final URL url;
		try {
			url = getServletContext().getResource(path);
		} catch (MalformedURLException e) {
			return new Error(HttpServletResponse.SC_BAD_REQUEST, "Malformed path");
		}
		if (url == null) {
			return new Error(HttpServletResponse.SC_NOT_FOUND, "Not found");
		}
		final String mimeType = getMimeType(path);
		final String realpath = getServletContext().getRealPath(path);
		if (realpath != null) {
			// Try as an ordinary file
			File f = new File(realpath);
			if (!f.isFile()) {
				return new Error(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
			} else {
				return new StaticFile(f.lastModified(), mimeType, (int) f.length(), acceptsDeflate(req), url);
			}
		} else {
			try {
				// Try as a JAR Entry
				final ZipEntry ze = ((JarURLConnection) url.openConnection()).getJarEntry();
				if (ze != null) {
					if (ze.isDirectory()) {
						return new Error(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
					} else {
						return new StaticFile(ze.getTime(), mimeType, (int) ze.getSize(), acceptsDeflate(req), url);
					}
				} else {
					return new StaticFile(-1, mimeType, -1, acceptsDeflate(req), url);
				}
			} catch (ClassCastException e) {
				return new StaticFile(-1, mimeType, -1, acceptsDeflate(req), url);
			} catch (IOException e) {
				return new Error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
			}
		}
	}

	protected String getPath(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		String pathInfo = ServletUtils.coalesce(req.getPathInfo(), "");
		return servletPath + pathInfo;
	}

	protected boolean isForbidden(String path) {
		String lpath = path.toLowerCase();
		return lpath.startsWith("/web-inf/") || lpath.startsWith("/meta-inf/");
	}

	protected String getMimeType(String path) {
		return ServletUtils.coalesce(getServletContext().getMimeType(path), "application/octet-stream");
	}

	protected static boolean acceptsDeflate(HttpServletRequest req) {
		final String ae = req.getHeader("Accept-Encoding");
		return ae != null && ae.contains("gzip");
	}

	protected static boolean deflatable(String mimetype) {
		return mimetype.startsWith("text/") || mimetype.equals("application/postscript")
		        || mimetype.startsWith("application/ms") || mimetype.startsWith("application/vnd")
		        || mimetype.endsWith("xml");
	}

	private static final int DEFLATETHRESHOLD = 4 * 1024;

	private static final int BUFFER_SIZE = 4 * 1024;

	protected static void transferStreams(InputStream is, OutputStream os) throws IOException {
		try {
			byte[] buf = new byte[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				os.write(buf, 0, bytesRead);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	static final class ServletUtils {
		private ServletUtils() {
		}

		public static <T> T coalesce(T... ts) {
			for (T t : ts) {
				if (t != null) {
					return t;
				}
			}
			return null;
		}
	}
}
