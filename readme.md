# ZUtils工具框架开发记录

2019年1月3日16:51:40

使用方式(最低api为19)

    1、Add it in your root build.gradle at the end of repositories:

    allprojects {
    		repositories {
    			maven { url 'https://jitpack.io' }
    		}
    	}

    2、Add the dependency

    dependencies {
    	api 'com.github.zhouwenxi:ZUtils:1.0.7'
    }
    
    3、注册BaseQiShuiApplication或则实现BaseQiShuiApplication初始化方法

permisssion
---
1、Activity、Fragment请求权限及拒绝回调处理

    PermissionUtils.with(this)
                .addPermissions(PermissionUtils.GROUP_STORAGE)
                .addPermissions(PermissionUtils.GROUP_CAMERA)
                .addPermissions(PermissionUtils.GROUP_LOCATION)
                .request()
                .setCallback(new PermissionUtils.Callback() {
                    @Override
                    public void refuse() {
                        PermissionUtils.goSetInfo(MainActivity.this, PermissionUtils.getPermission(), 
                        new PermissionUtils.SetCallback() {
                            @Override
                            public void onclikCancle() {
                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

dialog
---
1、CommonDialog提示框 两种风格 STYLE_IOS、STYLE_ANDROID

    CommonDialog.with(this, CommonDialog.STYLE_IOS)
              .setDialogTitle("你是否需要添加?")
              .setDialogLeftText("不了")
              .setDialogRightText("好的")
              .setCallBack(new CommonDialog.CallBack() {
                @Override
                public void left() {

                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void right() {

                    Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                }
            }).showDialog();

2、加载框 设置请求文字

    LoadingDialog.with(this).setText("请求中").showDialog();

3、ListDialog 列表对话框

    List<Bean> list=new ArrayList<Bean>();
    list.add(new Bean("0x1233","hello world 0","江西"));
    list.add(new Bean("0x1234","hello world 1","北京"));
    list.add(new Bean("0x1235","hello world 2","上海"));

    new ListDialog<Bean>(this).setDialogLv(list)
        .setCallback(new ListDialog.CallBack<Bean>() {
        @Override
        public void disPlay(Bean bean, int position) {

            Toast.makeText(MainActivity.this, bean.getCode(), Toast.LENGTH_SHORT).show();
        }
    }).showDialog();

加载显示Bean 需要继承QiShuiDialogBean并设置showName字段

    public class Bean  extends QiShuiDialogBean {

        private String code;
        private String msg;
        private String displayCity;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDisplayCity() {
            return displayCity;
        }


        public void setDisplayCity(String displayCity) {
            this.displayCity = displayCity;
            this.showName=displayCity;
        }

        public Bean(String code, String msg, String displayCity) {

            this.code = code;
            this.msg = msg;
            this.displayCity = displayCity;
            this.showName=displayCity;

        }
    }

4、分享、支付、拍照片等底部弹出框(2019年2月1日 17点37分 添加)

方式一

    BottomDialogFragment.with(R.layout.dialog_frgment_share)
      .setCallBack(new BottomDialogFragment.CallBack() {
                        @Override
                        public void handle(View view) {

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toast("Hello world ");
                                    BottomDialogFragment.getInstance().dismissDialog();
                                }
                            });
                        }
                    }).showDialog(MainActivity.this);

方式二

    BottomDialog.with(MainActivity.this, R.layout.dialog_frgment_share)
          .setCallBack(new BottomDialog.CallBack() {
                        @Override
                        public void handle(View view) {

                            view.findViewById(R.id.tv_bottom_sheet_heading)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toast("TTTTTTTTTTTTTtt");
                                    BottomDialog.getInstance().dismissDialog();
                                }
                            });

                        }
                    }).showDialog();

http
---

cache
---
1、存数据【本地SD卡及内存使用LRU算法】,对于不同类型，如bitmap或者Object key可以一样，不会覆盖

    String key1 = "xxx";
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    CacheManager.with().putBitmap(key1, bitmap);

    String key2 = "yyyy";
    CacheManager.with().putObject(key2, "11111");

2、取数据

    Bitmap bitmap1 = CacheManager.with().getBitmap(key1);
    String object = (String) CacheManager.with().getObject(key2);

3、清除数据【默认SD大于100MB自动删除SD缓存数据】

    CacheManager.with().clean();

ActivtyResultUtils 处理onActivityResult方法
---
    int requestCode=123;
    ActivtyResultUtils.with(this).startForResult(QiShuiSeeBigPictureActivity.class, requestCode, 
      new ActivtyResultUtils.Callback() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }
    });

