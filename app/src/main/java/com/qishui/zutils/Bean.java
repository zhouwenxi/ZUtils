package com.qishui.zutils;

import java.io.Serializable;
import java.util.List;

public class Bean  {

    /**
     * data : [{"time":"08:41","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-28","lng":115.981981,"note":" ","lat":28.716996},{"time":"17:33","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-27","lng":115.979323,"note":" ","lat":28.716921},{"time":"08:40","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-27","lng":115.981838,"note":" ","lat":28.717013},{"time":"17:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-26","lng":115.979163,"note":" ","lat":28.716951},{"time":"08:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-26","lng":115.981029,"note":" ","lat":28.716928},{"time":"17:34","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-25","lng":115.979243,"note":" ","lat":28.717071},{"time":"08:39","NAME":"周文熙","location":"江西省南昌市青山湖区京东大道交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-25","lng":115.980297,"note":" ","lat":28.717087},{"time":"17:44","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-24","lng":115.979341,"note":" ","lat":28.716931},{"time":"08:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-24","lng":115.981316,"note":" ","lat":28.71694},{"time":"19:36","NAME":"周文熙","JOB_NUMBER":"20180089","location":"江西省南昌市青山湖区火炬五路北沥三和公寓","verify":3,"data":"2018-12-21","lng":115.986111,"note":"不在范围内打卡无效","lat":28.720524},{"time":"18:53","NAME":"周文熙","JOB_NUMBER":"20180089","location":"江西省南昌市青山湖区火炬五路北沥三和公寓","verify":3,"data":"2018-12-21","lng":115.986118,"note":"不在范围内打卡无效","lat":28.720511},{"time":"18:28","NAME":"周文熙","location":"江西省南昌市青山湖区京东大道818号中凯国际-D座","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.979394,"note":" ","lat":28.714862},{"time":"17:38","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.979126,"note":" ","lat":28.716955},{"time":"17:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.97932,"note":" ","lat":28.716923},{"time":"17:02","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.979314,"note":" ","lat":28.716922},{"time":"17:02","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.979314,"note":" ","lat":28.716922},{"time":"08:38","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-21","lng":115.980516,"note":" ","lat":28.71702},{"time":"17:35","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-20","lng":115.979219,"note":" ","lat":28.716934},{"time":"08:35","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大名都","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-20","lng":115.983103,"note":" ","lat":28.717074},{"time":"17:33","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-19","lng":115.979293,"note":" ","lat":28.716982},{"time":"08:39","NAME":"周文熙","location":"江西省南昌市青山湖区京东大道交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-19","lng":115.98003,"note":" ","lat":28.716892},{"time":"17:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-18","lng":115.979082,"note":" ","lat":28.716942},{"time":"08:44","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-18","lng":115.978137,"note":" ","lat":28.717125},{"time":"17:52","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-17","lng":115.979366,"note":" ","lat":28.716933},{"time":"08:41","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-17","lng":115.978136,"note":" ","lat":28.71714},{"time":"17:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-14","lng":115.979212,"note":" ","lat":28.716893},{"time":"08:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-14","lng":115.979712,"note":" ","lat":28.716873},{"time":"17:42","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-13","lng":115.978181,"note":" ","lat":28.717147},{"time":"17:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-13","lng":115.979296,"note":" ","lat":28.716909},{"time":"08:38","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-13","lng":115.981999,"note":" ","lat":28.716976},{"time":"17:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-12","lng":115.979275,"note":" ","lat":28.716904},{"time":"08:40","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大名都","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-12","lng":115.982066,"note":" ","lat":28.7172},{"time":"17:45","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-11","lng":115.979082,"note":" ","lat":28.716948},{"time":"08:40","NAME":"周文熙","location":"江西省南昌市青山湖区京东大道交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-11","lng":115.980316,"note":" ","lat":28.716938},{"time":"17:45","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-10","lng":115.979292,"note":" ","lat":28.716886},{"time":"08:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大名都","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-10","lng":115.982959,"note":" ","lat":28.717039},{"time":"17:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-07","lng":115.979189,"note":" ","lat":28.716923},{"time":"08:37","NAME":"周文熙","location":"江西省南昌市青山湖区京东大道交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-07","lng":115.980276,"note":" ","lat":28.716891},{"time":"17:35","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-06","lng":115.979225,"note":" ","lat":28.716916},{"time":"08:35","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路交叉口恒大中心写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-06","lng":115.98097,"note":" ","lat":28.71701},{"time":"17:36","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-05","lng":115.979102,"note":" ","lat":28.716944},{"time":"08:33","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-05","lng":115.982375,"note":" ","lat":28.716947},{"time":"17:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-04","lng":115.979066,"note":" ","lat":28.716936},{"time":"08:37","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-04","lng":115.979283,"note":" ","lat":28.716942},{"time":"08:32","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大名都","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-04","lng":115.982009,"note":" ","lat":28.717127},{"time":"17:35","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路新城吾悦广场写字楼","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-03","lng":115.979064,"note":" ","lat":28.716946},{"time":"08:32","NAME":"周文熙","location":"江西省南昌市青山湖区艾溪湖北路恒大SOHO","JOB_NUMBER":20180089,"verify":3,"data":"2018-12-03","lng":115.982433,"note":" ","lat":28.71697}]
     * code : 200
     * msg : 获取打卡记录成功!
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 08:41
         * NAME : 周文熙
         * location : 江西省南昌市青山湖区艾溪湖北路恒大SOHO
         * JOB_NUMBER : 20180089
         * verify : 3
         * data : 2018-12-28
         * lng : 115.981981
         * note :
         * lat : 28.716996
         */

        private String time;
        private String NAME;
        private String location;
        private int JOB_NUMBER;
        private int verify;
        private String data;
        private double lng;
        private String note;
        private double lat;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getJOB_NUMBER() {
            return JOB_NUMBER;
        }

        public void setJOB_NUMBER(int JOB_NUMBER) {
            this.JOB_NUMBER = JOB_NUMBER;
        }

        public int getVerify() {
            return verify;
        }

        public void setVerify(int verify) {
            this.verify = verify;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
