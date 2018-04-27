# GoogleNavigationDemo
Google官方Android导航探究

生词：
|英文|翻译|
|----|----|
|synthetic |合成的|
|derived |派生的|
|EULA（ End-user license agreement ）|最终用户许可协议|
|tint |着色|

## 第一步：
创建Model：
NavigationModel

好吧先是：Model，但是感觉里面有一些东西用不到。
比如要想创建Model还要先创建两个Enum：QueryEnum和UserActionEnum。

在NavigationModel中用到了NavigationConfig。
这个时候需要导航的几个Activity应该就已经建好了，或者起码需要添加进来了。

NavigationConfig中提供了对NavigationModel中提供的NavigationItemEnum各项的配置操作方法，包括新增（目前没有用到）、筛选（把BuildConfig中无效化的页面去掉）。

## 第二步：
接下来要创建UpdatableView。这个是需要进行切换的页面需要继承的接口。通常为Fragment，也就是切换的页面显示的视图。

## 第三步：
AppNavigationView：忽略细节的导航View，算是NavigationBottomView的代理吧。

##第四步：
终于到 AppNavigationViewAbstractImpl 了，真正的实现类。

~~出了点小问题，一个是NavigationModel报错，还有一个是是LOAD_ITEMS报错。~~
原来是变量长得太像弄混了。

## 第五步：
这是还要创建 Presenter 以及 PresenterImpl 了。

## 第六步：
AppNavigationViewAsBottomNavImpl是最终实现功能的类。在实现它之前还要先实现BadgedBottomNavigationView，这是一个系统原生的导航BottomNavigationView的一个自定义子类。

自定义 BadgedBottomNavigationView 的时候用到了一个 ViewTreeObserver.OnDrawListener 不熟悉类，但是感觉很厉害。
BadgedBottomNavigationView 的构造函数中需要自定义样式，用到了 values/attrs.xml 文件（需要创建）。需要定义自定义 View 的参数。
在这其中Google做了一个分析，最终是归为FirebaseAnalytics中的函数调用了，但是过程中打印了数次日志，同时发送了事件（Event）目前没有细看是否做了网络或是文件操作。

## 第七步：
AppNavigationViewAsBottomNavImpl

## 第八步：
最终，AppNavigationViewAsBottomNavImpl 在 BaseActivity 中被调用。


过程中犯的几个错误：

NavigationItemEnum 的配置项忘记配置到 NavigationConfig 中去了
```
NavigationModel.NavigationItemEnum.HOME,
NavigationModel.NavigationItemEnum.SCHEDULE,
NavigationModel.NavigationItemEnum.FEED,
NavigationModel.NavigationItemEnum.MAP,
NavigationModel.NavigationItemEnum.INFO,
```
BadgedBottomNavigationView 中 listener 忘记加了，还漏了一句 `a.recycle();`

主要问题还是出在 BaseActivity 中，
```java
@Override
protected void onPostCreate(@Nullable Bundle savedInstanceState)
// 错写成了
@Override
public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
```
这是一个很常见的错误，以前经常把两个`onCreate()`方法弄混。
还有一个，
```
mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
// 写成了
mToolbarTitle = mToolbarTitle.findViewById(R.id.toolbar_title);
```
差不多就是这样。



附：
# 单面板活动探究：
SimpleSinglePaneActivity
