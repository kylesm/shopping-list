<html>
<head>
	<title>Log in to shopping list</title>
	<meta name="layout" content="main"/>
	<r:require modules="core"/>
</head>
<body>
	<div data-role="page" id="login">
		<div data-role="header">
			<h1>Login to Shopping List</h1>
		</div>
		<g:if test="${flash.message}">
			<div data-role="content" id="errorPane" data-theme="a" class="ui-bar">
				${flash.message}
			</div>
		</g:if>
		<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
			<form action="${postUrl}" method="POST" id="loginForm">
				<label for="username">Username:</label>
				<input type="text" data-clear-btn="true" name="j_username" id="username">
				<label for="password">Password:</label>
				<input type="password" name="j_password" id="password" autocomplete="off">
				<input type="checkbox" name="${rememberMeParameter}" id="remember_me" <g:if test="${hasCookie}">checked="checked"</g:if>>
				<label for="remember_me">Remember me</label>

				<input type="submit" id="submit" value="Login">
			</form>
		</div>
	</div>
</body>
</html>
