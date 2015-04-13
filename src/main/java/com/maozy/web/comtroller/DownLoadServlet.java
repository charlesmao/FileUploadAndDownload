package com.maozy.web.comtroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DownLoadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//得到要下载的文件名
		String fileName = request.getParameter("filename"); //23239283-92489-阿凡达.avi
		fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
		//上传的文件都保存在/WEB-INFO/upload目录下的子目录当中 
		String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		//通过文件名找出文件所在目录
		String path = fileFileSavePathByFileName(fileName, fileSaveRootPath);
		//得到要下载的文件
		File file = new File (path + "\\" + fileName);
		//如果文件不存在
		if (!file.exists()) {
			request.setAttribute("message", "您要下载的资源已被删除！！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		//处理文件名
		String realName = fileName.substring(fileName.indexOf("_") + 1);
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(path + "\\" + fileName);
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区中
		while ((len = in.read(buffer)) > 0) {
			//输出缓冲区中的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		
		//关闭文件输入流
		in.close();
		//关闭输出流
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	/**
	 * 
	 * @param fileName 要下载的文件名
	 * @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
	 * @return 要下载的文件的存储目录
	 */
	private String fileFileSavePathByFileName(String fileName, String saveRootPath) {
		int hashcode = fileName.hashCode();
		int dir1 = hashcode&0xf; //0--15
		int dir2 = (hashcode&0xf0) >> 4; //0-15
		String dir = saveRootPath + "\\" + dir1 + "\\" + dir2; //upload\2\3  upload\3\5
		File file = new File(dir);
		if (!file.exists()) {
			//创建目录
			file.mkdirs();
		}
		
		return dir;
	}

}
