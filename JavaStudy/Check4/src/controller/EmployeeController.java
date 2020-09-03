package controller;

/**
 * 社員情報管理コントローラー
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 問① index.htmlから送信されたIDとPassWordの値を取得できるように修正しましょう。
            String id = request.getParameter("ここを改修");
            String password = request.getParameter("ここを改修");

            /*
             *  IDとPassWordと元に、社員情報を検索する関数の呼び出し、結果をJSPに渡す処理
             *  ※ EmployeeBeanとEmployeeServiceをimportするのを忘れないでください。
             */

            // 問② EmployeeServiceクラスをインスタンス化する。

            // 問③ EmployeeBeanに、EmployeeServiceよりsearch関数を呼び出し、返り値を格納する。

            // 問④ nullの部分に適切な引数をセットする。
            request.setAttribute("EmployeeBean", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}