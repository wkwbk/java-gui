package model;

public class Hotel {
    String hotelName;
    String hotelAddress;
    String hotelPhone;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    @Override
    public String toString() {
        return "Hotel [hotelName=" + hotelName + ", hotelAddress=" + hotelAddress + ", hotelPhone=" + hotelPhone + "]";
    }
}
