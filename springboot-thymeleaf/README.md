# thymeleaf 使用详解

官方文档: http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#integrating-thymeleaf-with-spring

## 标准表达式语法

它们分为四类：

* 1.变量表达式
* 2.选择或星号表达式
* 3.文字国际化表达式
* 4.URL表达式

### 变量表达式

变量表达式即OGNL表达式或Spring EL表达式(在Spring术语中也叫model attributes)。如下所示：

```java
${session.user.name}
```
它们将以HTML标签的一个属性来表示：
```html
<span th:text="${book.author.name}">  
<li th:each="book : ${books}"> 
```

### 选择(星号)表达式

选择表达式很像变量表达式,不过它们用一个预先选择的对象来代替上下文变量容器(map)来执行,

```html
*{user.name}
```
**被指定object由 th:object 属性定义**
```html
<div th:object="${book}">  
      ...  
      <span th:text="*{title}">...</span>  
      ...  
    </div>
```

### 文字国际化表达式

文字国际化表达式允许我们从一个外部文件获取区域文字信息(.properties)，用Key索引Value，还可以提供一组参数(可选).

```html
    #{main.title}  
    #{message.entrycreated(${entryId})}
```

可以在模板文件中找到这样的表达式代码：`
```html
<table>  
      ...  
      <th th:text="#{header.address.city}">...</th>  
      <th th:text="#{header.address.country}">...</th>  
      ...  
    </table>
```

### URL表达式

URL表达式指的是把一个有用的上下文或回话信息添加到URL，这个过程经常被叫做URL重写。

`@{/order/list}` 

URL还可以设置参数：  `@{/order/details(id=${orderId})}`  

相对路径：  `@{../documents/report}` 

让我们看这些表达式
```html
<form th:action="@{/createOrder}">  
    <a href="main.html" th:href="@{/main}">
```

## 变量表达式和星号表达式有什么区别?

如果不考虑上下文的情况下两者没有什么区别,
星号语法评估在选定对象上表达，而不是整个上下文 
什么是选定对象？就是父标签的值，如下：
```html
<div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```
这安全等价于:
```html
  <div th:object="${session.user}">
	  <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
	  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
	  <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
  </div>
```

当然，美元符号和星号语法可以混合使用：
```html
  <div th:object="${session.user}">
	  <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
  	  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```
## 几种常用的使用方法

### 1、赋值、字符串拼接

```html
 <p  th:text="${collect.description}">description</p>
 <span th:text="'Welcome to our application, ' + ${user.name} + '!'">
```

字符串拼接还有另外一种简洁的写法
```html
<span th:text="|Welcome to our application, ${user.name}!|">
```

### 2、条件判断 If/Unless

Thymeleaf中使用th:if和th:unless属性进行条件判断，下面的例子中，<a>标签只有在th:if中条件成立时才显示：

```html
<a th:if="${name=='yes'}" href="#"> 只有name=yes 时,你才能看到我 </a>
<a th:unless="${name == 'yes'}" href="#" >只有name!=yes 时,你才能看到我 </a>



```
也可以使用 (if) ? (then) : (else) 这种语法来判断显示的内容


### 3、for 循环

```html
<tr  th:each="collect,iterStat : ${collects}"> 
     <th scope="row" th:text="${collect.id}">1</th>
     <td >
        <img th:src="${collect.webLogo}"/>
     </td>
     <td th:text="${collect.url}">Mark</td>
     <td th:text="${collect.title}">Otto</td>
     <td th:text="${collect.description}">@mdo</td>
     <td th:text="${terStat.index}">index</td>
 </tr>
```


### 4、URL

URL在Web应用模板中占据着十分重要的地位，需要特别注意的是Thymeleaf对于URL的处理是通过语法@{…}来处理的。
 如果需要Thymeleaf对URL进行渲染，那么务必使用th:href，th:src等属性，下面是一个例子

```html
<!-- Will produce 'http://localhost:8080/standard/unread' (plus rewriting) -->
 <a  th:href="@{/standard/{type}(type=${type})}">view</a>

<!-- Will produce '/gtvg/order/3/details' (plus rewriting) -->
<a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
```
设置背景
```html
<div th:style="'background:url(' + @{/<path-to-image>} + ');'"></div>
```

* 上例中URL最后的(orderId=${o.id}) 表示将括号内的内容作为URL参数处理，该语法避免使用字符串拼接，大大提高了可读性

* @{...}表达式中可以通过{orderId}访问Context中的orderId变量

* @{/order}是Context相关的相对路径，在渲染时会自动添加上当前Web应用的Context名字，假设context名字为app，那么结果应该是/app/order


























