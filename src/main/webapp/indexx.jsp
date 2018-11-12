<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="jss/jquery.js"></script>
<script type="text/javascript" src="jss/common.js"></script>
<head>


<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Hello World!!!!!!</h2>


<input type="button" onclick="TestForm()" value="提交" />

<div id="test"></div>

</body>
<script type="text/javascript">
	function TestForm() {
		//var formData=new FormData($("#form")[0]);
		console.log("start");
		$.ajax({
			url : "music/user//test.do",
			data : null,
			dataType : null,
			type : "post",
			success : function(data) {
				alert(data.msg);
				var context = "<h1>" + data.msg + "</h1>";
				$("#test").append(context);
			},
			error : function(data) {
				alert("error");
			}
		});
		console.log("end");
	}
</script>
</html>