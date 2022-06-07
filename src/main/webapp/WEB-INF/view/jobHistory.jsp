<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/assets/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href='${pageContext.request.contextPath}/assets/css/quartz.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/common.js"></script>
    <title> My Quartz Job History :D </title>
</head>
<body>
    <h2>Quartz Job History 목록</h2>
    
    <div class="box" style="display: inline-block">
        <h3>[ Menu ]</h3>
        <h3 id="home"><a href="${pageContext.request.contextPath}/">Home</a></h3>
        <h3>Quartz management <a href="${pageContext.request.contextPath}/schedule">go</a> </h3>
        <h3>Quartz exception list <a href="${pageContext.request.contextPath}/exception">go</a> </h3>
    </div>
    
    <div class="box">
        <div>
            <h3>[ Job History Search ]</h3>
            <div style="border: 1px solid black; padding: 10px; border-radius: 6px; display: inline-block" id="search">
                <h3>[ search box ]</h3>
                <input type="text" name="date" placeholder="날짜 yyyy-mm-dd" maxlength="10">
                <label style="font-weight: bold; font-size: 12px"><input type="radio" name="dateType" data-url="${pageContext.request.contextPath}/job/history" value="day" checked> 일</label>
                <label style="font-weight: bold; font-size: 12px"><input type="radio" name="dateType" data-url="${pageContext.request.contextPath}/job/history/month" value="month"> 월</label>
                <input type="button" value="조회">
            </div>
            
            <div style="border: 1px solid black; padding: 10px; border-radius: 6px; display: inline-block" id="filterBox">
                <h3>[ filter box ]</h3>
                <input type="text" name="jobKey" style="width: 300px;" placeholder="jobKey '!' not 조건 사용가능">
                <label style="font-weight: bold; font-size: 12px"><input type="radio" name="isSuccess" value="" checked> 전체</label>
                <label style="font-weight: bold; font-size: 12px"><input type="radio" name="isSuccess" value=true> 성공</label>
                <label style="font-weight: bold; font-size: 12px"><input type="radio" name="isSuccess" value=false> 실패</label>
            </div>
        </div>
        
        
        <div style="margin-top: 10px">
            <b> Cell을 클릭하면 Clip board에 cell의 내용이 복사됩니다.</b>
        </div>
        <table id="table" style="width: 100%; margin-top: 10px;">
            <colgroup>
                <col width="1%" style="text-align: center">
                <col width="1%" style="text-align: center">
                <col width="2%" style="text-align: center">
            </colgroup>
            <thead>
                <tr>
                    <th>total</th>
                    <th>index</th>
                    <th>jobKey</th>
                    <th>isSuccess</th>
                    <th>jobRunTime</th>
                    <th>fireTime</th>
                    <th>nextFireTime</th>
                    <th>jobExceptionMessage</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    
</body>
<script>

    let JobHistory = function () {
        this.jobHistories = [];

        this.setJobHistories = function (jobHistories) {
            this.jobHistories = jobHistories;
        };
        
        // filter 조건 적용하여 return 
        this.getJobHistories = function () {
            
            var resultArr = this.jobHistories;
            
            let jobKey = document.querySelector('input[name=jobKey]').value.trim().toLocaleLowerCase();
            
            if (!!jobKey && !jobKey.includes('!')) {
                resultArr = this.jobHistories.filter(jobHistory => (jobHistory.jobKey.toLocaleLowerCase().includes(jobKey)));
            }else if (!!jobKey && jobKey.includes('!')) {
                jobKey = jobKey.replaceAll('!','')
                resultArr = this.jobHistories.filter(jobHistory => (!jobHistory.jobKey.toLocaleLowerCase().includes(jobKey)));
            }

            let checkedRadio = Array.prototype.filter.call(document.querySelectorAll('input[name=isSuccess]'), (radio) => radio.checked === true);
            let radioValue = checkedRadio[0].value;
            if (checkedRadio.length > 0) {
                resultArr = (radioValue === '') ? resultArr : resultArr.filter(jobHistory => (jobHistory.success === (JSON.parse(radioValue))));
            }
            return resultArr;
        };
    };
    
    // job history 객체 초기화
    let jobHistory = new JobHistory();
    
    window.onload = () => {
        
        // search event
        ['click', 'keypress'].forEach(eventType => document.querySelector("#search").addEventListener(eventType, (event) => {
            if ( event.type === 'keypress' && event.key === 'Enter' ){
                getHistories(event);
            }
            if( event.type === 'click' && event.target.type === 'button' ){
                getHistories(event);
            }
        }));
        
        // filter event
        ['click','keypress'].forEach( eventType => document.querySelector("#filterBox").addEventListener(eventType, (event) => {
            if ( event.type === 'keypress' && event.key === 'Enter'){
                bindResult(jobHistory.getJobHistories());
            }
            
            if( event.type === 'click' && event.target.type === 'radio'){
                bindResult(jobHistory.getJobHistories());
            }
        }));

        // table clipboard
        document.querySelector('#table').addEventListener('click', (event) => {
            if (event.target.nodeName === 'TD') {
                copyClipBoard(event.target.textContent);
            }
        });
    }
    
    // jobHistory 조회
    let getHistories = function(event){
        let date = document.querySelector("input[name=date]").value;
        if(!!!date){
            alert("날짜는 필수값 입니다.");
            return;
        }
        
        let pattern1 = /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; // YYYY-MM-DD
        if (!pattern1.test(date)) {
            alert("날짜 형식은 yyyy-mm-dd 입니다 맞춰주세요");
            return;
        }

        const url = Array.prototype
                .filter.call(document.querySelectorAll("input[name=dateType]"), (radio) => radio.checked)
                .map(radio => radio.dataset.url)
                .toString();

        fetch(`\${url}/\${date}`, {method: 'GET'})
            .then( res => {
                alert(`status: \${res.status}`);
                return res.json();
            })
            .then( (histories) => {
                jobHistory.setJobHistories(histories);
                bindResult(jobHistory.getJobHistories());
            })
            .catch( err => {
                alert(err);
            });
    }

    // html 정리후 조회된 jobHistories 다시 삽입
    let bindResult = function(histories){
        
        let table = document.querySelector('#table');
        let tbody = table.tBodies[0];
        tbody.innerHTML = ''; //초기화
        
        let htmlElement = '';
        // data bind
        histories.forEach( (history, i) => {

            htmlElement += `
                <tr>
                    <td>\${histories.length}</td>
                    <td>\${i}</td>
                    <td>\${history.jobKey}</td>
                    <td style="font-weight: bold; color: \${(history.success) ? "green" : "red"};">\${history.success}</td>
                    <td>\${history.jobRunTime}</td>
                    <td>\${history.fireTime}</td>
                    <td>\${history.nextFireTime}</td>
                    <td>\${history.jobExceptionMessage}</td>
                </tr>
            `;
        });

        tbody.innerHTML = htmlElement;
    }
</script>
</html>