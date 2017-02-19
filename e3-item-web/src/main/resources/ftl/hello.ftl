<html>

<head>

    <title>标题</title>

</head>
<body>
${hello} world![BR]<br />

今天的日期：${today?date} &nbsp;&nbsp;&nbsp;&nbsp;现在的时间：${today?time}
<br>
今天的日期和时间：${today?datetime}
<br>
自定日期格式：${today?string('yyyy/MM/dd HH:mm:ss')}

${hello!''}<br />
${helloo!''}<br/>
${mouse!'No mouse'}
<#assign mouse="jerry">
${mouse}
</body>

</html>


