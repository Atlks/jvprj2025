function wall(){
 // 圆的参数
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const radius = rds*beishu; // 半径 = 直径 / 2
    console.log("rds="+radius)

    // 绘制绿色填充的圆
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, 0, Math.PI * 2);
    ctx.fillStyle = "yellow";
    ctx.fill();

     // 设置褐色边框
        ctx.strokeStyle = "brown"; // 颜色
        ctx.lineWidth = 5; // 线条宽度
        ctx.stroke(); // 画边框


    ctx.closePath();

}


function wall2(radius){
 // 圆的参数
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
  //  const radius = rds*beishu; // 半径 = 直径 / 2
    console.log("rds="+radius)

    // 绘制绿色填充的圆
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, 0, Math.PI * 2);
    ctx.fillStyle = "green";
    ctx.fill();


    // 设置褐色边框
            ctx.strokeStyle = "brown"; // 颜色
            ctx.lineWidth = 20; // 线条宽度
            ctx.stroke(); // 画边框


    ctx.closePath();

}


//紫色区域
function anml(radius){
 // 圆的参数
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
  //  const radius = rds*beishu; // 半径 = 直径 / 2
    console.log("rds="+radius)

    // 绘制 填充的圆
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, 0, Math.PI * 2);
    ctx.fillStyle = "purple"; // 紫色区域
    ctx.fill();


    // === 添加文本 ===
        ctx.fillStyle = "black"; // 文字颜色
        ctx.font = "30px Arial"; // 字体大小和样式
        ctx.textAlign = "center"; // 文字居中对齐
        ctx.textBaseline = "middle"; // 文字基线居中
        ctx.fillText("anmls", centerX-200+100, centerY); // 在圆心绘制文字


  // 设置褐色边框
        ctx.strokeStyle = "brown"; // 颜色
        ctx.lineWidth = 10; // 线条宽度
        ctx.stroke(); // 画边框

    ctx.closePath();

}

//ati center
function waterCenter(radius){
 // 圆的参数
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
  //  const radius = rds*beishu; // 半径 = 直径 / 2
    console.log("rds="+radius)

    // 绘制绿色填充的圆
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, 0, Math.PI * 2);
    ctx.fillStyle = "green";
    ctx.fill();


    // === 添加文本 ===
        ctx.fillStyle = "black"; // 文字颜色
        ctx.font = "30px Arial"; // 字体大小和样式
        ctx.textAlign = "center"; // 文字居中对齐
        ctx.textBaseline = "middle"; // 文字基线居中
        ctx.fillText("a1k", centerX, centerY); // 在圆心绘制文字


 // 设置褐色边框
        ctx.strokeStyle = "blue"; // 颜色
        ctx.lineWidth = 10; // 线条宽度
        ctx.stroke(); // 画边框

    ctx.closePath();

}