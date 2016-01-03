# Spring Bootで何か作る

- Reference Guide
  - http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle
  - http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
  - http://docs.spring.io/spring-boot/docs/current/api
  - http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle
  - http://docs.spring.io/spring/docs/current/javadoc-api
  - http://junit.sourceforge.net/javadoc
  - https://docs.oracle.com/javaee/7/api/index.html?javax/persistence/package-summary.html
  - http://docs.mockito.googlecode.com/hg/latest/index.html
  - http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf_ja.html
  - http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html
  - http://www.thymeleaf.org/apidocs/thymeleaf/2.1.4.RELEASE/
  - http://www.thymeleaf.org/apidocs/thymeleaf-spring4/2.1.4.RELEASE/

# Thymeleaf
- th:each
      "post: ${posts}"
      "post,st: ${posts}"
      特殊変数stのメンバーはindex, count, size, current, even, odd, first, last。
      stを明示しない場合は、postStat.indexなどと参照可能。
- th:text
      "${post.title}"
      "${#calendars.format(today,'dd MMMM yyyy')}"
- th:utext
      "${posts.content}"
      htmlエスケープしない。
- th:if, th:unless
      偽なら(真なら)、当該要素(と子孫要素)を出力しない
      null, false, 0, '0', "false", "off", "no"が偽。
- th:fragment, th:include, th:replace
  - footer.htmlの中のdivに、th:fragment="copyright"などとして、部品を定義
  - a_page.htmlの中のdivで、th:include="footer::copyright"として参照
  - th:include="footer"なら、footer.html全体をインクルード
  - "footer::[//div[@class='hoge']]"のようなXPathっぽい指定も可能(XPathそのものではないみたい)
    - この場合、th:fragmentは不要
  - "::[//div[@class='hoge']]"や"this::[//div[@class='hoge']]"の場合、自ファイルを参照
  - th:replaceはth:includeの親戚(微妙に違うので実験すること)
  - 部品へパラメータを渡す
        th:fragment="frag (onevar,twovar)"
        th:include="::frag (${value1},${value2})"
        th:include="::frag (onevar=${value1},twovar=${value2})"
- th:remove
      th:remove="all"   ...当該要素と子孫を削除
      th:remove="body"  ...当該タグ自体は残し、ボディを削除
      tag, all-but-first, noneもある
      <tbody th:remove="all-but-first">
        <tr th:each=...> ... </tr>
        <tr> ... </tr>
        <tr> ... </tr>
        <tr> ... </tr>
      </tbody>
  - コメントアウトする、という手もある
        <!--/*-->
        <tr> ... </tr>
        <tr> ... </tr>
        <tr> ... </tr>
        <!--*/-->
- th:block
      <!--/*/ <th:block th:each="user : ${users}"> /*/-->
      <tr>
          <td th:text="${user.login}">...</td>
          <td th:text="${user.name}">...</td>
      </tr>
      <tr>
          <td colspan="2" th:text="${user.address}">...</td>
      </tr>
      <!--/*/ </th:block> /*/-->
      /*/から/*/の間は、HTMLコメントの中にあるが、Thymeleafにより処理される。
      そして、th:block要素は、Thymeleafに処理された後で削除される(子要素は残る)。
      これなら、th:xxxを書くために、余計なdivやspan要素を作る必要がない。
- th:with
      <span th:with="df=#{date.format}" 
          th:text="${#calendars.format(today,df)}">13 February 2011</span>
- JavaScript
      <script th:inline="javascript">
      /*<![CDATA[*/
          ...

          var username = /*[[${session.user.name}]]*/ 'Sebastian';

          /*[+
          var msg  = 'Hello, ' + [[${session.user.name}]];
          +]*/

          /*[- */
          var msg  = 'This is a non-working template';
          /* -]*/

          ...
      /*]]>*/
      </script>

- th:object
      VBのwith構文的なやつ。
      th:object="${session.user}"
      th:text="*{firstName}"        session.user.firstNameのこと
      th:text="*{lastName}"
      th:text="*{country}"
