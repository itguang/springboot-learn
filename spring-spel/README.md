#  spring之SpEL表达式在Bean定义中的表达式语言支持

参考: http://blog.csdn.net/a1610770854/article/details/51908390


## SpEL表达式在Bean定义中的表达式语言支持

SPEL 表达式是一种表达式语言,是一个可以和Spring应用程序中的bean进行交互的东西.类似于OGNL表达式.

SPEL表达式是一种简化Spring应用程序开发的表达式,通过使用表达式来简化开发,减少一些逻辑,配置的编写.

SPEL表达式的一个重要的功能就是Spring容器的功能.允许在bean定义中使用 SPEL.

在XML配置文件和Annotation注解中都可以使用 SPEL表达式.只需要在表达式外面增加 #{} 包围即可


