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
    	api 'com.github.zhouwenxi:ZUtils:1.0.4'
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

    public class QiShuiLoginPresenter extends MvpBasePresenter<QiShuiMVPContract.ILoginView,QiShuiLoginModel> implements QiShuiMVPContract.IPresenter{
    
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

    public class QiShuiLoginActivity extends MvpBaseActivity<QiShuiMVPContract.ILoginView, QiShuiLoginModel, QiShuiLoginPresenter> implements QiShuiMVPContract.ILoginView {
    
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
            findViewById(R.id.qishui_login_btn).setOnClickListener(new QiShuiClick(new View.OnClickListener() {
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
                                            Toast.makeText(MainActivity.this, "xxoxoxoxoxoxo", Toast.LENGTH_SHORT).show();
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