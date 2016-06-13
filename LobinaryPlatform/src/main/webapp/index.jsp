<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>欢迎访问Lobinary网站</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <style type="text/css">
        @font-face {
            font-family: digit;
            src: url('digital-7_mono.ttf') format("truetype");
        }
    </style>
    <link href="style/default.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="style/jquery.js"></script>
    <script type="text/javascript" src="style/garden.js"></script>
    <script type="text/javascript" src="style/functions.js"></script>
</head>

<body>
    <div id="mainDiv">
        <div id="content">
            <div id="code">
                <span class="comments">/**</span><br />
                <span class="space"/><span class="comments">* 我们正在准备组建一个网站团队</span><br />
                <span class="space"/><span class="comments">* 搭建一个网站运行起来很容易</span><br />
                <span class="space"/><span class="comments">* 但是如果想要让每个人都想用它确是非常难的.</span><br />
                <span class="space"/><span class="comments">* 所以，如果你对这个团队或者网站感兴趣，希望您的加入.</span><br />
                <span class="space"/><span class="comments">*/</span><br />
              		联系方式 <span class="keyword"> contact1 =  </span> new QQ群(<span class="string">"202254695"</span>);<br />
                	联系方式<span class="keyword"> contact2 = </span> new 邮箱(<span class="string">"crazyteamsystem@sina.com"</span>);<br />
                <span class="comments">// April 7, 2016,就在今天， 赶快加入我们吧. </span><br />
            </div>
            <div id="loveHeart">
                <canvas id="garden"></canvas>
                <div id="words">
                    <div id="messages">

                        &nbsp;&nbsp;我将为这个团队贡献自己的一份力量！
                        <div id="elapseClock" style="display:none"></div>
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='views/team/add_team.html' id="accept"> 点击我 同意加入这个团队</a>
                    </div>
                    <div id="loveu">
                        诚邀您的加入<br/>
                        <div class="signature">- Crazy Team</div>
                    </div>
                </div>
            </div>
        </div>
        <div id="copyright">
             <a href="http://www.lobinary.com/">Crazy Team</a><br />
        </div>
    </div>
    <script type="text/javascript">
        var offsetX = $("#loveHeart").width() / 2;
        var offsetY = $("#loveHeart").height() / 2 - 55;
        
        if (!document.createElement('canvas').getContext) {
            var msg = document.createElement("div");
            msg.id = "errorMsg";
            msg.innerHTML = "Your browser doesn't support HTML5!<br/>Recommend use Chrome 14+/IE 9+/Firefox 7+/Safari 4+"; 
            document.body.appendChild(msg);
            $("#code").css("display", "none")
            $("#copyright").css("position", "absolute");
            $("#copyright").css("bottom", "10px");
            document.execCommand("stop");
        } else {
            setTimeout(function () {
                adjustWordsPosition();
                startHeartAnimation();
            }, 10000);
            
            $("#accept").click(function(){
                $(this).hide();
                $("#elapseClock").show();
                var together = new Date();
                timeElapse(together);
                setInterval(function () {
                    timeElapse(together);
                }, 500);
            })
            adjustCodePosition();
            $("#code").typewriter();
        }
    </script>
</body>
</html>
