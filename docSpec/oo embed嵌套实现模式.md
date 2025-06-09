

Hibernate / JPA 中，实现“组件对象嵌套”的方式有几种，下面是各种典型模式和它们的用途：

✅ 1. @Embedded / @Embeddable（嵌入式对象）
这是最常见的方式，适用于多个实体中复用的一组字段，共用主表，不生成新表。


✅ 2. @ElementCollection（嵌套集合对象，多值组件）
用于一个实体中包含多个组件实例，如 List/Set，并由 JPA 生成一个独立的关联表。

✅ 3. @OneToOne / @OneToMany + @Entity（单独实体关联）
如果嵌套对象需要自己有生命周期、主键或能独立持久化，那么使用实体间关联（不是组件）更合适。


✅ 4. @MappedSuperclass（共享字段基类）
不是嵌套字段，但用于多个实体共用字段结构（继承用），不会映射为独立表或字段。


✅ 5. @AssociationOverride / @AttributeOverride（嵌套字段自定义列名）
在 @Embedded 上微调列名：