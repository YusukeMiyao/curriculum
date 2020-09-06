<%@ page language="java" contentType="text/html; charset=UTF-8;" pageEncoding="UTF-8"%>
<%@ page import="java.util.Objects"%>
<%@ page import="java.util.List"%>
<%@ page import="skillcheck.bean.ResponseBean"%>
<%@ page import="skillcheck.bean.EmployeeBean"%>
<%@ page import="skillcheck.logger.Logger"%>

<!-- Java -->
<%
    String loginEmpId = "";
    ResponseBean responseBean = null;
    List<EmployeeBean> empResultList = null;
    int requestStatus = 0;
    String message = "";

    try {
        if (Objects.isNull(request.getSession()) || request.getSession().isNew()) {
            Logger.log(new Throwable(), "セッションなし");
        } else {
            // session: login
            loginEmpId = (String) session.getAttribute("login");
            Logger.log(new Throwable(), "セッションあり: loginEmpId = " + loginEmpId);
        }

        // FIXME Step-2-1: リクエストよりレスポンスBeanを取得しなさい。
        // Tips: 正確な型（クラス）でキャストすること
        responseBean = [ここへ記述];
        empResultList = responseBean.getEmplyeeBeanList();
        requestStatus = responseBean.getRequestStaus();
        message = responseBean.getMessage();
    } catch (Exception e) {
        Logger.log(new Throwable(), e);
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/employeeResult.css">
<script type="text/javascript" src="js/common.js"/></script>
<title>照会結果</title>
</head>
<body>
    <input id="hiddenDialog" type="hidden" value="<%=requestStatus == 2 ? message : ""%>"></input>
    <br>
    <% if (requestStatus < 2 && !message.isEmpty()) { %>
        <!-- FIXME Step-2-2: 式（Expression）を用いてメッセージ（message）を表示しなさい。 -->
        <p>[ここへ記述]</p>
    <% } %>
    <% if (!empResultList.isEmpty()) { %>
    <div class="div-table-list">
        <table id="resultTable" class="table-emp-list" border="1">
        <caption class="">【社員情報】</caption>
            <tr class="th-last-two-obj">
                <th></th>
                <th>社員番号</th>
                <th>氏名</th>
                <th>メールアドレス</th>
                <th>プログラミング言語</th>
                <th>コメント</th>
                <th>編集</th>
                <th>削除</th>
            </tr>
        <% for (EmployeeBean emp: empResultList) { %>
            <tr class="td-max-width td-last-btn td-last-checkbox">
                <td class="td-marker"></td>
                <!-- FIXME Step-2-3: 社員情報一覧に表示する内容を式（Expression）を用いて表示しなさい。 -->
                <!-- Tips: ループにより取得したリスト内の社員情報Beanを使用すること -->
                <td id="empId">[ここへ記述</td>
                <td title="<%=emp.getName()%>">[ここへ記述</td>
                <td title="<%=emp.getMail()%>">[ここへ記述</td>
                <td title="<%=emp.getProgramingLanguage()%>">[ここへ記述</td>
                <td title="<%=emp.getComment()%>">[ここへ記述]</td>
                <td>
                    <form action="/MVC_Task/employee" method="get">
                        <input type="hidden" name="sender" value="/employeeResult.jsp"></input>
                        <input type="hidden" name="empId" value="<%=emp.getEmpId()%>">
                        <label id="update" class="btn-emp-regist">
                            <span class="underline">編集</span>
                        </label>
                    </form>
                </td>
                <td id="delete">
                    <input id="disabled" type="hidden" name="rowDelCheckBox" value="<%=emp.getEmpId().equals(loginEmpId)%>">
                    <input id="delCheckBox" type="checkbox" name="delCheckBox" value="0">
                </td>
            </tr>
        <% } %>
            <tfoot align="right" class="tfoot-td">
                <tr>
                    <td colspan="8">
                        <form action="/MVC_Task/employee" method="post">
                            <div class="div-btn-delete-area">
                                <input type="hidden" name="sender" value="/employeeResult.jsp"></input>
                                <input id="deleteEmpId" type="hidden" name="empId" value="">
                                <button id="btnDelete" class="btn-delete common-btn-disabled"
                                        name="requestType" value="delete">削除</button>
                            </div>
                        </form>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
    <% } %>
    <br>
    <form action="/MVC_Task/employee" method="get">
        <input type="hidden" name="sender" value="/employeeResult.jsp"></input>
        <br>
        <label id="logout" class="btn-logout" onclick="exeSubmit(this)">
            <span class="underline">ログアウト</span>
        </label>
    </form>
</body>
</html>