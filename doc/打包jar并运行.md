

shade会包含依赖


你可以不用 shade，但 JAR 里不会自动包含依赖。

maven-jar-plugin 让 JAR 变可执行。

运行时需要 手动复制依赖 或 用 maven-assembly-plugin 生成 jar-with-dependencies.jar。




如果你的 JAR 需要依赖库：

    shade → 合并所有依赖到一个 JAR，可以 java -jar 直接运行。

    不用 shade → 需要用 -cp 指定 lib/ 目录里的 JAR。