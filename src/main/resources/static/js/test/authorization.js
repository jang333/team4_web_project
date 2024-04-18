console.log('authorization.js')

let pageObject={
    page : 1,
    pageSize : 5
}
getAuthorization(1);
function getAuthorization(page){
    pageObject.page = page;
    $.ajax({
        type: "get",
        url: "/authorization",
        data : pageObject,
        success: function (r) {
            console.log(r);
            let authorizationView = document.querySelector('.authorizationView > table > tbody')
            console.log(authorizationView);
    
            let html = ``;
            r.list.forEach((list)=>{
            console.log(list)
             html += `<tr><th scope="row" class="text-center">${list.mno}</th>
                        <td>${list.name}</td>
                        <td>${list.state} </td>
                        <td>
                            <select class="state${list.mno} form-select w-50 me-2">
                                <option selected>선택</option>
                                <option value="0">권한없음</option>
                                <option value="1">학생</option>
                                <option value="2">교수</option>
                                <option value="3">행정직원</option>
                                <option value="5">졸업생</option>
                                <option value="6">휴학생</option>
                            </select>
                        </td>
                        <td class="text-center">
                            <button type="button" onclick="change( ${ list.mno } )" class="btn btn-outline-secondary btn-sm">
                                Edit
                            </button>
                        </td>
                    </tr>`
            })
           authorizationView.innerHTML = html;
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
                 <a class="page-link" onclick="getAuthorization(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="getAuthorization(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="getAuthorization(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}



function change( mno ){

    let state = document.querySelector(`.state${mno}`).value;
    console.log(state);
    $.ajax({
        url : "/changestate",
        method : "put",
        data : {"state" : state,'mno':mno},
        success : function (r){
            alert('권한을 변경 하였습니다.')
            location.href='/m/auth'
        }
    })
}