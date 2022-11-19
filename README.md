## [English Doc](https://github.com/azhon/FlutterModuleBridge/blob/main/README-EN.md)

### Dart模块化开发，事件通信模板生成插件(AS/IDEA)

### 对于`Bridge`模板有如下几个要求
1. 一个dart文件只能有一个class
2. class必须`with Bridge`
3. 方法必须使用@Url注解
4. 方法返回值必须是`R`或者`Future<R>`类型

### 示例如下：

```java
import 'package:module_bridge/module_bridge.dart';

class UserBridge with Bridge {

  @Url(url: '/user/getUserId', desc: '获取用户Id')
  R getUserId() {
    return R.ok(data: 1234);
  }

  @Url(url: '/user/getUserName', desc: '获取用户名称')
  Future<R> getUserName() async {
    return R.ok(data: 'azhon');
  }
}

```
