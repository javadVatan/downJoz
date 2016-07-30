package com.vallco.downjoz.utils;

public class ViewModel {

    private int id;
    private String bookName, author, typeVersion, typeProduct, priceProduct, productDescription, downloadCount;
    private String image;

    public ViewModel(int id, String bookName, String image, String author, String typeVersion, String typeProduct,
                     String productDescription, String downloadCount, String priceProduct) {
        this.id = id;
        this.bookName = bookName;
        this.image = image;
        this.author=author;
        this.typeVersion=typeVersion;
        this.typeProduct=typeProduct;
        this.priceProduct=priceProduct;
        this.productDescription=productDescription;
        this.downloadCount=downloadCount;
    }


    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getTypeVersion() {
        return typeVersion;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public String getImageUrl() {
        return image;
    }
}