- th:href
      <a href="details.html" 
         th:href="@{/order/{fuga}/details(orderId=${o.id}, hoge=4, fuga=8)}">view</a>
      'http://localhost:8080/gtvg/order/fugavalue/details?orderId=3&hoge=4'
- 属性を変える
      th:attr="action=@{/subscribe}" または th:action="@{/subscribe}"
      th:attr="value=#{subscribe.submit}"  または th:value="#{subscribe.submit}"
      th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}"

      th:alt-titleを使うと、altとtitleに同じ値をセットできる(imgタグ向き)。

      class="btn" th:attrappend="' error'"
      th:attrappendとth:attrprependで属性の現在地に付けたせる。
      th:classappendとth:styleappendなら、さらにシンプル。
      th:classappend="error"

      th:checked="${user.active}"     ... user.activeがfalseならchecked属性なし
      th:readonlyやth:selected, th:disabledなどもある
- propertyファイルを参照するとき
      "#{msg.not_found}"
      hoge.htmlに対して、hoge.propertiesやhoge_jp.propertiesの定義を参照。
- パラメータ付きメッセージ
      "#{home.welcome(${session.user.name})}"
      home.welcome=welcome, {0}
      java.text.MessageFormat参照。
- 特殊なコンテキスト変数/オブジェクト
  - param: リクエストパラメータ
  - session: セッション
  - application: サーブレットコンテキスト
  - execInfo
    - templateName
    - now
  - \#ctx, #locale, #vars, ...
  - \#dates, #calendars, #numbers, #strings, #objects, #bools, #arrays, #lists, #sets, #maps, #aggregates, #messages, #ids, #conversions
    - org.thymeleaf.expression.Calendars
    - org.thymeleaf.expression.Dates
    - http://www.thymeleaf.org/apidocs/thymeleaf/2.1.4.RELEASE/
  - \#fields
    - org.thymeleaf.spring4.expression.Fields
- OGNL構文
      http://commons.apache.org/ognl/
- 変数展開
      "|Welcome to our application, ${user.name}!|"
- 3項演算子/デフォルト式
      "${row.even}? 'even' : 'odd'"
      "${session.age}?: '(no age specified)'"
- プリプロセス
      ${}や#{}の代わりに、__${}__や__#{}__を使うと、プリプロセスされる。
- 演算子
  - and, or, not
  - eq, ne
  - gt, ge, lt, le
- Security/Login
  - 素のままで
        th:text="${#httpServletRequest.userPrincipal?.name}"    ...未ログインなら空っぽ
  - extrasと共に
        thymeleaf-extras-springsecurity4
        xmlns:sec="org.thymeleaf.extras.springsecurity4"
        <li class="navbar-text" sec:authorize="hasRole('ROLE_ADMIN')">admin</li>   ...adminのときだけ
        <li class="navbar-text" th:text="${#authentication.name}">hoge</li>      ...未ログインなら"anonymousUser"
        <li class="navbar-text" th:text="${#authentication.principal.username}">hoge</li>      ...未ログインなら、principalが"anonymousUser"なので注意(使えん)
        <li class="navbar-text" sec:authentication="name">fuga</li>   ...未ログインなら"anonymousUser"
        <li sec:authorize="!isAuthenticated()"><a href="../auth/login.html" th:href="@{/login}">ログイン</a></li>
        <li sec:authorize="isAuthenticated()"><a href="#" th:href="@{/logout}">ログアウト</a></li>

## Springと
- thymeleaf-spring4
- org.thymeleaf.spring4
- OGNLの代わりに、SpELが使える
- SpringのBeanを直接参照する
      "${@cacheManager.getCacheNames()}"
