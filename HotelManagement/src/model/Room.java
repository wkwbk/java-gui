package model;

public class Room {
    String roomNumber;
    String roomType;
    int roomPrice;
    int roomState;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getRoomState() {
        return roomState;
    }

    public void setRoomState(int roomState) {
        this.roomState = roomState;
    }

    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", roomState=" + roomState + "]";
    }
}
