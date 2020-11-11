package clinic.bd.com.tclinic.bean;

import cn.bmob.v3.BmobObject;


public class CustomerBean extends BmobObject {

    private String name;
    private int gender;
    private int age;
    private String telNumber;
    private String description;
    private String title;
    private String preference;
    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public void updateWithNewObject(CustomerBean bean) {
        this.name = bean.getName();
        this.gender = bean.getGender();
        this.age = bean.getAge();
        this.telNumber = bean.getTelNumber();
        this.desp = bean.getDesp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerBean that = (CustomerBean) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", telNumber='" + telNumber + '\'' +
                '}';
    }
}