- フォーム
  - form要素の中のth:objectは、Formオブジェクトと結びつく
  - input/select/textarea要素にth:fieldでフィールド名を指定
  , th:errors, th:errorclass
  - チェックボックス
        <ul>
          <li th:each="feat : ${allFeatures}">
            <input type="checkbox" th:field="*{features}" th:value="${feat}" />
            <label th:for="${#ids.prev('features')}" 
                   th:text="#{${'seedstarter.feature.' + feat}}">Heating</label>
          </li>
        </ul>
  - ラジオボタン
        <ul>
          <li th:each="ty : ${allTypes}">
            <input type="radio" th:field="*{type}" th:value="${ty}" />
            <label th:for="${#ids.prev('type')}" th:text="#{${'seedstarter.type.' + ty}}">Wireframe</label>
          </li>
        </ul>
  - リスト
        <select th:field="*{type}">
          <option th:each="type : ${allTypes}" 
                  th:value="${type}" 
                  th:text="#{${'seedstarter.type.' + type}}">Wireframe</option>
        </select>
  - 行追加/行削除
        <table>
          <thead>
            <tr>
              <th th:text="#{seedstarter.rows.head.rownum}">Row</th>
              <th th:text="#{seedstarter.rows.head.variety}">Variety</th>
              <th th:text="#{seedstarter.rows.head.seedsPerCell}">Seeds per cell</th>
              <th>
                <button type="submit" name="addRow" th:text="#{seedstarter.row.add}">Add row</button>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr th:each="row,rowStat : *{rows}">
              <td th:text="${rowStat.count}">1</td>
              <td>
                <select th:field="*{rows[__${rowStat.index}__].variety}">
                  <option th:each="var : ${allVarieties}" 
                          th:value="${var.id}" 
                          th:text="${var.name}">Thymus Thymi</option>
                </select>
              </td>
              <td>
                <input type="text" th:field="*{rows[__${rowStat.index}__].seedsPerCell}" />
              </td>
              <td>
                <button type="submit" name="removeRow" 
                        th:value="${rowStat.index}" th:text="#{seedstarter.row.remove}">Remove row</button>
              </td>
            </tr>
          </tbody>
        </table>
  - 入力エラー
    - #fields.hasErrors('all') ... 何かエラーがある? hasAnyErrors()でもOK
    - #fields.hasErrors('hoge') ... hogeフィールドにエラーがある?
    - #fields.errors('hoge') ... hogeフィールドのエラーメッセージ一覧。allErrors()でもOK
    - th:errors="*{hoge}" ... エラーメッセージを<br />で連結
    - th:field付きの要素にth:errorclass="err"を付ける ... エラーがあれば、classに"err"を追加
    - form要素の外で入力エラーを処理するなら、#fields.hasErrors('${myForm}')とか#fields.hasErrors('${myForm.field1}')とかth:errors="${myForm.field1}"とか

- #mvc.uri(...)
- "${{sb.datePlanted}}"で、Spring Conversion Serviceを自動適用
- "#{${'seedstarter.type.' + sb.type}}"
- ユーティリティオブジェクト
      "${#strings.arrayJoin(
           #messages.arrayMsg(
                #strings.arrayPrepend(sb.features,'seedstarter.feature.')), ', ')}"
- th:fragmentをAJAXで返す
      @RequestMapping("/showContentPart")
      public String showContentPart() {
          ...
          return "index :: content"; // index.htmlのth:fragment="content"
                                     // index :: #contentId
                                     // index :: content ('param1')
      }
- routesの逆引き
      @RequestMappingすると、自動的に逆引き用の短縮名が登録される。
      HogeControllerのfuga()なら、HC#fuga といった具合。
      <a th:href="${#mvc.url('HC#fuga').arg(0, 'piyo').arg(1, 'piyopiyo')}">Get Data</a>

# Annotations
- @Component
  - いわゆるコンポーネント(部品、モジュールなどと同じニュアンス)
  - DIオブジェクトの供給元
- @SpringBootApplication
  - @Configuration, @EnableAutoConfiguration, @ComponentScanをまとめたやつ
  - Applicationクラスに付けとけばいいっぽい
  - @Configurationは、クラスをJava Configuration対象とみなす(@Beanを探す)
  - @EnableAutoConfigurationは、jarの依存関係に基づいて自動コンフィグする
  - @ComponentScanは、パッケージから@Componentを探す
