package lanqiao.bean;

import java.sql.Date;

/**
 * @Author 梁金梅
 * @Date: 2022/12/04/ 18:11
 * @Version 1.0
 */
public class Purchase {
    private String name;
    private String unit;
    private double purchase;
    private int number;
    private double sell;
    private int expiration;
    private String supplier;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Purchase(String name, String unit, double purchase, int number, double sell, int expiration, String supplier, Date date) {
        this.name = name;
        this.unit = unit;
        this.purchase = purchase;
        this.number = number;
        this.sell = sell;
        this.expiration = expiration;
        this.supplier = supplier;
        this.date = date;
    }

    @Override
    public String toString() {
        return "stock{" +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", purchase=" + purchase +
                ", number=" + number +
                ", sell=" + sell +
                ", expiration=" + expiration +
                ", supplier='" + supplier + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
