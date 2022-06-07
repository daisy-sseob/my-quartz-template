window.addEventListener('load', (e) => {
    document.querySelector('#dangerZone').addEventListener('click',(event) => {
        
        // 박스 비활성화 되어있으면 클릭 못함.
        if (event.currentTarget.classList.contains('dangerzone')) {
            alert("Danger Zone이 비활성화 되어있습니다.\n해제 password는 README를 참고하세요.");
            event.stopPropagation();
            return false;
        }
    });
    
    document.querySelector('#container').addEventListener("click", (event) => {
        let el = event.target;
        if (el.nodeName === "INPUT" && el.type === "button") {
            
            if (!!!el.dataset.url) {
                alert("Url이 설정되어있지 않아요 !! html dataset을 설정해주세용");
                return false;
            }
            
            if (!!el.dataset.url) {
                if(!confirm("진짜 실행 합니까 ? 다시한번 생각해 보세요 !")) return false;
                
                dangerzoneClear(document.querySelector('#dangerzonePassword').value, () => {
                    fetch(`${el.dataset.url}`, {method: "POST"})
                        .then( res => {
                            if (res.status === 200) {
                                alert(`status: ${res.status}`);
                                return res.json();
                            } else {
                                alert(`status: ${res.status} exception list page에서 로그를 확인하세요.`);
                            }
                        })
                        .then(json => {
                            if (json !== undefined) {
                                document.querySelector('#response_message').textContent = (json.msg === undefined) ? json : json.msg;
                            }
                        })
                        .catch( err => {
                            alert(err);
                        });
                })
            }
        }
    });
});

let dangerzoneClear = function (password, callback){

    if (password.length === 0) {
        document.querySelector('#response_message').textContent = '';
        return;
    }
    
    fetch(`dangerZone/match?password=${password}`, {method: 'POST'})
        .then( res => {
            let dangerZoneEl = document.querySelector('#dangerZone');
            if (res.status === 200) {
                dangerZoneEl.classList.remove('dangerzone');
                if (typeof callback === "function") {
                    callback();
                }
            } else if (res.status === 401) {
                dangerZoneEl.classList.add('dangerzone');
            } else {
                dangerZoneEl.classList.add('dangerzone');
            }
            return res.json();
         })
        .then(json => document.querySelector('#response_message').textContent = json.msg)
        .catch( err => {
            alert(err);
        });
}

let getContextPath = function () {
    return location.href.substring(location.href.indexOf(location.host) + location.host.length + 1);
};