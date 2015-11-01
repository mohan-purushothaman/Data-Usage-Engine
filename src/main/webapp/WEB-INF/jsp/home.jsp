<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .bar {
        height: 18px;
        background: green;
    }
    
    
      .ui-progressbar {
    position: relative;
  }
  #progress-label {
    position: absolute;
    left: 50%;
    top: 4px;
    font-weight: bold;
    text-shadow: 1px 1px 0 #fff;
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

        <div id="progressbar">
            <div id="progress-label">Loading...</div>
        </div>
    </div>



    <script>
        $(function () {
            $('#fileupload').fileupload({
            done: function (e, data) {

                $('#responseDiv').append('<p/>').text(data.result);
                var inter = setInterval(function () {
                    $.post('<c:url value="/currentProgress"/>', {}, function (d) {
                        if (d.total===0) {
                            clearInterval(inter);
                            $('#progress-label').text(d.msg);
                        } else {
                            $("#progressbar").progressbar({
                                value: ((d.completed * 100) / d.total)
                            });
                            $('#progress-label').text(((d.completed * 100) / d.total)+" %");
                        }
                    });
                }, 5000);

            }
            ,
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
