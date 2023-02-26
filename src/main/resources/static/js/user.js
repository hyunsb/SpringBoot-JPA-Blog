let index = {
    init: function (){
        $("#btn-save").on("click", ()=>{ // function(){} 대신 ()=>{}을 사용, this 를 바인딩하기 위해
            this.save(); // function 을 사용하면 해당 this 가 window 를 가리킴
        });
    },

    save: function (){
        // alert("user의 save함수 호출됨");
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        }
        // console.log(data);

        // ajax 호출 시 default 가 비동기 호출
        // ajax 통시을 이용하여 3개의 데이터를 json 으로 변경하여 insert 요청
        $.ajax({
            // 회원가입 수행을 요청
            type: "POST",
            url: "/blog/api/user",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터 타입 (MIME)
            dataType: "json", // 응답된 데이터가 json 형태라면 -> javascript object 로 변경

        }).done(function (response){
            // 요청 결과가 정상인 경우
            alert("회원가입이 완료 되었습니다.");
            console.log(response);
            // location.href = "/blog";

        }).fail(function (error){
            // 요청 결과가 비정상인 경우
            alert(JSON.stringify(error));

        }); // ajax 통신을 이용해서 3개의 데이터를 json 으로 변경하여 insert 요청
    }
}

index.init();