- @RestController
  - @Controllerと@ResponseBodyをまとめたやつ
- @Controller
  - いわゆるコントローラ
  - @Componentの一種
- @ResponseBody
  - コントローラの出力を(Viewを通さずに)そのままレスポンスに使う
- @RequestMapping
  - リクエストURLとコントローラのメソッドを対応付ける
- @PathVariable("id")
  - リクエストURLのパスの一部をメソッドの引数として受け取る
- @RequestBody
  - リクエストのボディ部をメソッドの引数として受け取る
- @RequestParam
  - クエリ文字列パラメータをメソッドの引数として受け取る
        @RequestParam(value="wait", defaultValue="false")
- @Service
  - モデルに対するインターフェイスを提供するクラス
  - @Componentの一種
- @Repository
  - ストレージIOを提供するクラス(DAO相当)
  - @Componentの一種
- @Autowired
  - Springによりインスタンス化(DI)されるフィールド
  - Java標準の@Injectや@Resourceも同様
  - @Autowiredと@Injectは型優先で実装クラスを見つけるが、@Resourceは名前優先
  - @Autowiredはrequired属性を持つが、@Injectには無い(常にrequired)
- @Entity
  - エンティティクラス(JPA)
- @Id
  - PKフィールド
- @GeneratedValue
  - PKの生成方法を指定(デフォは「おまかせ」)
- @EnableTransactionManagement
  - Springによるトランザクション管理を有効化
- @Transactional
  - トランザクションの属性を指定
  - propagation: いつトランザクションを作るか
  - readOnly: トランザクション内で書き込みを行うか(ヒントに過ぎない)
        @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
        @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
- @EnableCaching
  - Springによるキャッシュ管理を有効化
- @Cacheable
  - キャッシュ対象の属性を指定
  - value: キャッシュ名
  - key: キャッシュのキーをSpEL(Spring Expression Language)で指定
        @Cacheable(value = "greetings", key = "#id")
- @CachePut
  - キャッシュインするメソッド
        @CachePut(value = "greetings", key = "#result.id")
        // #root.methodName(メソッド名), #result(戻り値)といったメタデータを使用可能。
- @CacheEvict
  - キャッシュをクリアするメソッド
        @CacheEvict(value="greetings", key="#id")
        @CacheEvict(value="greetings", allEntries=true)
- @ExceptionHandler
  - 例外を処理するメソッド
- @EnableScheduling
  - Springのバッチ処理を有効化
- @Scheduled
  - バッチ処理の属性を指定
        @Scheduled(cron=".....")
        @Scheduled(initialDelay = 5000, fixedRate = 15000)
        @Scheduled(initialDelay = 5000, fixedDelay = 15000)
        @Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedDelayString = "${batch.greeting.fixeddelay}")
            // propertyファイルの定義を参照している。
- @EnableAsync
  - Springの非同期処理を有効化
- @Async
  - 非同期実行するメソッド
- @Value
  - フィールドに初期値を与える
- @Profile
  - 特定のプロファイルがアクティブなときだけ有効化されるコンポーネント
        @Profile("batch")
- @RunWith
  - JUnitのrunnerクラスを指定
- @SpringApplicationConfiguration
  - ApplicationContextのロード方法や構成方法を指定
  - unit testなどで、Springアプリを起動するときに使う
- @WebAppConfiguration
  - ApplicationContextとして、WebApplicationContextを使う
- @Test
  - JUnitのテストメソッド
- @Before
  - JUnitで、@Testメソッドの前に実行するメソッド
- @After
  - JUnitで、@Testメソッドのあとに実行するメソッド
- @Mock
  - Mockitoによりモック化するフィールド
- @InjectMocks
  - Mockitoによりモックを注入されるフィールド

# war化
- SpringBootServletInitializer

# hot deploy
- Spring Loaded
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>springloaded</artifactId>
      </dependency>
- デバッグ実行すると、コンパイルするたびに自動リロードされる
- 通常の実行時にも自動リロードするには、Javaオプションを追加する必要がある
      -javaagent:d:\dev\springloaded-1.2.4.RELEASE.jar -noverify

