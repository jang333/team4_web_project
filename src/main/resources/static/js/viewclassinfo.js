let pageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="getAllClassInfo(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="getAllClassInfo(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="getAllClassInfo(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}

pfview(1)
function pfview(page){
    $.ajax({
        type: "get",
        url: "/member/professor/class",
        success: function (r) {
            console.log(r);
            let classAllInfo = document.querySelector('.classAllInfo');
            r.forEach((list) => {
                console.log(list);
                classAllInfo.innerHTML += `<tr class="text-center">
                            <th scope="row" class="text-center">${list.no}</th>
                            <td>${list.classname}</td>
                            <td>${list.professorname}</td>
                            <td>${list.roomnumber}</td>
                            <td>${list.dayweek}${list.starttime}~${list.endtime}</td>
                            <td>${list.semester}</td>
                            <td class="instate${list.no}">대기</td>
                        </tr>`;
                if(list.cstate == 1){
                    document.querySelector('.instate'+list.no).innerText='반려'
                }else if(list.cstate == 2){
                    document.querySelector('.instate'+list.no).innerText='승인'
                }
            })

        }
    });
}

