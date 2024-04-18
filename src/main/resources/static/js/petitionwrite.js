let regidate;
let duedate;

petTime();
function petTime(){
 // ======== 등록 날짜 ======== //
    let today  = new Date();
    let year1 = today.getFullYear();
    let month1 = today.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줌.
    let date1 = today.getDate();
    regidate = year1 + "-" + month1 + "-" + date1;
    document.querySelector('.regidate').innerHTML = regidate;

    // ======== 마감 날짜 ======== //
    let futureDate = new Date(today);
    futureDate.setDate(today.getDate() + 30);
    let year2 = futureDate.getFullYear();
    let month2 = futureDate.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줌.
    let date2 = futureDate.getDate();
    duedate = year2 + "-" + month2 + "-" + date2;
    document.querySelector('.duedate').innerHTML = duedate;
}

// 청원게시글 등록
function petWrite(){
    console.log ('petWrite()');
    let ptitle = document.querySelector('.ptitle').value;
    let pcontent = document.querySelector('.pcontent').value;
    console.log(duedate);
    // ajax 첨부파일 폼 전송
        $.ajax({
            url : "/up/write",
            method: "post",
            async : false,
            data : {'regidate':regidate, 'duedate':duedate, 'ptitle':ptitle, 'pcontent':pcontent},
            success : (r)=> {

                if(r){
                    alert('청원 등록이 완료되었습니다.');
                  location.href = '/up/pt/li'
                }else{
                    alert('청원 등록에 실패하였습니다. 관리자에게 문의하세요.');
                }
                console.log(r);
            }
        }) // ajax end
} // petWrite end





