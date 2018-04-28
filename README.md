# GoogleNavigationDemo
Google官方Android导航探究

~~对master分支下几乎是半懂不懂摸索的代码进行精简，尽量抓住这个设计的本质。~~

精简的过程中发现原来的代码中的 MVP 模式同样应用到了对 NavigationView 的实现中，起初以为是针对 Activity 以及其中的 Fragment 做设计模式。

精简之后把 Presenter 层去掉了，使 NavigationModel 中的数据直接加载到了 NavigationView 中。

从最终的代码可以看出，整个设计其实只做了三件事：
1. 对 NavigationView 进行抽象；
2. 在 BaseActivity 中进行具体的实现；
3. 派生的 Activity 重写 `getSelfNavDrawerItem()` 方法，将自己加入到导航的豪华套餐。

整个设计的关键在于每个导航的页面都是一个 Activity，并且都包含一个 NavigationView。从整个应用原来的设计角度来看，应该是为了对导航页面可以进行灵活的配置。并且附加的好处是 NavigationView 的具体实现可以方便的进行替换。

但是这里有个让我很不解的情况，应该是没有需要替换的必要以及可能吧，Google 家自己不就出了这么一个 BottomNavigationView 嘛，而且还不怎么好用。同样的实现方式，我的代码跑起来之后底部菜单项的缩放动画夸张的让人讨厌，为什么他们自己的应用不会有这个问题。
