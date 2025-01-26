

save auto inert or update..not replc..

bcs replace ,,,trigger weill trig...

3. 为什么 ORM 可能更新所有字段
   性能考虑：一些 ORM 框架（如早期的 Hibernate 或一些配置较低的框架）可能选择在 UPDATE 操作中更新所有字段，这样做可以避免在执行更新时需要检查哪些字段已经发生变化。但这种做法通常效率较低，尤其是当对象的字段较多时。
   简化实现：某些 ORM 框架（尤其是简单的框架）可能默认在 UPDATE 操作中更新所有字段，避免了复杂的差异计算和管理。