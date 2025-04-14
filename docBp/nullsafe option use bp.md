


Optional 使用速查表
✅ 使用场景	❌ 不推荐使用场景
✔️ 方法返回值 可能为空，需要明确表达“可能没有”	❌ 方法参数类型（不推荐 Optional 作为参数）
✔️ 想表达“值的缺失是正常的”	❌ 类字段/成员变量（如 POJO、DTO）
✔️ 链式调用，避免嵌套 if (x != null)	❌ 需要高性能、频繁调用的逻辑（Optional 有一定开销）
✔️ 和 Stream 搭配使用（如 .findFirst()）	❌ 和框架集成对象使用（如 JPA 实体类字段）
✔️ 用在外部接口返回值、SDK、API 的设计中，明确表示“不一定有值”	❌ 本地局部变量使用，仅仅是为了省 null 判断（不值得）
✔️ 想让调用方“必须显式处理没有值的情况”	❌ 替代异常或错误码使用（不是为错误处理设计的）
🚦 快速判断流程图

这个值可能为 null 吗？
│
├─ 否 → 不用 Optional
│
└─ 是 →
├─ 是字段？→ ❌ 不推荐用 Optional
├─ 是方法参数？→ ❌ 不推荐用 Optional
├─ 是返回值？→ ✅ 推荐用 Optional
└─ 是链式处理场景？→ ✅ 非常合适！

💡 使用示例建议
场景	示例	是否推荐
查询用户	Optional<User> findById(Long id)	✅ 推荐
用户类字段	Optional<String> username;	❌ 不推荐
方法参数	void setName(Optional<String> name)	❌ 不推荐
流处理	list.stream().findFirst()	✅ 推荐
简单变量	Optional<String> name = Optional.of("Tom")	⚠️ 仅在表达语义需要时使用



🔧 如果不使用 Optional，该怎么办？

    可以使用 @NonNull / @Nullable 注解标明期望

    用文档 / 注释清晰说明可能为 null

    在业务逻辑中用 assert 或 Objects.requireNonNull 防御式编程