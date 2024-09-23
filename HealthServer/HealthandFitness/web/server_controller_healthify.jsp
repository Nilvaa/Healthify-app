<%-- 
    Document   : server_controller_healthify
    Created on : 1 Feb, 2024, 12:31:58 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="Connection.dbconnection"%>
<%
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
 
 
// USER PROFILE

if (key.equals("User_profile")) {
    String id = request.getParameter("id");
        String st = "SELECT * FROM `user_reg` WHERE `r_id`='"+id+"'";
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(0) + "#" + v.get(1)+"#"+ v.get(2)+"#"+ v.get(3)+"#"+ v.get(4)+"#"+ v.get(5)+"#"+ v.get(6)+"#"+ v.get(7)+"#"+ v.get(8)+"#"+ v.get(9)+"#"+ v.get(10)+"#"+ v.get(11)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }


//USER EDIT PROFILE

 if (key.equals("edit_profile")) {
      String id=request.getParameter("u_id");
        String na=request.getParameter("name");
        String em = request.getParameter("email");
        String pa = request.getParameter("passwrd");
        String ph = request.getParameter("num");
        String dob = request.getParameter("dob");
        String ag = request.getParameter("age");
        String ge=request.getParameter("gender");
        String he = request.getParameter("height");
        String we = request.getParameter("weight");
        String bl = request.getParameter("des");
        String i=request.getParameter("image");
//        System.out.println("IMAG==="+i);
        String sq5 = "UPDATE `user_reg` SET name='"+na+"',email='"+em+"',phone='"+ph+"',pass='"+pa+"',dob='"+dob+"',age='"+ag+"',height='"+he+"',weight='"+we+"',gender='"+ge+"',des='"+bl+"',pic='"+i+"' WHERE r_id='"+id+"'";
        String st = "UPDATE `login` SET pass='"+pa+"',uname='"+em+"' WHERE u_id='"+id+"' AND type='user'";
       System.out.println("EEE"+sq5);
        int res6 = db.putData(sq5);
        int res7 = db.putData(st);
        if (res6 > 0 && res7 > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }  
    }
 
 
 
//USER VIEW NUTRI PLANS

