<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>

<button id="addOriginalProblem">添加原创题目</button>
<script src="/static/js/jquery/jquery-3.3.1.min.js" ></script>
<script language="JavaScript">
    $(function() {
        $("#addOriginalProblem").on("click", function(){
            alert("点击");
            var str = '{"id":null,"output":"输出","input":"输入","extension":"{}","sampleInput":"输入样例","timeLimit":2000,"sampleOutput":"输出样例","description":"题目描述","memoryLimit":65535,"title":"题目标题"}';
            $.ajax({
                type: "post",
                url: "/originalProblem/add",
                data: {originalProblemJson: str},
                dataType: "json",
                success: function(data){
                }
            });
        });
    });
</script>

</body>
</html>
