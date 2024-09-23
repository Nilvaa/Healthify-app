package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.Vector;
import java.util.Iterator;
import Connection.dbconnection;

public final class server_005fcontroller_005fhealthify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

  dbconnection db = new dbconnection();
   String key = request.getParameter("requestType").trim();
   System.out.print(key);
   
//////////////////////////////////////////////////////////////////////////////USER SECTION/////////////////////////////////////////////////////////////////////

//USER REGISTER

 if (key.equals("User_reg")) {
        String un=request.getParameter("name");
        String e = request.getParameter("email");
        String ph = request.getParameter("phn");
        String pa = request.getParameter("passwd");
        String d = request.getParameter("dob");
         String l=request.getParameter("age");
        String a = request.getParameter("hei");
        String h = request.getParameter("wei");
        String de = request.getParameter("des");
        String ex = request.getParameter("gender");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM user_reg WHERE `email`='"+e+"' OR `phone`='"+ph+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);
             if (max_vid == 0) {
                String sq = "INSERT into user_reg(name,email,phone,pass,dob,age,height,weight,gender,des,pic)values('"+un+"','"+e+"','" + ph + "','" + pa + "','" + d + "','" + l + "','" + a + "','" + h + "','" + ex + "','" + de + "','" + i + "')";
                 String sq1 = "INSERT into login(u_id,uname,pass,type,status)values((select max(r_id) from user_reg),'" + e + "','" + pa + "','user','1')";
                System.out.println(sq + "\n" + sq1);

                if (db.putData(sq) > 0 && db.putData(sq1) > 0) {

                      System.out.println("Registerd");
                    out.println("Registerd");

                } else {
                     System.out.println("Registration failed");
                    out.println("failed");
                }

            } else {
                System.out.println("Already Exist");
                out.println("Already Exist");
            }
        } else {
            out.println("failed");
        }

    }

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
