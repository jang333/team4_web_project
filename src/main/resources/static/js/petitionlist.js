// 카운트

function timeBox(targetDate){
     // 현재 날짜
    var today = new Date();

    // 목표 날짜와 현재 날짜 간의 시간 차이 계산
    var timeDiff = targetDate.getTime() - today.getTime();

    // 시간 차이를 일 단위로 계산
    var daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

    return daysDiff;
}

let pageObject={
    page : 1,
    pageSize : 6
}

// 청원게시글 출력
PetitiotionList(1)
function PetitiotionList(page){
    pageObject.page = page
    console.log('PetitiotionList()');

    // 필드값 가져오기
    $.ajax({
        url : "/up/list",
        async : false,
        method : "get",
        data : pageObject,
        success : (r)=> {
            // 1. 어디에
            let petitionList = document.querySelector(".petitionList");
            console.log(r)
            let html= '';
            for(let i = 0; i < r.list.length ; i++){
            // DDay 계산
            let duedate = new Date(r.list[i].duedate);
            let dDay = timeBox(duedate);
            html += `<div class="border rounded border-primary-subtle py-3 m-2 shadow petBox card">
                                        <div class="py-3 petitionbody card-body">
                                            <a href="/up/pt/vi?ppno=${r.list[i].ppno}" style="text-decoration: none;">
                                                <h5 class="text-center card-title text-dark">${r.list[i].ptitle}</h5>
                                            </a>
                                        </div>
                                        <div class="border-top border-primary-subtle p-3">
                                            <div id="progressBar${r.list[i].ppno}" class="progress mb-2" role="progressbar" aria-label="Success example" aria-valuenow="${r.list[i].participation}" aria-valuemin="0" aria-valuemax="1000" style="height:20px">
                                            <div id="progressBarWidth${r.list[i].ppno}" class="progress-bar bg-success">
                                                ${(r.list[i].participation/500)*100}%
                                            </div>
                                        </div>
                                            <p class="card-text m-0">${r.list[i].participation}명 참여중</p>
                                            <p class="card-text m-0 timeBox">D-DAY : ${dDay}일</p>
                                            <p class="card-text m-0">${r.list[i].regidate} ~ ${r.list[i].duedate}</p>
                                        </div>
                                    </div>`
            }
            petitionList.innerHTML = html;
            for(let j=0; j<r.list.length; j++){
                document.getElementById('progressBarWidth'+r.list[j].ppno).setAttribute('style',`width:${(document.getElementById(`progressBar${r.list[j].ppno}`).getAttribute('aria-valuenow')/500)*100}%;`);
            }
            pagination(r);
        }
    })
} // petWrite end

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="PetitiotionList(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="PetitiotionList(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="PetitiotionList(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}