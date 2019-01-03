# ZUtils工具框架开发记录

2019年1月3日16:51:40

dialog
---

1、ListDialog 列表对话框

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
