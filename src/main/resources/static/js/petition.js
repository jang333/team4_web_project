let pageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};
PetitiotionList(1)
// 청원게시글 등록
function petWrite(){
    console.log ('petWrite()');

    // 필드값 가져오기
    let ptitle = document.querySelector('.ptitle').value;
    let pcontent = document.querySelector('.pcontent').value;

    // ajax 첨부파일 폼 전송
        $.ajax({
            url : "/up/write",
            method: "post",
            data : {'ptitle':ptitle, 'pcontent':pcontent},
            success : (r)=> {
                if(r){
                    alert('게시글 등록 완료');
                  location.href = '/up/pt/li'
                }else{
                     alert('게시글 등록 실패');
                   location.href = '/up/pt/li'
                }
            }
        }) // ajax end
} // petWrite end

// 청원게시글 출력
function PetitiotionList(page){
    pageObject.page = page;
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
            for(let i = 0; i < r.length ; i++){
                petitionList.innerHTML += `<div class="border rounded border-primary-subtle py-3 m-2 shadow petBox card">
                                            <div class="py-3 petitionbody card-body">
                                                <a href="/up/pt/vi?ppno=${r[i].ppno}" style="text-decoration: none;">
                                                    <h5 class="text-center card-title text-dark">${r[i].ptitle}</h5>
                                                </a>
                                            </div>
                                            <div class="border-top border-primary-subtle p-3">
                                                <div id="progressBar${r[i].ppno}" class="progress mb-2" role="progressbar" aria-label="Success example" aria-valuenow="${r[i].pstate}" aria-valuemin="0" aria-valuemax="1000" style="height:10px">
                                                <div id="progressBarWidth${r[i].ppno}" class="progress-bar bg-success text-dark" style="width:25%"></div>
                                            </div>
                                                <p class="card-text m-0">${r[i].participation}명 참여중</p>
                                                <p class="card-text m-0">기간</p>
                                                <p class="card-text m-0">${r[i].regidate} ~ ${r[i].duedate}</p>
                                            </div>
                                        </div>`
                document.getElementById('progressBarWidth').setAttribute('style',`width:${(document.getElementById(`progressBar${r[i].ppno}`).getAttribute('aria-valuenow')-document.getElementById(`progressBar${r[i].ppno}`).getAttribute('aria-valuemax')*100)}%;`);                
            }

        }
    })
} // petWrite end

// 2. 주소도 동일하고 매개변수도 동일할때
function plikeWrite( ppno , method ){    console.log( ppno ); console.log( method );
    let result = false;
    $.ajax({
        url : "/up/plike.do" ,
        method : method ,
        async : false ,
        data: { 'pno' : pno },
        success : function(r) { console.log( r );
            result = r;
        }
    });
    if( method != 'get' ) plikeView( ppno ); // 찜하기 변화 가 있을때
    return result;
}

// 3. 찜하기 상태 출력 함수 // 1., 사이드바 열릴때  2. 찜하기 변화가 있을때.
function plikeView( ppno ){
       // *현재 로그인 했고 찜하기 상태 여부 따라 css 변화
      let result = plikeWrite(  ppno , 'get'  );
      if( result  ){ // 로그인 했고 이미 찜하기 상태.
           document.querySelector('.sideBarBtnBox').innerHTML = `
                <button onclick="plikeWrite( ${ ppno } ,  'delete' )" type="button"> 찜하기 ♥</button>
                `
      }else{ // 로그인 안했거나 찜하기 안한 상태.
         document.querySelector('.sideBarBtnBox').innerHTML = `
              <button onclick="plikeWrite( ${ ppno } ,  'post' )" type="button"> 찜하기 ♡ </button>
              `
      }
}

// 클릭했을 때
function clickBtn() {

}






