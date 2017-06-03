<%--
  Created by IntelliJ IDEA.
  User: fy
  Date: 2017/6/2
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script>
      var ws;
      if ('WebSocket' in window) {
        ws = new WebSocket("ws://localhost:8080/trader/future");
        console.log("websocket in window");
      }
    </script>
  </head>
  <body>
  $END$
  </body>
</html>
