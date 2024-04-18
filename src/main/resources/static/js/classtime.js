//페이지정보
let timePageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};
console.log(timePageObject);
classTime(1);
// ====================================== 강의 시간 ====================================== //
let putTime = ""; //강의시간 보낼 데이터
console.log(putTime);

//강의시간 등록
function putEduTime(){
console.log("putEduTime()")

$.ajax({
    url : "/up/puttime",
    method : "post",
    async:false,
    data : putTime,
    success : (r)=>{
        console.log(r)
        classTime(1);
    }
})
}

//강의시간 중복 확인
function checkEduTime(){
console.log("checkEduTime()");

let dayweek = document.querySelector('.dayweek').value;       console.log(dayweek);
let starttime = document.querySelector('.starttime').value;
let endtime = document.querySelector('.endtime').value;

putTime = {dayweek : dayweek, starttime : starttime, endtime: endtime};
console.log(putTime);

if((dayweek.length != 0 || dayweek !="")&&(starttime.length != 0 || starttime!="")&&(endtime.length != 0 || endtime!="")){
console.log("if통과")
    $.ajax({
        url : "/up/checktime",
        method : "get",
        async:false,
        data : putTime,
        success : (r)=>{
            console.log(r)
            if(r){
                alert("중복된 시간이 있습니다.");
            }else{
                alert("등록이 완료되었습니다.");
                putEduTime();
            }
        }

    }); //ajax e
}else if(dayweek.length == 0||dayweek ==""){
    console.log(dayweek);
    console.log("if 안됨");
    alert("요일을 입력해주세요.");
}else if(starttime.length == 0||starttime ==""){
    alert("강의 시작시간을 입력해주세요.");
}else if(endtime.length == 0||endtime ==""){
    alert("강의 끝시간을 입력해주세요.");
}else{
    alert("관리자에게 문의하세요.");
}
}//f e



//강의시간출력
function classTime(page){
console.log("강의시간출력")


timePageObject.page = page; //매개변수페이지를 현재페이지에 대입
console.log(page);

$.ajax({
    url:"/up/classtime",
    method : "get",
    async:false,
    data : timePageObject,
    success : (r)=>{
        console.log(r)
        //어디에
        let classtime = document.querySelector(".classTimeBox > tbody");
        //무엇을
        let html="";
        for(let i = 0 ; i < r.list.length ; i++){
            html+=`<tr class="reClassTime${r.list[i].tno} text-center">
                <th scope="row" class="text-center">${r.list[i].tno}</th>
                <td>${r.list[i].dayweek}</td>
                <td>${r.list[i].starttime}교시</td>
                <td>${r.list[i].endtime}교시</td>
                <td class="text-center" id="editBtn">
                    <button class="btn btn-outline-secondary btn-sm" type="button" onclick = "doInputClassTime('${r.list[i].tno}','${r.list[i].dayweek}','${r.list[i].starttime}','${r.list[i].endtime}')">
                        Edit
                    </button>
                </td>
                </tr>`
        }
        //출력
        classtime.innerHTML = html;
        // ---------- 페이지네이션 -----------------------------------------------------------------
        doPaging(r);
        //---------- 페이지네이션 -----------------------------------------------------------------
    }
});
}

//페이징처리 함수
function doPaging(r){
    console.log(timePageObject);
    let object = timePageObject;
    console.log(object)
    //어디에
        let pagination = document.querySelector(".pagination");
        //무엇을
        let pagehtml = "";
        pagehtml += `<li class="page-item">
                     <a class="page-link" onclick="classTime(${object.page-1 < 1 ? 1 : object.page-1})" aria-label="Previous">
                         <span aria-hidden="true">&laquo;</span>
                     </a>
                     </li>`
        for(let i = r.startbtn ; i <= r.endbtn ; i++){
            pagehtml += `<li class="page-item"${i == object.page ? 'active' : '' }>
            <a class="page-link" onclick="classTime(${i})">${i}</a></li>`
        }

        pagehtml +=`<li class="page-item" >
                        <a class="page-link" onclick="classTime(${object.page+1>r.totalpage? r.totalpage : object.page+1})" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>`
        //출력
        pagination.innerHTML = pagehtml;

}


//강의시간 수정 input박스
function doInputClassTime(tno, dayweek, starttime, endtime){
  //어디에
  let classtime = document.querySelector(".reClassTime"+tno);
  //무엇을
  let html=`<th scope="row" class="text-center">${tno}</th>
          <td>
            <select class="reDayweek${tno} form-select w-50 me-2 value=${dayweek}">
                <option value="월">월</option>
                <option value="화">화</option>
                <option value="수">수</option>
                <option value="목">목</option>
                <option value="금">금</option>
            </select>
          </td>
         <td>
            <select class="reStarttime${tno} form-select w-50 me-2 value=${starttime}">
                <option value="1">09:00</option>
                <option value="2">10:00</option>
                <option value="3">11:00</option>
                <option value="4">12:00</option>
                <option value="5">13:00</option>
                <option value="6">14:00</option>
                <option value="7">15:00</option>
                <option value="8">16:00</option>
                <option value="9">17:00</option>
                <option value="10">18:00</option>
            </select>
         </td>
         <td>
            <select class="reEndtime${tno} form-select w-50 me-2 value=${endtime}">
                <option value="1">09:50</option>
                <option value="2">10:50</option>
                <option value="3">11:50</option>
                <option value="4">12:50</option>
                <option value="5">13:50</option>
                <option value="6">14:50</option>
                <option value="7">15:50</option>
                <option value="8">16:50</option>
                <option value="9">17:50</option>
                <option value="10">18:50</option>
            </select>
         </td>
         <td class="text-center">
            <button type="button" class="btn btn-secondary btn-sm" onclick = "doUpdateClassTime(${tno})">수정</button>
         </td>`
  //출력
      classtime.innerHTML = html;
}

//강의 수정
function doUpdateClassTime(tno){
console.log("doUpdateClassTime")
   console.log(tno)
    let reDayweek = document.querySelector('.reDayweek'+tno).value;
    console.log(reDayweek)
    let reStarttime = document.querySelector('.reStarttime'+tno).value;
    console.log(reStarttime)
    let reEndtime = document.querySelector('.reEndtime'+tno).value;

    let rePutTime = {'dayweek' : reDayweek, 'starttime':reStarttime, 'endtime':reEndtime,'tno':tno}

   $.ajax({
       url : "/up/updatetime",
       method : "put",
       data : rePutTime ,
       async:false,
       success : (r) => {
           console.log(r)
           classTime(1);
       }
   })
}

$.ajax({
    method: "get",
    async : false,
    url: "/member/login/check",
    success: function (id) {
        $.ajax({
            url : '/member/login/info',
            method : 'get',
            data : {'id' : id},
            async : false,
            success : (logindto) => {
                if(logindto.state == 1){
                    let editBtn = document.querySelectorAll('#editBtn')
                    for(let i=0; i<editBtn.length; i++){
                        editBtn[i].innerHTML ='';
                    }
                }else if(logindto.state == 2){
                    let editBtn = document.querySelectorAll('#editBtn')
                    for(let i=0; i<editBtn.length; i++){
                        editBtn[i].innerHTML ='';
                    }
                }       
            }
        });
    }
})