# MySQL
- datetime型のカラムとJavaのLocalDateオブジェクトは相互変換できないので、自前のコンバータが必要
      @Converter(autoApply=true)
      public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {

        @Override
        public Timestamp convertToDatabaseColumn(LocalDate ld) {
          return ld == null ? null : Timestamp.valueOf(ld.atTime(0, 0));
        }

        @Override
        public LocalDate convertToEntityAttribute(Timestamp ts) {
          return ts == null ? null : ts.toLocalDateTime().toLocalDate();
        }

      }

- datetime型のカラムに0が入っている場合(NOT NULL制約付きとかで)、ORMのところで例外が発生するので、対処が必要
  - MySQLのオプションzeroDateTimeBehaviorで制御できる
        spring.datasource.url=jdbc:mysql://localhost/test_lanmap?zeroDateTimeBehavior=convertToNull

# http://qiita.com/uzresk/items/31a4585f7828c4a9334f
- flash
      // controller
      Sting save(RedirectAttributes attrs) {
        attrs.addFlashAttribute("flashMsg", "fuga");
        return "redirect:/index"
      }
      // index.html
      <p  th:text="${flashMsg}">what?</p>
- validation
  -@Length(min=,max=)
  -@Max(value=)
  -@Min(value=)
  -@NotNull
  -@NotEmpty   ""はNG
  -@NotBlank   "   "でもNG
  -@Pattern(regex="regexp", flag=)
  -@Range(min=, max=)
  -@AssertFalse
  -@AssertTrue
          @AssertTrue
          public boolean getInvariant() {
              return ...;
          }
  -@Valid  ...?
  -@Email

      @RequestMapping(...)
      String save(@Validated HogeForm form, BindingResult result) {
          if ( result.hasErrors() ) {
              return "showForm";
          }
  -文言
      ValidationMessages_ja.properties
      メッセージIDは、アノテーション名?
  -カスタム
    - https://access.redhat.com/documentation/ja-JP/JBoss_Enterprise_Application_Platform/5/html-single/Hibernate_Validator_Reference_Guide/index.html#validator-defineconstraints-builtin
    - Validatorを準備
          @Component
          public class AccountValidator implements Validator {
              @Override
              public boolean supports(Class<?> clazz) {
                  return AccountCreateForm.class.isAssignableFrom(clazz);
              }
              @Override
              public void validate(Object target, Errors errors) {
                  AccountCreateForm form = (AccountCreateForm) target;
                  ...
                  errors.rejectValue("accountId",
                          "AccountValidator.duplicateAccountId", "メッセージ");
              }
          }
    - ControllerへDI
          @Autowired
          AccountValidator accountValidator;

          @InitBinder
          public void initBinder(WebDataBinder binder) {
              binder.addValidators(accountValidator);
          }
- messages.propertiesの参照
        @Autowired
        MessageSource messageSource;
        messageSource.getMessage("messageId",
                     new String[] { argument },
                                  "defaultMessage", Locale.JAPAN);
- コンテキストパス
        server.contextPath=/app

# tutorial
## part0
- STSをインストール
- Spring Starter Projectを作成(mvn)
- @pringBootApplication
- Maven => Update Project
- Project => Clean

    ./mvnw spring-boot:run    ...実行
    ./mvnw clean package      ...jar化
    java -jar target/demo-0.0.1-SNAPSHOT.jar   ...実行
    http://localhost:8080/

## part1
- REST(index)
- @RestController
- @RequestMapping
  - value = "/api/greetings"
  - method = RequestMethod.GET
  - produces = MediaType.APPLICATION_JSON_VALUE

## part2
- REST(show, create, update, destroy)
- @PathVariable("id")
- @RequestMapping
  - consumes = MediaType.APPLICATION_JSON_VALUE
- @RequestBody

## part3
- Service
- @Service
- @Autowired

## part4
- JPA
- pom.xmlのscope=runtime
- @Entity
- @Id, @GeneratedValue
- @Repository
- import org.springframework.data.jpa.repository.JpaRepository;
- sql files

## part5
- Transaction
- @EnableTransactionManagement
- @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)  ...既存のTXをサポート
- @Transactional(propagation = Propagation.REQUIRED, readOnly = false) ...TXが無ければ作る