if (key.equals("ViewNutriPlan")) {
        String str3 = "SELECT * FROM `nutri_plan` GROUP BY `n_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("nu_id", v.get(0).toString());
                jsonobj.put("plan_nam", v.get(1).toString());
                jsonobj.put("plan_typ", v.get(2).toString());
                jsonobj.put("breakfast", v.get(3).toString());
                jsonobj.put("dinner", v.get(4).toString());
                jsonobj.put("lunch", v.get(5).toString());
                jsonobj.put("snack", v.get(6).toString());
                jsonobj.put("water", v.get(7).toString());
                jsonobj.put("macro", v.get(8).toString());
                jsonobj.put("plan_des", v.get(9).toString());
                 jsonobj.put("plan_pic", v.get(10).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


 
 //USER VIEW WORKOUTS

if (key.equals("ViewWorkOut")) {
        String str3 = "SELECT * FROM `workout` GROUP BY `w_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("w_id", v.get(0).toString());
                jsonobj.put("ex_nam", v.get(1).toString());
                jsonobj.put("ex_type", v.get(2).toString());
                jsonobj.put("ex_diff", v.get(3).toString());
                jsonobj.put("ex_num", v.get(4).toString());
                jsonobj.put("ex_set", v.get(5).toString());
                jsonobj.put("ex_res", v.get(6).toString());
                jsonobj.put("calory", v.get(7).toString());
                jsonobj.put("inst", v.get(8).toString());
                jsonobj.put("ex_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }
 
 //USER ADD MEDICINE

if (key.equals("AddMedicine")) {
        String bid = request.getParameter("user_id");
        String pid = request.getParameter("medname");
         String sid = request.getParameter("dosage");
        String p = request.getParameter("timeofday");
         String  q= request.getParameter("food");
        String cv = request.getParameter("des");
         String sql="INSERT INTO `medicine`(`u_id`,`med_nam`,`dosage`,`timeofday`,`food`,`des`)VALUES('"+bid+"','"+pid+"','"+sid+"','"+p+"','"+q+"','"+cv+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }




//USER VIEW MEDICINES

if (key.equals("ViewMedicine")) {
    String id = request.getParameter("user_id");
        String str3 = "SELECT * FROM `medicine` WHERE `u_id`='"+id+"' GROUP BY `m_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("m_id", v.get(0).toString());
                jsonobj.put("m_uid", v.get(1).toString());
                jsonobj.put("med_nam", v.get(2).toString());
                jsonobj.put("dosage", v.get(3).toString());
                jsonobj.put("timeofday", v.get(4).toString());
                jsonobj.put("food", v.get(5).toString());
                jsonobj.put("med_des", v.get(6).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }




//USER EDIT MEDICINE

if (key.equals("Edit_medicine")) {
    String mid = request.getParameter("med_id");
        String id = request.getParameter("user_id");
        String pid = request.getParameter("medname");
         String sid = request.getParameter("dosage");
        String p = request.getParameter("timeofday");
         String  q= request.getParameter("food");
        String cv = request.getParameter("des");
         String sq5 = "UPDATE `medicine` SET med_nam='"+pid+"',dosage='"+sid+"',timeofday='"+p+"',food='"+q+"',des='"+cv+"' WHERE m_id='"+mid+"' AND u_id='"+id+"'";
        int res2 = db.putData(sq5);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }

//LOGIN
if (key.equals("login")) {
        String ln = request.getParameter("uname");
        String lp = request.getParameter("pass");
        String st = "SELECT * from login where uname='" + ln + "' and pass='" + lp + "' AND status='1'";
        System.out.println(st);
        
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(1) + "#" + v.get(4)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }

////////////////////////////////////////////////////////////////////ADMIN SECTION///////////////////////////////////////////////////////////////////////////////



//ADMIN VIEW USERS

if (key.equals("ViewUsers")) {
        String str3 = "SELECT * FROM `user_reg` GROUP BY `r_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("r_uid", v.get(0).toString());
                jsonobj.put("user_name", v.get(1).toString());
                jsonobj.put("u_email", v.get(2).toString());
                jsonobj.put("u_phone", v.get(3).toString());
                jsonobj.put("u_pass", v.get(4).toString());
                jsonobj.put("u_dob", v.get(5).toString());
                jsonobj.put("u_age", v.get(6).toString());
                jsonobj.put("u_hei", v.get(7).toString());
                jsonobj.put("u_wei", v.get(8).toString());
                jsonobj.put("u_gen", v.get(9).toString());
                jsonobj.put("u_des", v.get(10).toString());
                jsonobj.put("u_pic", v.get(11).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN ADD NUTRI PLAN

if (key.equals("NutriPlan")) {
        String bid = request.getParameter("plan_name");
        String pid = request.getParameter("type");
         String sid = request.getParameter("breakfast");
        String n = request.getParameter("lunch");
        String p = request.getParameter("dinner");
         String  q= request.getParameter("water");
         String t = request.getParameter("snack");
         String b = request.getParameter("macro");
        String cv = request.getParameter("des");
         String cn = request.getParameter("image");
         String sql="INSERT INTO `nutri_plan`(`plan_nam`,`type`,`breakfast`,`lunch`,`dinner`,`snack`,`water`,`macro`,`des`,`pic`)VALUES('"+bid+"','"+pid+"','"+sid+"','"+n+"','"+p+"','"+t+"','"+q+"','"+b+"','"+cv+"','"+cn+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//ADMIN VIEW NUTRI PLANS

if (key.equals("AdminViewNutriPlan")) {
        String str3 = "SELECT * FROM `nutri_plan` GROUP BY `n_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("nu_id", v.get(0).toString());
                jsonobj.put("plan_nam", v.get(1).toString());
                jsonobj.put("plan_typ", v.get(2).toString());
                jsonobj.put("breakfast", v.get(3).toString());
                jsonobj.put("dinner", v.get(4).toString());
                jsonobj.put("lunch", v.get(5).toString());
                jsonobj.put("snack", v.get(6).toString());
                jsonobj.put("water", v.get(7).toString());
                jsonobj.put("macro", v.get(8).toString());
                jsonobj.put("plan_des", v.get(9).toString());
                 jsonobj.put("plan_pic", v.get(10).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN EDIT NUTRI PLANS

if (key.equals("EditNutriPlan")) {
     String id = request.getParameter("nutri_id");
     String bid = request.getParameter("plan_name");
        String pid = request.getParameter("type");
         String sid = request.getParameter("breakfast");
        String n = request.getParameter("lunch");
        String p = request.getParameter("dinner");
         String  q= request.getParameter("water");
         String t = request.getParameter("snack");
         String b = request.getParameter("macro");
        String cv = request.getParameter("des");
         String cn = request.getParameter("image");
         String sq5 = "UPDATE `nutri_plan` SET plan_nam='"+bid+"',type='"+pid+"',breakfast='"+sid+"',dinner='"+p+"',snack='"+t+"',water='"+q+"',macro='"+b+"',des='"+cv+"',pic='"+cn+"' WHERE n_id='"+id+"'";
        int res2 = db.putData(sq5);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



//ADMIN ADD WORK OUT

if (key.equals("WorKOutPlan")) {
        String bid = request.getParameter("exc_name");
        String pid = request.getParameter("type");
         String sid = request.getParameter("different");
        String n = request.getParameter("num");
        String p = request.getParameter("sets");
         String  q= request.getParameter("rest");
         String t = request.getParameter("calory");
         String b = request.getParameter("des");
        String cv = request.getParameter("image");
         String sql="INSERT INTO `workout`(`nam`,`type`,`diff`,`num`,`sets`,`res`,`calory`,`instr`,`pic`)VALUES('"+bid+"','"+pid+"','"+sid+"','"+n+"','"+p+"','"+q+"','"+t+"','"+b+"','"+cv+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


 //ADMIN VIEW WORKOUTS

if (key.equals("AdminWorkOutPlan")) {
        String str3 = "SELECT * FROM `workout` GROUP BY `w_id` DESC";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("w_id", v.get(0).toString());
                jsonobj.put("ex_nam", v.get(1).toString());
                jsonobj.put("ex_type", v.get(2).toString());
                jsonobj.put("ex_diff", v.get(3).toString());
                jsonobj.put("ex_num", v.get(4).toString());
                jsonobj.put("ex_set", v.get(5).toString());
                jsonobj.put("ex_res", v.get(6).toString());
                jsonobj.put("calory", v.get(7).toString());
                jsonobj.put("inst", v.get(8).toString());
                jsonobj.put("ex_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//ADMIN EDIT WORKOUT

if (key.equals("EditWorKOutPlan")) {
    String eid = request.getParameter("w_id");
     String bid = request.getParameter("exc_name");
        String pid = request.getParameter("type");
         String sid = request.getParameter("different");
        String n = request.getParameter("num");
        String p = request.getParameter("sets");
         String  q= request.getParameter("rest");
         String t = request.getParameter("calory");
         String b = request.getParameter("des");
        String cv = request.getParameter("image");
         String sq5 = "UPDATE `workout` SET nam='"+bid+"',type='"+pid+"',diff='"+sid+"',num='"+n+"',sets='"+p+"',res='"+q+"',calory='"+t+"',instr='"+b+"',pic='"+cv+"' WHERE w_id='"+eid+"'";
        int res2 = db.putData(sq5);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }

%>