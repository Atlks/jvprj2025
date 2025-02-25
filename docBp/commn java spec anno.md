
=rest  jax rs
=java vld bean   @nutnull


=store  jpa  jdo


AOP


、依赖注入  @inject

、测试 

=doc  @Documented

生成 Javadoc 时包含该注解信息

2. JAX-RS（Java API for RESTful Web Services）

用于构建 REST API：
注解	作用
@Path	标记 REST 资源的路径，如 @Path("/users")
@GET / @POST / @PUT / @DELETE	标记 HTTP 请求方法
@Produces	指定返回的数据格式，如 @Produces("application/json")
@Consumes	指定可接受的请求数据格式
@PathParam	绑定 URL 路径变量到方法参数
@QueryParam	绑定 URL 查询参数到方法参数
@FormParam	绑定表单参数到方法参数
@HeaderParam	绑定请求头参数到方法参数

4. JDO（Java Data Objects）

类似 JPA，但更通用：
注解	作用
@PersistenceCapable	标记类为可持久化对象
@Persistent	标记字段为可持久化
@NotPersistent	标记字段不持久化
@PrimaryKey	指定主键
@Unique	指定唯一约束

=事务  @Transactional
=配置  @config

5. Spring 相关注解

用于依赖注入（IoC）、AOP、事务、Web 开发等：
Spring IoC（控制反转）
注解	作用
@Component	标记为 Spring 组件，自动扫描并实例化
@Bean	定义一个 Bean
@Service	标记业务逻辑层的组件
@Repository	标记持久层（DAO）组件
@Controller	标记 MVC 控制器
@RestController	@Controller + @ResponseBody，返回 JSON
@Autowired	自动注入 Bean（依赖注入）
@Qualifier("beanName")	指定注入的 Bean 名称
@Value("${config.value}")	注入配置文件的值
Spring AOP（面向切面编程）
注解	作用
@Aspect	定义一个切面
@Before("execution(...)")	在方法执行前执行
@After("execution(...)")	在方法执行后执行
@Around("execution(...)")	在方法执行前后执行
@Pointcut("execution(...)")	定义切入点表达式
Spring 事务管理
注解	作用
@Transactional	声明事务管理，适用于数据库操作


6. 测试相关（JUnit、Mockito）
   注解	作用
   @Test	标记测试方法
   @Before / @BeforeEach	在每个测试前执行
   @After / @AfterEach	在每个测试后执行
   @BeforeClass / @BeforeAll	在所有测试前执行（静态方法）
   @AfterClass / @AfterAll	在所有测试后执行（静态方法）
   @Mock	创建模拟对象
   @InjectMocks	注入模拟对象

7. Lombok 代码简化
   注解	作用
   @Data	自动生成 getter/setter/toString/equals/hashCode
   @Getter / @Setter	生成 Getter/Setter
   @ToString	生成 toString() 方法
   @EqualsAndHashCode	生成 equals() 和 hashCode()
   @NoArgsConstructor	生成无参构造
   @AllArgsConstructor	生成全参构造
   @Builder	生成 Builder 模式
=auth    @RolesAllowed	指定允许访问的角色
   @Schedule	定时任务
   @RolesAllowed	指定允许访问的角色

9. 其他常见注解
   注解	作用
   @JsonIgnore	在 Jackson 中忽略字段序列化
   @JsonProperty("name")	指定 JSON 属性名称
   @XmlRootElement	标记类为 XML 根元素
   @XmlElement	指定 XML 标签


总结

不同场景下的常用注解：

    REST API：@RestController、@GetMapping、@PathParam
    ORM（JPA）：@Entity、@Table、@Column
    Spring：@Autowired、@Transactional、@Component
    AOP：@Aspect、@Before
    测试：@Test、@Mock
    Lombok：@Data、@Builder
    JSON/XML：@JsonIgnore、@XmlRootElement