## part6
- Caching
- @EnableCaching
- @Cacheable(value = "greetings", key = "#id")  ...変数idをキーにしてキャッシュを読む
- @CachePut(value = "greetings", key = "#result.id")  ...戻り値のidフィールドをキーにしてキャッシュを更新
- @CacheEvict(value="greetings", key="#id")   ...キャッシュをpurge
- @CacheEvict(value="greetings", allEntries=true)   ...全エントリをpurge
- com.google.guava

## part7
- Scheduling batch jobs
- @EnableScheduling
- @Component
- @Scheduled(cron=".....")
- Logger
- @Scheduled(initialDelay = 5000, fixedRate = 15000)
- @Scheduled(initialDelay = 5000, fixedDelay = 15000)

## part8
- Async request handling
- @Async
- Future<V>
- @RequestParam(value="wait", defaultValue="false")

## part9
- Profile
- @Profile("batch")
- java -jar hoge.jar --spring.profiles.active=batch
- または、application.propertiesに、spring.profiles.active=batchを追加
- application-batch.properties
      hoge.fuga = 1
      hoge.piyo = 2
- int the code
      "${hoge.fuga}"     ... "1"へ展開される
       @Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedDelayString = "${batch.greeting.fixeddelay}")
- 文字列内でしか展開できない
- @Scheduledには、initialDelayの仲間のinitialDelayStringがある

## part10
- Testing Service classes
- @RunWith(SpringJUnit4ClassRunner.class)
- @SpringApplicationConfiguration(classes=DemoApplication.class)
- @Test, @Before, @After
- mvn clean package
- またはRun As JUnit testで実施
- mvn clean package -DskipTests

## part11
- Testing Controller classes
- @WebAppConfiguration
- MockMvc    ...request/responseのモック
- com.fasterxml.jackson.databind.ObjectMapper

## part12
- Testing with mock
- @Mock
- @InjectMocks

## part13
- Actuator
- autoconfig, beans, configprops, ...

## part14
- Security
- Basic認証
      // application.propagation
      security.user.name=hoge
      security.user.password=hoge

## part15
- Handling exceptions
- @ExceptionHandler

# tutorial2
## classes
### Model
- Account, Category
- Goods(belongs to Category)
- OrderLine(belongs to Goods), 仮想カラムgetSubtotal()
- Order(has many OrderLine through OrderLines)
- OrderLines, Cart ...コレクション。Modelっぽくない
### Repository
- Account, Category
- Goods ...直SQLでINNER JOIN
- Order ...newしたOrderに、オーダーIDを割り当て、ORDERテーブルへINSERT
### Service
- Account ...isUnusedEmail(email), register(account, password)
- Category ...findAll()
- Goods ...findOne(), findByCategoryId()
- Order ...calcSignature(), purchace()
- ShopUserDetails ...UserDetailsServiceを継承しloadUserByUsername()をオーバーライド
### Validation
- Confirm ...パスワードの2重チェック
- UnusedEmail ...emailの一意性
## Form
- Account, AddtoCart, Cart
## Controller
### Login
- loginForm
### Account
- @ModelAttributeメソッドが返すAccountFormが、自動的にModelへaddAttributeされる
- createForm
- create ...Accountをregisterして、createFinishへリダイレクト
- createFinish
### Goods
- @ModelAttributeを使って、カテゴリマスタ情報をModelへaddAttribute
- showGoods
- addToCart
### Cart
- viewCart
- removeFromCart
### Order
- @AuthenticationPrincipalにより、ログインユーザの情報を得る
- confirm
- order
- finish
## Configuration
### SecurityConfig
- authentication ...
- loginフォーム
- authorization
### AppConfig
- cartをセッションオブジェクトとする
