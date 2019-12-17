## T系列—SP存储TSPUtil

> 精简到只有一个[类文件](tsputil/src/main/java/com/github/john/tsputil/TSPUtil.kt)，可以复制粘贴到项目中
   * 跟随APP（欢迎页、引导页等），设置中`删除数据`时删除 - appSp
   * 动态SP，用户登录保存用户数据，退出后删除此SP - userSp
   * 跟随User（ userId,userName,token 等）,用户登录时随userID产生，用户退出时删除 - userReleatedSp
   * 跟随其他（推送开关、个性配置等），设置中`删除数据`时删除 - moreSp

![演示图](resource/TSpUtil.gif =540x960){: width="540px" height="960px"}
### 用例

	对用户相关的userRelatedSp进行存取操作，需要登录时调用步骤3中的方法设置uId
  1. 存
  ```java
    // 存入appSp
	TSPUtil.get().putInApp("key", any)
    // 存入userSp
	TSPUtil.get().putInUser("key", any)
    // 存入userRelatedSp
	TSPUtil.get().putInUserRelated("key", any)
    // 存入moreSp
	TSPUtil.get().putInMore(index, "key", any)
  ```
  2. 取
  ```java
    // 从appSp取
	TSPUtil.get().getFromApp("key", any)
    // 从userSp取
	TSPUtil.get().getFromUser("key", any)
    // 从userRelatedSp取
	TSPUtil.get().getFromUserRelated("key", any)
    // 从moreSp取
	TSPUtil.get().getFromMore(index, "key", any)
  ```
  3. 用户登录
  ````
  	TSPUtil.get().uId = user.id
  ````
  4. 用户退出
  ```
  	TSPUtil.get().exitUser()
  ```