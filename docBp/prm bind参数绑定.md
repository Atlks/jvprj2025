


请求参数绑定

这些注解用于绑定 HTTP 请求参数到方法参数。

(1) @PathVariable

    绑定 URL 路径变量到方法参数。

(2) @RequestParam

    绑定 查询参数 到方法参数。

(4) @ModelAttribute

    绑定表单提交的数据（通常用于 application/x-www-form-urlencoded）。


3. 返回数据

用于控制 API 的返回值格式。
(1) @ResponseBody

    指定返回值 自动转换为 JSON 或 XML（Spring MVC 默认使用 Jackson 处理 JSON）。

@ResponseStatus

    指定 HTTP 响应状态码。