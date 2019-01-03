# ZUtils工具框架开发记录

2019年1月3日16:51:40

使用方式

    1、Add it in your root build.gradle at the end of repositories:

    allprojects {
    		repositories {
    			maven { url 'https://jitpack.io' }
    		}
    	}

    2、Add the dependency

    dependencies {
    	api 'com.github.zhouwenxi:ZUtils:1.0.1'
    }

permisssion
---
1、Activity\Fragment请求权限及拒绝回调处理

    PermissionUtils.with(this)
                .addPermissions(PermissionUtils.GROUP_STORAGE)
                .addPermissions(PermissionUtils.GROUP_CAMERA)
                .addPermissions(PermissionUtils.GROUP_LOCATION)
                .request()
                .setCallback(new PermissionUtils.Callback() {
                    @Override
                    public void refuse() {
                        PermissionUtils.goSetInfo(MainActivity.this, PermissionUtils.getPermission(), new PermissionUtils.SetCallback() {
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
    ActivtyResultUtils.with(this).startForResult(QiShuiSeeBigPictureActivity.class, requestCode, new ActivtyResultUtils.Callback() {
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

OnClickListener
---
防止按钮快速点击,默认1s之外

    view.setOnClickListener(new QiShuiClick(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    }));

