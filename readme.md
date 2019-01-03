# ZUtils工具框架开发记录

2019年1月3日16:51:40

使用方式

    1、Add it in your root build.gradle at the end of repositories:

    allprojects {
    		repositories {
    			...
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

    PermissionUtils.with(this).
                addPermissions(PermissionUtils.GROUP_STORAGE)
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

    public void onclick2(View view) {

        CommonDialog.with(this, CommonDialog.STYLE_IOS).setDialogTitle("你是否需要添加?").setDialogLeftText("不了").setDialogRightText("好的").setCallBack(new CommonDialog.CallBack() {
            @Override
            public void left() {

                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void right() {

                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        }).showDialog();

    }


2、ListDialog 列表对话框

    public void onclick1(View view) {

        List<Bean> list=new ArrayList<Bean>();
        list.add(new Bean("0x1233","hello world 0","江西"));
        list.add(new Bean("0x1234","hello world 1","北京"));
        list.add(new Bean("0x1235","hello world 2","上海"));
        
        new ListDialog<Bean>(this).setDialogLv(list).setCallback(new ListDialog.CallBack<Bean>() {
            @Override
            public void disPlay(Bean bean, int position) {

                Toast.makeText(MainActivity.this, bean.getCode(), Toast.LENGTH_SHORT).show();
            }
        }).showDialog();
        

    }
    

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
