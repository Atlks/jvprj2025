
我从jwt获取username后，是注入到 哪种模式比较好

第一，注入到 jakarta.security.enterprise.SecurityContext;

还有个azure serverless ，easy auth模式，是注入到 
X-MS-CLIENT-PRINCIPAL-NAME



我们来分两种情况对比分析下你提到的两种方式：
jakarta.security.enterprise.SecurityContext（标准 Java EE 安全机制）和 X-MS-CLIENT-PRINCIPAL-NAME（Azure Easy Auth 提供的机制）。