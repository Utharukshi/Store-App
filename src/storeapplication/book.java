/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeapplication;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Uththara Tharukshi
 */
public class book {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty author;
    private final StringProperty  qty;
    
    public book()
    {
        id = new SimpleStringProperty(this, "id");
        name = new SimpleStringProperty(this, "name");
        author = new SimpleStringProperty(this, "author");
        qty = new SimpleStringProperty(this, "qty");
    }
 
    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }
 
    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String newName) { name.set(newName); }
 
    public StringProperty authorProperty() { return author; }
    public String getauthor() { return author.get(); }
    public void setauthor(String newAuthor) { author.set(newAuthor); }
    
    public StringProperty qtyProperty() { return qty; }
    public String getqty() { return qty.get(); }
    public void setqty(String newqty) { qty.set(newqty); }
}



    