TakePictureUtils 拍照及选取相册图片
---
1、默认路径及名字【拍照】

    TakePictureUtils.with(this).takePicture(new TakePictureUtils.TakePictureCallback() {
        @Override
        public void show(String path) {

        }
    });

2、自定义路径及名字【拍照】

    String picPath="sdcard/xxx";
    String picName="yyy.jpg";
    TakePictureUtils.with(this).takePicture(picPath,picName,new TakePictureUtils.TakePictureCallback() {
        @Override
        public void show(String path) {

        }
    });

3、选区相册图片

    TakePictureUtils.with(this).selectPicture(new TakePictureUtils.SelectPictureCallBack() {
        @Override
        public void show(String path) {

        }
    });

MVP
---
使用用例

1、QiShuiMVPContract 所有要实现的UI及数据加载及逻辑约束控制类

    public class QiShuiMVPContract {
    
        interface ILoginView extends MvpBaseView{
    
            /**
             * 提示信息
             * @param message
             */
            void show(String message);
    
            /**
             * 设置加载进度条
             * @param text
             */
            void loadingDialog(String text);
    
            /**
             * 关闭进度条
             */
            void dissmissDialog();
    
            /**
             * 页面跳转
             */
            void nextPage();
        }
    
        // m p 一般是相对应的，排除同一个请求使用不同参数
    
        interface ILoginModel extends MvpBaseModel {
    
            /**
             * 进行联网操作 登录
             * @param url
             * @param map
             * @param callBack
             */
            void requestLogin(String url, HashMap<String,Object> map, ICallBack callBack);
        }
    
        interface IPresenter {
    
            /**
             * 请求登录
             * @param userName
             * @param password
             */
            void login(String userName,String password);
    
        }
    
    }

2、QiShuiLoginModel 数据操作类

    public class QiShuiLoginModel implements QiShuiMVPContract.ILoginModel {
    
        @Override
        public void requestLogin(String url, HashMap<String, Object> map, ICallBack callBack) {
            /**
             * 实际请求网络
             */
            HttpManager.with().getProxy().get(url,map,callBack);
        }
    }
3、QiShuiLoginPresenter业务处理

    public class QiShuiLoginPresenter
    extends MvpBasePresenter<QiShuiMVPContract.ILoginView,QiShuiLoginModel> implements
    QiShuiMVPContract.IPresenter{
    
        public QiShuiLoginPresenter(QiShuiLoginModel qiShuiLoginModel) {
            super(qiShuiLoginModel);
        }
    
        @Override
        public void login(final String userName, final String password) {
    
            if(isAttachView()){
                if(TextUtils.isEmpty(userName)){
                    getView().show("用户名不能为空!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    getView().show("密码不能为空!");
                    return;
                }
                getView().loadingDialog("正在登录中...");
            }
    
            String url="https://www.baidu.com";
            HashMap<String, Object> map=new HashMap<>();
            map.put("username",userName);
            map.put("password",password);
            getModel().requestLogin(url, map, new StringCallBack() {
                @Override
                protected void onEasySuccess(String result) {
    
                    //提示登录成功，及发生跳转
                    if(isAttachView()){
                        getView().show(userName+"_"+result);
                        getView().nextPage();
                    }
    
                }
    
                @Override
                protected void onEasyError(String message) {
    
                    //提示错误信息
                    if(isAttachView()){
                        getView().show(userName+"_"+message);
                    }
    
                }
    
                @Override
                public void onLast() {
                    //关闭进度条
                    if(isAttachView()){
                        getView().dissmissDialog();
                    }
                }
            });
    
    
        }
    }

4、页面转移 MVP 使用实例 login

    public class QiShuiLoginActivity
    extends MvpBaseActivity<QiShuiMVPContract.ILoginView, QiShuiLoginModel, QiShuiLoginPresenter>
    implements QiShuiMVPContract.ILoginView {
    
        private EditText etName;
        private EditText etPassword;
    
        @Override
        protected QiShuiLoginPresenter createPresenter() {
            return new QiShuiLoginPresenter(new QiShuiLoginModel());
        }
    
        @Override
        protected int initLayout() {
            return R.layout.activity_qi_shui_login;
        }
    
        @Override
        protected void initEvent(Bundle savedInstanceState) {
    
            bindView();
            login();
        }
    
        private void bindView() {
            etName = findViewById(R.id.qishui_login_et_name);
            etPassword = findViewById(R.id.qishui_login_et_password);
        }
    
        /**
         * 登录
         */
        public void login() {
            findViewById(R.id.qishui_login_btn)
            .setOnClickListener(new QiShuiClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getPresenter() != null) {
                        getPresenter().login(getText(etName), getText(etPassword));
                    }
                }
            }));
        }
    
        @Override
        public void show(String message) {
            toast(message);
        }
    
        @Override
        public void loadingDialog(String text) {
            LoadingDialog.with(this).setText(text).showDialog();
        }
    
        @Override
        public void dissmissDialog() {
            LoadingDialog.with(this).dismissDialog();
        }
    
        @Override
        public void nextPage() {
             startActivity(QiShuiMainStyle01Activity.class);
        }
    
        @Override
        protected void onDestroy() {
            super.onDestroy();
            LoadingDialog.with(this).dismissDialog();
        }
    }
    
