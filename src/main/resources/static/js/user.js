let index = {
    init: function (){
        $("#btn-save").on("click", ()=>{ // function(){} 대신 ()=>{}을 사용, this 를 바인딩하기 위해
            this.save(); // function 을 사용하면 해당 this 가 window 를 가리킴
        });

        $("#btn-login").on("click", ()=>{
            this.login();
        })
    },

    login: function (){
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
        }

        $.ajax({
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",

        }).done(function (response){
            console.log(response);
            const status = response.status;

            if (response.status === 200){
                alert("로그인이 완료 되었습니다.");
                location.href = ("/");
            } else {
                alert("올바른 정보를 입력해주세요.")
                throw new Error();
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    save: function () {
        // alert("user의 save함수 호출됨");
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        // console.log(data);

        // ajax 호출 시 default 가 비동기 호출
        // ajax 통신을 이용하여 3개의 데이터를 json 으로 변경하여 insert 요청
        // ajax 통신을 성공하고 서버가 json 형태의 데이터를 리턴하면 자동으로 자바 Object 로 변환
        $.ajax({
            // 회원가입 수행을 요청
            type: "POST",
            url: "/api/user",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터 타입 (MIME)
            dataType: "json", // 응답된 데이터가 json 형태라면 -> javascript object 로 변경

        }).done(function (response) {
            // 요청 결과가 정상인 경우
            const status = response.status;

            if (status === 200){
                alert("회원가입이 완료 되었습니다.");
                location.href = ("/");
            } else {
                throw new Error();
            }

        }).fail(function (error) {
            // 요청 결과가 비정상인 경우
            alert(JSON.stringify(error));

        }); // ajax 통신을 이용해서 3개의 데이터를 json 으로 변경하여 insert 요청
    }
}

index.init();
