package com.example.coffeeandmore;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Blob;

public class ModelProduct implements Parcelable {

    private int id , dubcategoryId ;
    private String productName ;
    private int productPrice ;
    private String ProductDescription ;
    private Image productImage ;
    private  String createdAt ;
    private int inStockTotale , isActive ;

    public ModelProduct() {
    }

    public ModelProduct(int id, int dubcategoryId, String productName, int productPrice, String productDescription, Image productImage, String createdAt, int inStockTotale, int isActive) {
        this.id = id;
        this.dubcategoryId = dubcategoryId;
        this.productName = productName;
        this.productPrice = productPrice;
        ProductDescription = productDescription;
        this.productImage = productImage;
        this.createdAt = createdAt;
        this.inStockTotale = inStockTotale;
        this.isActive = isActive;
    }

    protected ModelProduct(Parcel in) {
        id = in.readInt();
        dubcategoryId = in.readInt();
        productName = in.readString();
        productPrice = in.readInt();
        ProductDescription = in.readString();
        createdAt = in.readString();
        inStockTotale = in.readInt();
        isActive = in.readInt();
    }

    public static final Creator<ModelProduct> CREATOR = new Creator<ModelProduct>() {
        @Override
        public ModelProduct createFromParcel(Parcel in) {
            return new ModelProduct(in);
        }

        @Override
        public ModelProduct[] newArray(int size) {
            return new ModelProduct[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDubcategoryId() {
        return dubcategoryId;
    }

    public void setDubcategoryId(int dubcategoryId) {
        this.dubcategoryId = dubcategoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getInStockTotale() {
        return inStockTotale;
    }

    public void setInStockTotale(int inStockTotale) {
        this.inStockTotale = inStockTotale;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(dubcategoryId);
        dest.writeString(productName);
        dest.writeInt(productPrice);
        dest.writeString(ProductDescription);
        dest.writeString(createdAt);
        dest.writeInt(inStockTotale);
        dest.writeInt(isActive);
    }
}
