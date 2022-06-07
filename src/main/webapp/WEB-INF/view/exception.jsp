<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/assets/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href='${pageContext.request.contextPath}/assets/css/quartz.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/common.js"></script>
    <title> My Quartz Exception list :D </title>
</head>
<body>
    <h2>Quartz Exception 목록</h2>
    
    <div class="box" style="display: inline-block">
        <h3>[ Menu ]</h3>
        <h3 id="home"><a href="${pageContext.request.contextPath}/">Home</a></h3>
        <h3>Quartz management <a href="${pageContext.request.contextPath}/schedule">go</a> </h3>
        <h3>Quartz job history <a href="${pageContext.request.contextPath}/job/history">go</a> </h3>
    </div>
    
    <div class="box">
        <div id="search" data-url="${pageContext.request.contextPath}/exception">
            
            <h3>[ Exception Search ]</h3>
            
            <input type="text" name="lastChangeDt" placeholder="날짜 yyyy-mm-dd" maxlength="10">
            <input type="button" value="조회">
            
            <b> Cell을 클릭하면 Clip board에 cell의 내용이 복사됩니다.</b>
        </div>
        <table id="table" style="width: 100%; margin-top: 10px;">
            <colgroup>
                <col style="width: 1%; text-align: center">
                <col style="width: 1%; text-align: center">
                <col style="width: 2%; text-align: center">
                <col style="max-width: 30%">
                <col style="max-width: 30%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
            </colgroup>
            <thead>
                <tr>
                    <th>total</th>
                    <th>index</th>
                    <th>excpLogSeqno</th>
                    <th>excpNm</th>
                    <th>excpCn</th>
                    <th>excpTraceCn</th>
                    <th>dvlprDc</th>
                    <th>vriablCn</th>
                    <th>lastChangeDt</th>
                    <th>lastChangerId</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    
</body>
<script>
    window.onload = () => {
        ['click','keypress'].forEach( event => document.querySelector("#search").addEventListener(event, typeHandler, false));
        
        document.querySelector('#table').addEventListener('click',(event) => {
            
            if(event.target.nodeName === 'TD'){
                copyClipBoard(event.target.textContent);
            }
            
            if(event.target.classList.contains('tooltip')){
                event.target.style.display = 'none';
            }
            
            if(event.target.classList.contains('trace')){
                let style = event.target.children[0].style;
                (style.display === 'none' || style.display === '') ? style.display = 'block' : 'none';  
            }
        })
    }
    
    // event종류에 따라
    let typeHandler = function(event){
        
        if ( event.type === 'keypress' && event.key === 'Enter' ){
            getExceptions(event);
        }
        
        if( event.type === 'click' && event.target.type === 'button' ){
            getExceptions(event);
        }
    }
    
    // exception 조회
    let getExceptions = function(event){
        let lastChangeDt = document.querySelector("input[name=lastChangeDt]").value;
        if(!!!lastChangeDt){
            alert("날짜는 필수값 입니다.");
            return;
        }
        
        let pattern1 = /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; // YYYY-MM-DD
        if (!pattern1.test(lastChangeDt)) {
            alert("날짜 형식은 yyyy-mm-dd 입니다 맞춰주세요");
            return;
        }

        fetch(`\${event.currentTarget.dataset.url}/\${lastChangeDt}`, {method: 'GET'})
            .then( res => {
                alert(`status: \${res.status}`);
                return res.json();
            })
            .then( (exceptions) => bindResult(exceptions))
            .catch( err => {
                alert(err);
            });
    }

    // html 정리후 조회된 exception 다시 삽입
    let bindResult = function(exceptions){
        
        let table = document.querySelector('#table');
        let tbody = table.tBodies[0];
        tbody.innerHTML = ''; //초기화
        
        let htmlElement = '';
        
        // data bind
        exceptions.forEach( (ex, i) => {
            htmlElement += `
                <tr>
                    <td>\${exceptions.length}</td>
                    <td>\${i}</td>
                    <td>\${ex.excpLogSeqno}</td>
                    <td>\${ex.excpNm}</td>
                    <td>\${ex.excpCn}</td>
                    <td class="trace">...생략 클릭하면 나와용 <span class="tooltip">\${ex.excpTraceCn}</span> </td>
                    <td>\${ex.dvlprDc}</td>
                    <td>\${ex.vriablCn}</td>
                    <td>\${ex.lastChangeDt}</td>
                    <td>\${ex.lastChangerId}</td>
                </tr>
            `;
        });

        tbody.innerHTML = htmlElement;
    }
</script>
</html>