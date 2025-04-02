如何解析这个内容：
boundary：所有的部分都通过 boundary 进行分隔，这个 boundary 是通过 Content-Type 头中的 boundary 参数给出的。在你的例子中，boundary 是 geckoformboundary34b49dbfd9a4a64fa922e966447ac390。


表单部分结构：

每个部分以 boundary 开始，包含一个 Content-Disposition 头，指示字段名称、文件名等。

如果是文件字段，则还有一个 Content-Type 头，指示文件的 MIME 类型。

然后是文件的实际内容（如果是文件上传的话）。

步骤：

步骤：

首先，你需要从请求的 Content-Type 头部提取 boundary。

然后你按 boundary 切割请求体，得到每个部分。

对于每个部分，你解析 Content-Disposition，确定它是字段还是文件字段。

如果是文件字段，则提取文件内容并保存。


------geckoformboundary34b49dbfd9a4a64fa922e966447ac390
Content-Disposition: form-data; name="file"; filename="download (1).jpg"
Content-Type: image/jpeg

ÿØÿà JFIF      ÿÛ „ 	( %!1!%)+...383-7(-.+



-+--+-+----+---+--------+-+--7---7+-77--7+77-7+++++ÿÀ  ¨," ÿÄ             ÿÄ :  
±'-£¿TÖ,MÖýdu7\¹6PxëÿÙ
------geckoformboundary34b49dbfd9a4a64fa922e966447ac390--