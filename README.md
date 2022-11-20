## [Chinese Doc](https://github.com/azhon/FlutterModuleBridge/blob/main/README-zh.md)

## [Plugin marketplace install](https://plugins.jetbrains.com/plugin/20491-fluttermodulebridge)

### Dart modular development, event communication template generation plugin (AS, IDEA)
<img src="https://github.com/azhon/FlutterModuleBridge/blob/main/imgs/plugin.png" width="750" >

- Menu `Build -> FlutterModuleBridge`
- Or use shortcut keys`Alt + B`

### There are several requirements for the `Bridge` template
1. A dart file can only have one class, stored in the `lib/` directory
2. The class must be `with Bridge`
3. The method must be annotated with `@Url`
4. The return value of the method must be `R` or `Future<R>` type

### Examples:

```java
import 'package:module_bridge/module_bridge.dart';

class UserBridge with Bridge {

   @Url(url: '/user/getUserId', desc: 'Get UserId')
   R getUserId() {
     return R.ok(data: 1234);
   }

   @Url(url: '/user/getUserName', desc: 'Get user name')
   Future<R> getUserName() async {
     return R.ok(data: 'azhon');
   }
}
```