MVP总结 2019年1月4日16:33:19

    把要在Activity要处理的，以view分割处理事件，model获取数据 转移到presenter里面处理。
    
    详情参见源码，关于基类mvp封装、网络部分封装。

OnClickListener
---
防止按钮快速点击,默认1s之外

    view.setOnClickListener(new QiShuiClick(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    }));

annotation
----------
1、QBindView、QBindOnclick 可用在activity或者fragment,需要继承基类或则单独初始化。

    public class MainFragment extends BaseQiShuiFragment {

        @QBindView(R.id.fragment_tv1)
        TextView tv;
        @QBindView(R.id.fragment_tv2)
        TextView tv2;


        @Override
        protected int initLayout() {
            return R.layout.fragment_main_test;
        }

        @Override
        protected void initEvent(Bundle savedInstanceState) {

            tv.setText("Hello");
            tv2.setText("world");

        }

        @QBindOnclick({R.id.fragment_tv1, R.id.fragment_tv2})
        void test(View view) {
            if (view.getId() == R.id.fragment_tv1) {
                toast("!!!!!!!!!!!!!!!!!!!!");
            }

            if (view.getId() == R.id.fragment_tv2) {
                toast("222222222222222");
            }
        }
    }

静态加载fragment

        <fragment
        android:name="com.qishui.zutils.MainFragment"
        android:id="@+id/fragment1"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

state
---
页面布局切换

1、设置属性,可以自定义加载视图(加载中、加载失败、网络出错、数据为空),可以设置默认视图背景颜色、文字提示、图片资源、监听回调视图等。

注释:当处于fragment根目录，需要继承BaseQiShuiFragment或则在控件外面包裹一层布局即可。

ll 为要显示状态变化的viewGroup控件

    layoutManager = StateLayoutManager
                .with(ll)
                .setLoadingColor(0xffFF4081)
                .setNetErrorColor(0xffFF4081);
调用

    //加载中
    stateLayoutManager.showLoading();   
    
    //处理视图为空，并回调处理
    stateLayoutManager.setCallBack(new StateLayoutManager.CallBack() {
                @Override
                public void handle(View view) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }).showDataEmpty();
       
    //网络错误
    layoutManager.showNetworkError();
    
    //数据加载错误
    layoutManager.showDataError();
    
    //加载原来视图
    layoutManager.showContent();

主页面框架 更新时间 2019年1月12日 14点17分

1、TabLayout + Fragment 实现主页框架QiShuiMainStyle01Activity.class

     // 提供自定义的布局添加Tab
            for (int i = 0; i < mFragmensts.length; i++) {
                TabLayout.Tab tab = mTabLayout.newTab();
                tab.setCustomView(DataGenerator.getTabView(0, i));
                LinearLayout layout = tab.view;
                layout.setBackgroundColor(UiUtils.getColor(R.color.colorWhite));
                mTabLayout.addTab(tab);
            }

2、BottomNavigationView + Fragment 实现主页框架QiShuiMainStyle02Activity.class

     <android.support.design.widget.BottomNavigationView
            android:background="@color/colorWhite"
            android:id="@+id/bottom_navigation_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:itemBackground="@color/colorWhite"
            app:labelVisibilityMode="labeled"
            app:elevation="2dp"
            app:itemTextColor="@android:color/black"
            app:itemIconTint="@android:color/black"
            app:menu="@menu/navigation_menu"
            />

