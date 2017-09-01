                    NestedScrolling机制分析

Android在发布 5.0（Lollipop）版本之后，Google为我们提供了嵌套滑动，。Lollipop及以上版本的所有View都已经支持了这套机制，Lollipop之前版本可以通过Support包进行向前兼容。

 主要的4个类：

  	NestedScrollingChild  (interface)
  	NestedScrollingChildHelper (class)

  	NestedScrollingParent (interface)
  	NestedScrollingParentHelper  (class)

**NestedScrolling是一套父view与子View之间的滑动交互机制**


 此demo是对于NestedScrollingParent的实现。 内部嵌套RecyclerView，由于RecyclerView实现了NestedScrollingChild接口所以只需要让parent实现NestedScrollingParent接口就能处理滑动的传递与消耗。

NestedScrollingParent接口如下：

	package android.support.v4.view;

	import android.view.View;

	public interface NestedScrollingParent {
	
	// 当子view想要滑动的时候会回调此方法，返回值决定是否允许子vew滑动
    boolean onStartNestedScroll(View var1, View var2, int var3);
	// 如果parent允许子view滑动，此方法会被调用
    void onNestedScrollAccepted(View var1, View var2, int var3);
	// 当一次完整的滑动全部完成后会调用此方法
    void onStopNestedScroll(View var1);

	// 当child处理完滑动过后会回调此方法，告诉parent自己消耗了多少滑动，没有消耗完的parent可以自行处理
    void onNestedScroll(View var1, int var2, int var3, int var4, int var5);

	// 在child想要滑动之前会被调用，parent如果完全消耗，child是接收不到滑动事件的，
    void onNestedPreScroll(View var1, int var2, int var3, int[] var4);

	// 此方法是child处理完fling事件后被调用，参数可以知道child是否消耗调fling事件，如果没有，自己还是可以消耗，也可以不消耗，返回值决定是否消耗
    boolean onNestedFling(View var1, float var2, float var3, boolean var4);
	// 此方法是在抬手之后被调用的，parent可以自行处理，但此事件一旦处理，就是消耗掉所有滑动，child就接收不到fling事件，返回值确定是否需要消耗fling事件
    boolean onNestedPreFling(View var1, float var2, float var3);

    int getNestedScrollAxes();
    }



