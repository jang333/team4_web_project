let pageObject = {
    page : 1,
    pageSize : 5
}
getEnrolmentList(1);
function getEnrolmentList(){
    $.ajax({
        type: "get",
        url: "/classlist.do",
        data : pageObject,
        success: (r) => {
            console.log(r);
            let html =``;
            let Enlistinfo = document.querySelector('.enlist > tbody')
            for(let i=0; i<r.list.length;i++){
                html += `<tr class="text-center"><th scope="row" class="text-center">${ r.list[i].classno }</th>
                        <td> ${ r.list[i].classname }</td>
                        <td> ${ r.list[i].classtype } </td>
                        <td> ${ r.list[i].mno } </td>
                    </tr>`
            }
            Enlistinfo.innerHTML = html;
            pagination(r);
        }
    });
}

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="getEnrolmentList(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="getEnrolmentList(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="getEnrolmentList(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}