3、RadioGroup + RadioButton + Fragment 实现主页框架QiShuiMainStyle03Activity.class

    子view是否裁剪 android:clipChildren="false"

        <!--主页rg样式-->
        <style name="RadioGroupButtonStyle" >
            <!-- 这个属性是去掉button 默认样式-->
            <item name="android:button">@null</item>
            <item name="android:background">"@color/colorTransparent</item>
            <item name="android:gravity">center</item>
            <item name="android:layout_width">0dp</item>
            <item name="android:layout_height">wrap_content</item>
            <item name="android:layout_weight">1</item>
            <item name="android:textSize">12sp</item>
            <item name="android:textColor">@drawable/color_selector</item>
        </style>

4、自定义CustomTabView实现主页面框架QiShuiMainStyle04Activity.class

       <com.qishui.commontoolslibrary.view.CustomTabView
            android:id="@+id/custom_tab_container"
            android:layout_width="match_parent"
            android:layout_height="55dp"
            android:gravity="center"/>


     customTabView = findViewById(R.id.custom_tab_container);
            customTabView.addTabView(R.layout.view_custom, 4)
            .setOnTabCheckListener(new CustomTabView.OnTabCheckListener() {

                @Override
                public void onSetTabAttrs(List<View> mTabViews) {
                    for (int position = 0; position < mTabViews.size(); position++) {
                        TextView textView = mTabViews.get(position).findViewById(R.id.custom_tv);
                        textView.setText(mTabTitle[position]);
                    }
                }

                @Override
                public void onTabSelected(View view, int position) {

                    TextView textView = view.findViewById(R.id.custom_tv);
                    ImageView iv = view.findViewById(R.id.custom_iv);
                    textView.setTextColor(Color.RED);
                    iv.setImageResource(R.drawable.ic_tab_strip_icon_feed_selected);
                    toast(mTabTitle[position]);
                }

                @Override
                public void onTabUnSelected(View view, int position) {

                    TextView textView = view.findViewById(R.id.custom_tv);
                    ImageView iv = view.findViewById(R.id.custom_iv);
                    textView.setTextColor(Color.BLUE);
                    iv.setImageResource(R.drawable.ic_tab_strip_icon_feed);
                }
            }).setCurrentItem(0);

