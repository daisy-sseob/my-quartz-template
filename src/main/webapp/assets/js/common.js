/**
 * 클립보드로 복사해줌
 * @param {String} copyVal 
 * @author shs
 */
let copyClipBoard = function (copyVal){

    var emptyEl = document.createElement("textarea");
    document.body.appendChild(emptyEl);

    emptyEl.value = copyVal;
    emptyEl.select();

    document.execCommand('copy');
    document.body.removeChild(emptyEl);
}