

# jqpl
# 🛠️ Criteria API（类型安全的动态查询）
java
复制
编辑
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
Root<Employee> root = cq.from(Employee.class);
cq.select(root).where(cb.equal(root.get("status"), "ACTIVE"));
功能	方法调用示例
创建查询对象	CriteriaQuery<T> cq = cb.createQuery(T.class)
根对象	Root<T> root = cq.from(T.class)
条件拼接	cb.equal(), cb.like(), cb.gt(), cb.and(), cb.or()
排序	cq.orderBy(cb.desc(root.get("id")))
聚合函数	cb.count(), cb.avg(), cb.sum()
分组与过滤	cq.groupBy(root.get("dept")), cb.having(cb.gt(...))
子查询	Subquery<T> sub = cq.subquery(T.class)
JOIN 关联	root.join("department") 或 root.fetch(...)
DISTINCT	cq.select(root).distinct(true)
执行查询	entityManager.createQuery(cq).getResultList()





✅ 2. 使用 Spring Data JPA 分页接口（推荐）
方法签名中加入 Pageable 参数：
java
复制
编辑
Page<Employee> findByStatus(String status, Pageable pageable);
使用方式示例：
java
复制
编辑
Pageable pageable = PageRequest.of(2, 10, Sort.by("createdAt").descending());
Page<Employee> page = employeeRepository.findByStatus("ACTIVE", pageable);

List<Employee> employees = page.getContent();
int totalPages = page.getTotalPages();
long totalItems = page.getTotalElements();



在 Spring 框架中，分页通常通过 org.springframework.data.domain.Page、Pageable、PageRequest 等类来实现，特别是在使用 Spring Data JPA 的时候。

🧩 主要类介绍
类名	作用
Page<T>	分页结果（包含内容、总页数、总条数等）
Pageable	分页请求参数（页码、大小、排序）接口
PageRequest	Pageable 的实现类，构建分页参数