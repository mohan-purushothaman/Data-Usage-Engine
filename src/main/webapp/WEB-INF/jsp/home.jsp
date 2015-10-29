<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .bar {
    height: 18px;
    background: green;
}
</style>
    
<h3>
    UploadFile
</h3>
<div>
    
    
    <input id="fileupload" type="file" name="file" data-url="<c:url value="/uploadFile"/>" >


    <div id="progress">
    <div class="bar" style="width: 0%;"></div>
    <div id="responseDiv"></div>
</div>
    
    
    
    <script>
$(function () {
    $('#fileupload').fileupload({
   
        done: function (e, data) {
               
                $('#responseDiv').append('<p/>').text(data.result);
            
        },
    progressall: function (e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .bar').css(
            'width',
            progress + '%'
        );
    }
});
});
</script>
</div>
