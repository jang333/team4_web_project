console.log("달력 js")
/*
//
    1. document : HTML에 관련된 기능 제공해주는 객체
        .querySelector('선택자') : query(질의)Selector(선택)
        .innerHTML = 문자열데이터   : <마크업> </마크업>

    2. new Date()
        new Date()                  : 현재 날짜/시간 객체
        new Date( 연도 , 월 , 일 )  : 대입한 연도/월/일 기준으로 날짜 구성
                        - 일(day) 자리에 0 대입하면 전 달의 마지막 날짜
        - 함수
        .getFullYear()  : 현재날짜의 연도
        .getMonth()     : 현재날짜의 월 ( 0:1월  ~ 11:12월 )
        .getDate()      : 현재날짜의 일
        .getDay()       : 현재날짜의 요일( 0:일요일 ~ 6:토요일 )
*/

// ================== 오늘 날짜/시간 객체 : new Date() ============
console.log( new Date() );
let 현재연도 = new Date().getFullYear();    // 오늘 날짜에서 '연도' 만 추출
let 현재월 = new Date().getMonth()+1;         // 오늘 날짜에서 '월'만 추출 [ 0:1월 1:2월 2:3월 ] +1 [ 1:1월 ]

console.log( 현재연도 ); console.log( 현재월 );

// ================== 여러개 스케줄 객체(내용과날짜) 가 담긴 배열  ============
let 스케줄목록 = [
                 { 'stdate' : '2024-03-07' , 'sccontent' : '강의종강' , 'scolor' : '#e8e8e8' }
                 ]; // 배열 선언
schedulList(0);
function schedulList(page){
console.log("schedulList() 함수실행")
    $.ajax({
        type: "get",
        url: "/up/sch/info",
        data : {'page':page, 'pageSize' : 0},
        success: (r) => {
            console.log(r)
            스케줄목록 = r.list;
            연도월출력함수();
        }
    });
}


function 연도월출력함수(){ // 함수정의
    // =================== 달력 상단 =============== //
    // 1. 어디에
    let calDate = document.querySelector('.calDate');
    // 2. 무엇을
    let dateHTML = ` ${ 현재연도 } 년 ${ 현재월 } 월`;    // `(백틱) 사이에 문자입력이 가능. 백틱 사이에 변수/객체/배열/함수실행 ${ } 안에 대입
    // 3. 대입
    calDate.innerHTML = dateHTML;

    // ==================== 달력 =================== //

    // (1). 현재 월의 마지막 일 구하기. Date( 연도 , 월 , 일 ) : 대입한 연도/월/일 기준으로 날짜를 구성
        // Date( 2024 , 4 , 1 ) : 2024-3-1   ,   Date( 2024 , 4 , 0 )  : 2024-2-29
    let now = new Date( 현재연도 , 현재월 , 0 );  // 일(day)에 0을 대입하면 1일 전 날짜
    let endDay = now.getDate();         console.log( endDay );  // 2024-04-01 의 전날인 2024-03-31 에서 31

    // (2). 현재 날짜에서 1일의 요일 구하기
    let now2 = new Date( 현재연도 , 현재월-1 , 1 ); // 현재연도 월에 해당하는 1일 날짜
    let startWeek = now2.getDay();      console.log( startWeek ); // 2024-03-01 의 요일은 5(금요일)

    // 1. 어디에
    let calender = document.querySelector('.calender')
    // 2. 무엇을
        // 1. 요일 HTML
    let calenderHTML = `<div class="week sunday">일</div>
                        <div class="week">월</div>  <div class="week">화</div>
                        <div class="week">수</div>  <div class="week">목</div>
                        <div class="week">금</div>  <div class="week">토</div>`;

        // 2. 1일 전까지의 공백 날짜 HTML
    for( let blank = 1 ; blank <= startWeek ; blank++  ){
        calenderHTML += `<div> </div>`
    }
        // 3. 일 HTML
    for( let day = 1 ; day <= endDay ; day++ ){ // 연도월 마다 마지막일 다르므로.
            // day는 1부터 31 까지 1씩 증가하면서 반복

            // 일 구역 한칸 시작
        calenderHTML += `<div> ${day}`

                    // 스케줄 텍스트 출력 ( 배열내 데이터 출력 = 현재 날짜와 스케줄 날짜와 동일하면 )
            for( let i = 0 ; i < 스케줄목록.length ; i++ ){
                // i는 0부터 배열내 마지막 순서(인덱스)까지 1씩 증가하면서 반복
                let 스케줄 = 스케줄목록[i]; // 스케줄 목록에서 i번째 스케줄 호출

                if( 스케줄.stdate == `${ 현재연도 }-${ 현재월 < 10 ? "0"+현재월 : 현재월 }-${ day < 10 ? "0"+day : day }` ){
                    calenderHTML += `<div style="background-color:${스케줄.scolor}"> ${ 스케줄.sccontent } </div>`
                }

            }  // end

            // 일 구역 한칸 종료
        calenderHTML += `</div>`

        // += : 복합대입연산자 : (누적) 오른쪽값을 왼쪽에 더한후에 왼쪽변수에 대입
    }

    // 3. 대입
    calender.innerHTML = calenderHTML;

} // f end

// 2. 화살표 버튼을 클릭해서 현재 월을 증가/감소 함수
function 월증감( 타입 ){
    console.log( '월증가감소 함수 실행');
    // 2-1 만약에 타입이 0 이면 월 감소 , 타입이 1 이면 월 증가
        // if 조건문 : if( 조건 ){ true }else{ false }
    if( 타입 == 0 ){
        현재월 = 현재월-1;
        if( 현재월 < 1 ){ // 만약에 현재월 을 감소 했을떄 1보다 작아지면 12월로 설정 하고 연도를 1차감
            현재월 = 12;
            현재연도 = 현재연도 - 1;
        }
    }
    else{
        현재월 = 현재월+1;
        if( 현재월 > 12 ){
            현재월 = 1;
            현재연도 = 현재연도 + 1;
        }
    }
    // 2-2 월 변수가 변경되었기 때문에 연도월 출력도 다시.. 새로고침
    연도월출력함수(); // 해당 코드를 가지고 있는 함수를 실행.
} // f end