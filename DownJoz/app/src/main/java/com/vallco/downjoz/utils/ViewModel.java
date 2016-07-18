package com.vallco.downjoz.utils;

public class ViewModel {

    private String id;
    private String text;
    private String image;

    public ViewModel(String id, String text, String image) {
        this.id = id;
        this.text = text;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

   /* public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setImage(String image) {
        this.image = image;
    }
*/
}
