package softuni.WatchUSeek.web.api;

public class WatchResponseModel {

    private String manufacturer;
    private String refNumber;
    private Integer year;
    private String picUrl;
    private double price;
    private String description;
    private String contactNumber;
    private String gender;

    public WatchResponseModel(String manufacturer,
                              String refNumber,
                              Integer year,
                              String picUrl,
                              double price,
                              String description,
                              String contactNumber,
                              String gender) {
        this.manufacturer = manufacturer;
        this.refNumber = refNumber;
        this.year = year;
        this.picUrl = picUrl;
        this.price = price;
        this.description = description;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }

    public WatchResponseModel() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
