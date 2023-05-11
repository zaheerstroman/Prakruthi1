package com.prakruthi.ui.ui.home.products;

public class HomeProductModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String attachment;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public HomeProductModel(int id,String name,String description,String attachment)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attachment = attachment;
    }
}
