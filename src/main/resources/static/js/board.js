let index = {
    init: function (){
        $("#btn-save").on("click", ()=>{ // function(){} 대신 ()=>{}을 사용, this 를 바인딩하기 위해
            this.save(); // function 을 사용하면 해당 this 가 window 를 가리킴
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",

        }).done(function (response) {
            // 요청 결과가 정상인 경우
            const status = response.status;

            if (status === 200){
                alert("글쓰기가 완료 되었습니다.");
                location.href = ("/");
            } else {
                alert(response.data);
            }

        }).fail(function (error) {
            // 요청 결과가 비정상인 경우
            alert(JSON.stringify(error));

        });
    },

    deleteById: function () {

        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id,
            dataType: "json",

        }).done(function (response) {
            // 요청 결과가 정상인 경우
            const status = response.status;

            if (status === 200){
                alert("삭제가 완료 되었습니다.");
                history.back();
                // location.href = ("/");
            } else {
                alert(response.data);
            }

        }).fail(function (error) {
            // 요청 결과가 비정상인 경우
            alert(JSON.stringify(error));

        });
    },

    update: function () {
        let id = updateId;

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",

        }).done(function (response) {
            // 요청 결과가 정상인 경우
            const status = response.status;

            if (status === 200){
                alert("글 수정이 완료 되었습니다.");
                history.back();
                // location.href = ("/");
            } else {
                alert(response.data);
            }

        }).fail(function (error) {
            // 요청 결과가 비정상인 경우
            alert(JSON.stringify(error));

        });
    },
}

index.init();
