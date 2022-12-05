package lanqiao.bean;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public class Commodity {
    private int id;
    private String name;
    private String sort;
    private int number;
    private double purchase;
    private double sell;
    private String picAddress;

    public Commodity(int id, String name, String sort, int number, double purchase, double sell, String picAddress) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.number = number;
        this.purchase = purchase;
        this.sell = sell;
        this.picAddress=picAddress;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", number=" + number +
                ", purchase=" + purchase +
                ", sell=" + sell +
                '}';
    }
}
