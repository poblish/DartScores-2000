<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file = "headertags.html" %>
	<title>Hiatus One Error Page</title>
</head>

<body>
	<h2>The Error message:</h2>
	<hr>
	<p class="emphasis"> <%= exception.toString() %> </p>
	<p class="main"> <% if (exception != null) exception.printStackTrace(); %> </p>
</body>

</html>