$('#upload-image-form').submit( function(e) {
    e.preventDefault();

    var data = new FormData(this); // <-- 'this' is your form element

    $.ajax({
            url: 'http://localhost:8082/api/v1/image',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(data, status, xhr) {
                console.log(data)
                if (data.code === 200) {
                    alert("Upload image successfully")
                    location.reload()
                }
                else {
                    alert(data.message)
                }
            },
            error(xhr,status,error){
                console.log(error)
            }
        }
    )
})