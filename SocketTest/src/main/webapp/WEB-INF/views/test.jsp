<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>test page</h1>
	<div>
		<input type="text" id="inputMSG">
		<button type="button" id ="btn">전송</button>
	</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	var ws = new WebSocket("ws://localhost:8081/socket");
	console.log(ws);
	
	ws.onopen = function(e){
		console.log("info : connection opened.");
		console.log(e.data);
	}
	
	ws.onmessage = function(e){
		console.log(e.data);
	}
	
	ws.onclose = function(e){
		console.log("info : connection closed")
	};
	
	ws.onerror = function(e){console.log("error")};
	
	
	function send(){
		var text = document.getElementById("inputMSG").value;
		ws.send(text);
	}
	
	$("#btn").on("click",function(){
		send();
	})
	
</script>
</html>