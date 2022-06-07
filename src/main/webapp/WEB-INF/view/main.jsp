<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/assets/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href='${pageContext.request.contextPath}/assets/css/quartz.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/quartz.js"></script>
    <title> My Quartz Service :D </title>
</head>
<body>
	<div id="container">
        <div>
		    <h2 style="display: inline-block">Quartz Simple Service</h2>
            <b style="font-size: small; margin-left: 10px;">(이곳은 Explorer를 지원하지 않습니다.)</b>
        </div>
        
        <div class="box" style="display: inline-block">
            <h3>[ Menu ]</h3>
            <h3>Quartz management <a href="${pageContext.request.contextPath}/schedule">go</a> </h3>
            <h3>Quartz exception list <a href="${pageContext.request.contextPath}/exception">go</a> </h3>
            <h3>Quartz job history <a href="${pageContext.request.contextPath}/job/history">go</a> </h3>
        </div>
        
        <div style="width: 100%; display: flex;">
            <div class="cover box dangerzone" id="dangerZone" style="width: 40%;">
                <h3 style="color: #ff1e00;"> [ Danger zone method ] </h3>
                <ul>
                    <li><input type="button" data-url="${pageContext.request.contextPath}/sendMailSmsService" value="메일 발송"></li>
                </ul>
            </div>
            <div style="margin-top: 30px; margin-left: 10px;">
                <input type="password" id="dangerzonePassword" oninput="dangerzoneClear(this.value)" placeholder="danger-zone 해제 password를 입력하세요" autocomplete="off">
            </div>
            <div style="margin-left: 10px;">
                <div class="box" style="display: inline-block">
                    <h3>[ Server response message ]</h3>
                    <textarea id="response_message" readonly></textarea>
                </div>
            </div>
        </div>
        
	</div>
</body>
</html>