实现view

        public class CustomTabView extends LinearLayout {

            /**
             * 保存TabView
             */
            private List<View> mTabViews;
            private ArrayList<Integer> mHistoryPosition;


            /**
             * 点击回调
             */
            private OnTabCheckListener mOnTabCheckListener;

            public interface OnTabCheckListener {

                /**
                 * 设置属性
                 *
                 * @param mTabViews
                 */
                void onSetTabAttrs(List<View> mTabViews);

                /**
                 * 选中
                 *
                 * @param view
                 * @param position
                 */
                void onTabSelected(View view, int position);

                /**
                 * 未选择
                 *
                 * @param view
                 * @param position
                 */
                void onTabUnSelected(View view, int position);
            }

            public CustomTabView setOnTabCheckListener(OnTabCheckListener onTabCheckListener) {
                mOnTabCheckListener = onTabCheckListener;
                return this;
            }

            public CustomTabView(Context context, @Nullable AttributeSet attrs) {
                super(context, attrs);
                setOrientation(HORIZONTAL);
                setGravity(Gravity.CENTER);
                mTabViews = new ArrayList<>();
                mHistoryPosition = new ArrayList<>();
            }

            public CustomTabView(Context context) {
                this(context, null);
            }


            /**
             * 添加自定义视图
             *
             * @param layoutResId
             * @return
             */
            public CustomTabView addTabView(int layoutResId) {
                View tempView = UiUtils.inflate(layoutResId);
                tempView.setTag(mTabViews.size());
                if (tempView == null) {
                    return this;
                }
                tempView.setOnClickListener(new QiShuiClick(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();
                        if (getLastPosition() == position) {
                            return;
                        }
                        mHistoryPosition.add(position);
                        updateState(position);
                    }
                }));
                mTabViews.add(tempView);
                //添加到容器里面
                addView(tempView);
                return this;
            }

            /**
             * 一次添加多个view
             *
             * @param layoutResId
             * @param num
             * @return
             */
            public CustomTabView addTabView(int layoutResId, int num) {
                if (num <= 0) {
                    return this;
                }
                //限制最大数量
                if (num > 5) {
                    num = 5;
                }

                for (int i = 0; i < num; i++) {
                    addTabView(layoutResId);
                }
                return this;
            }

            /**
             * 设置选中Tab
             *
             * @param curPosition
             */
            public void setCurrentItem(int curPosition) {

                if (curPosition >= mTabViews.size()) {
                    return;
                }
                View view = mTabViews.get(curPosition);
                if (view == null) {
                    return;
                }
                mHistoryPosition.add(curPosition);

                //初始化属性
                if (mOnTabCheckListener != null) {
                    mOnTabCheckListener.onSetTabAttrs(mTabViews);
                }
                boolean flag = view.performClick();
                if (flag) {
                    updateState(curPosition);
                }

            }


            /**
             * 更新状态
             *
             * @param curPosition
             */
            private void updateState(int curPosition) {

                if (mTabViews == null) {
                    return;
                }
                for (int position = 0; position < mTabViews.size(); position++) {
                    View view = mTabViews.get(position);
                    if (mOnTabCheckListener != null && curPosition == position) {
                        mOnTabCheckListener.onTabSelected(view, curPosition);
                    } else if (mOnTabCheckListener != null && curPosition != position) {
                        mOnTabCheckListener.onTabUnSelected(view, curPosition);
                    }
                }
            }

            /**
             * 获取最后点击位置
             * @return
             */
            public int getLastPosition() {
                int position = 0;
                if (mHistoryPosition != null && mHistoryPosition.size() > 0) {
                    position = mHistoryPosition.get(mHistoryPosition.size() - 1);
                }
                return position;
            }


            @Override
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                if (mTabViews != null) {
                    mTabViews.clear();
                }
                if (mHistoryPosition != null) {
                    mHistoryPosition.clear();
                }
            }

            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                // 调整每个Tab的大小
                int size = mTabViews.size();
                for (int i = 0; i < size; i++) {
                    View view = mTabViews.get(i);
                    int width = getResources().getDisplayMetrics().widthPixels / size;
                    LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    view.setLayoutParams(params);
                }
            }
        }

banner 支持添加布局、ImageView滑动、自定义滑动样式
---

1、默认

       //数据
       List<Object> list = new ArrayList<>();
       list.add(R.drawable.banner);
       list.add(R.drawable.banner);
       list.add(R.drawable.banner);
       bannerView.setUnSelectId(R.drawable.white_point).setSelectId(R.drawable.red_point).setLocationLeft().setListViews(list).setImageLoader(new BannerView.ImageLoader() {
           @Override
           public void show(Context context, Object obj, ImageView iv) {
               iv.setImageResource((Integer) obj);
           }
       }).setBannerClick(new BannerView.BannerCallBack() {
           @Override
           public void click(View view, int position) {
               toast("QQQQQQQQQQQQQQQ");
           }
       }).setAutoPlay(false).showView();

2、自定义

        //数据
        List<Integer> list2 = new ArrayList<>();
        list2.add(R.layout.state_data_empty);
        list2.add(R.layout.state_data_error);

        final List<String> listTitle = new ArrayList<>();
        listTitle.add("康熙有多数老年人都存在的“不服输”的心理。虽然年龄摆在那儿了，但是仍然不承认自己力不从心");
        listTitle.add("年迈的狮子是最敏感的");

        //控件
        bannerView2.setLoadView(R.layout.view_banner_1);
        View bannerView = bannerView2.getBannerView();
        ViewPager vp = UiUtils.findViewById(bannerView, R.id.banner_vp);
        final TextView titleTv = UiUtils.findViewById(bannerView, R.id.banner_tv);
        titleTv.setText(listTitle.get(0));
        bannerView2.setViewPager(vp).setListResIds(list2).setDelayTime(3500).sePageChangeListenert(new BannerView.PageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                titleTv.setText(listTitle.get(position));
            }
        }).showView();
        
需要在生命周期时调用

      @Override
        protected void onStart() {
            super.onStart();
            if(bannerView!=null){
              bannerView.stratPlay();
            }
            if(bannerView2!=null){
              bannerView2.stratPlay();
            }

        }
    
        @Override
        protected void onStop() {
            super.onStop();
             if(bannerView!=null){
                 bannerView.stopPlay();
             }
            if(bannerView!=null){
               bannerView2.stopPlay();
            }
        }
        
更新时间：2019年1月15日17:24:10 

