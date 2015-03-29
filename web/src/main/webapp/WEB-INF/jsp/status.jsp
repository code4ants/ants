<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lords of the Ants</title>
    <script src="//code.jquery.com/jquery-2.1.3.min.js"></script>
    <script src="/static/js/lords.of.the.ants.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/lords.of.the.ants.css">
    <script language="JavaScript">
        $(function() {
            AntGame.init($('#board'), $('#ladder'));
        })
    </script>
</head>
<body>
        <div id="main-holder">
            <div id="ladder-holder">
                <div id="ladder-title">Ladder</div>
                <div id="ladder"></div>
            </div>
            <div id="board"></div>
            <div id="buttons-holder">
                <a class="button" id="btnStart">Start</a>
                <a class="button" id="btnStop">Stop</a>
                <div id="status-holder"></div>
            </div>
        </div>
</body>
</html>
