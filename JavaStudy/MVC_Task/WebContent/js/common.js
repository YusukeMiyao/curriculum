/**
 * js: 共通
 */

/**
 * ログイン中の社員番号と一致する行の削除チェックボックスを非活性にする
 */
window.onload = function() {

    // 例外発生していればダイアログを表示
    const dlg = document.getElementById('hiddenDialog');
    if (dlg && dlg.value) {
        alert(dlg.value);
        return;
    } else {
        var tbl = document.getElementById('resultTable');
        if (tbl === null) return;

        var fe = Array.prototype.forEach;
        fe.call(tbl.children, function(rows) {
            fe.call(rows.children, function(cols) {
                for (var index = 0; index < cols.children.length; index++) {
                    var data = cols.children[index];
                    if (data === null || data.id !== 'delete') continue;
                    var input = data.getElementsByTagName("input");
                    // 要素数が明らかなので直接要素指定
                    if ('true' === input[0].value) input[1].disabled = true;
                }
            })
        })
        setDisabledDeleteButton(false);
    }
};

/**
 *
 * @param isCheckedAtLeastOne
 * @returns
 */
function setDisabledDeleteButton(isCheckedAtLeastOne) {

    const BTN_ENABLE_DELETE = 'btn-enable-delete';
    const BTN_DISABLED = 'common-btn-disabled';
    console.log('isCheckedAtLeastOne = ' + isCheckedAtLeastOne);

    var btnDelete = document.getElementById('btnDelete');
    if (btnDelete === null || typeof btnDelete === 'undefined') return;

    btnDelete.disabled = !isCheckedAtLeastOne;

    /* ホバーで追加したスタイルのクリア */
    btnDelete.style.background = '';

    if (isCheckedAtLeastOne) {
        if (!btnDelete.classList.contains(BTN_ENABLE_DELETE)) btnDelete.classList.add(BTN_ENABLE_DELETE);
        if (btnDelete.classList.contains(BTN_DISABLED)) btnDelete.classList.remove(BTN_DISABLED);
    } else {
        if (btnDelete.classList.contains(BTN_ENABLE_DELETE)) btnDelete.classList.remove(BTN_ENABLE_DELETE);
        if (!btnDelete.classList.contains(BTN_DISABLED)) btnDelete.classList.add(BTN_DISABLED);
    }

}

/**
 * サブミット処理: 押下した疑似ボタンのIDからリクエスト判別用hiddenを作成後、formの子要素に追加して送信
 * @param obj フォーム送信用疑似ボタン（ラベル）
 * @returns
 */
function exeSubmit(obj) {
    const ELEMENT = 'input'
    const TYPE = ['type', 'hidden'];
    const NAME = ['name', 'requestType'];
    const VALUE = 'value';

    var input = document.createElement('input');
    input.setAttribute(TYPE[0], TYPE[1]);
    input.setAttribute(NAME[0], NAME[1]);
    input.setAttribute(VALUE, obj.id);

    obj.parentNode.appendChild(input);
    obj.parentNode.submit();
}