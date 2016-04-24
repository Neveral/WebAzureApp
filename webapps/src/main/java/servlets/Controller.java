package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Neveral on 15.11.15.
 */
public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WShingling alg = new WShingling();

        String data1 = req.getParameter("text1");
        String data2 = req.getParameter("text2");

        if(data1 == null || data2 == null){
            req.setAttribute("data1", "No data");
            req.setAttribute("data2", "No data");
        }else{
            if(!data1.equals("") && !data2.equals("")) {
                req.setAttribute("data1", data1);
                req.setAttribute("data2", data2);
                req.setAttribute("same", "Matches: " + alg.wShingling(data1, data2) + "%");
            }else{
                req.setAttribute("data1", "Enter text, please!");
                req.setAttribute("data2", "Enter text, please!");
            }

        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //doPost(req, resp);
    }
}