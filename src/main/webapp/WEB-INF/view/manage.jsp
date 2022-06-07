<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/assets/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href='${pageContext.request.contextPath}/assets/css/quartz.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/quartz.js"></script>
    <title> My Quartz Management :D </title>
</head>
<body>
	<div id="container">
        
		<h2>Quartz Management</h2>
        
        <div style="width: 100%; display: flex;">
            
            <div class="box" style="display: inline-block;">
                <h3>[ Menu ]</h3>
                <h3 id="home"><a href="${pageContext.request.contextPath}/">Home</a></h3>
                <h3>Quartz exception list <a href="${pageContext.request.contextPath}/exception">go</a> </h3>
                <h3>Quartz job history <a href="${pageContext.request.contextPath}/job/history">go</a> </h3>
            </div>
            
            <div style="margin-left: 10px;">
                <div class="box" style="display: inline-block">
                    <h3>[ Server response message ]</h3>
                    <textarea id="response_message" readonly></textarea>
                </div>
            </div>
        </div>
        
        <div style="width: 100%">
            <div class="box" style="display: inline-block;">
                <h3 style="color: #20d304">[ ${schedulerName} Status ]</h3>
                <b style="font-size: small">실시간 satatus는 새로고침으로 확인.</b>
                <ul>
                    <li>${schedulerName} is started ? ${isStarted}</li>
                    <li>${schedulerName} is standby ? ${isInStandbyMode}</li>
                    <li>${schedulerName} is shutdown ? ${isShutdown}</li>
                </ul>
            </div>
        </div>
        
        <div style="width: 100%; display: flex;">
            <div class="cover box dangerzone" id="dangerZone" style="width: 40%;">
                <h3 style="color: #ff1e00;"> [ Danger zone ] Quartz의 동작을 controll합니다.</h3>
                <ul>
                    <li><input type="button" data-url="${pageContext.request.contextPath}/schedule/start" value="Start"> <b>스케줄러를 시작 시킵니다.</b> </li>
                    <li><input type="button" data-url="${pageContext.request.contextPath}/schedule/standby" value="Pause"> <b>스케줄러를 대기 (일시정지) 시킵니다.</b> </li>
                    <li style="margin-top: 50px;"><input type="button" data-url="${pageContext.request.contextPath}/schedule/shutdown" value="Shutdown"> <b>스케줄러를 파괴 시킵니다. 다시 스케줄러를 시작할 수 없습니다.</b> </li>
                </ul>
            </div>
            <div style="margin-top: 30px; margin-left: 10px;">
                <input type="password" id="dangerzonePassword" oninput="dangerzoneClear(this.value)" placeholder="danger-zone 해제 password를 입력하세요" autocomplete="off">
            </div>
        </div>
	</div>
</body>
</html>