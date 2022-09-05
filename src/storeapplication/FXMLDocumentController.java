/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeapplication;


import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import  java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;

/**
 *
 * @author Uththara Tharukshi
 */


public class FXMLDocumentController implements Initializable {
    
     @FXML
    private Label label;


   

    @FXML
    private TextField textname;

    @FXML
    private TextField txtauthor;

    @FXML
    private TextField txtqty;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<book> table;

    @FXML
    private TableColumn<book, String> IdColumn;

    @FXML
    private TableColumn<book, String> nameColumn;

    @FXML
    private TableColumn<book, String> authorColumn;

    @FXML
    private TableColumn<book, String> qtyColumn;

    @FXML
    void Clear(ActionEvent event) {
        
            textname.setText("");
            txtauthor.setText("");
            txtqty.setText("");

    }

    

    @FXML
    void Insert(ActionEvent event) {
        
        String bname, author, qty;
        

           bname = textname.getText();
           author = txtauthor.getText();
           qty = txtqty.getText();
        
           try
        {
            pst = con.prepareStatement("insert into book(bname,author,quantity)values(?,?,?)");
            pst.setString(1, bname);
            pst.setString(2, author);
            pst.setString(3, qty);
            pst.executeUpdate();
          
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Test Connection");
 
             alert.setHeaderText("Book Management");
             alert.setContentText("Record Added!!!"); 

             alert.showAndWait();
 
            table();
            
            textname.setText("");
            txtauthor.setText("");
            txtqty.setText("");
            textname.requestFocus();
        }
           
           catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

           public void table()
      {
          Connect();
          ObservableList<book> books = FXCollections.observableArrayList();
       try
       {
           pst = con.prepareStatement("select id,bname,author,quantity from book");  
           ResultSet rs = pst.executeQuery();
      {
        while (rs.next())
        {
            book b = new book();
            b.setId(rs.getString("id"));
            b.setName(rs.getString("bname"));
            b.setauthor(rs.getString("author")) ;
            b.setqty(rs.getString("quantity"));
            books.add(b);
        }
    }
                table.setItems(books);
                IdColumn.setCellValueFactory(f -> f.getValue().idProperty());
                nameColumn.setCellValueFactory(f -> f.getValue().nameProperty());
                authorColumn.setCellValueFactory(f -> f.getValue().authorProperty());
                qtyColumn.setCellValueFactory(f -> f.getValue().qtyProperty());
                
              
 
       }
      
       catch (SQLException ex)
       {
           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }
 
                table.setRowFactory( tv -> {
                TableRow<book> myRow = new TableRow<>();
                myRow.setOnMouseClicked (event ->
     {
        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
        {
            myIndex =  table.getSelectionModel().getSelectedIndex();
           id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
           textname.setText(table.getItems().get(myIndex).getName());
           txtauthor.setText(table.getItems().get(myIndex).getauthor());
           txtqty.setText(table.getItems().get(myIndex).getqty());
                          
                        
                          
        }
     });
        return myRow;
                   });
    
    
      }
        
        
        
         @FXML
    void Delete(ActionEvent event) {
        
         myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    
 
        try
        {
            pst = con.prepareStatement("delete from book where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
 
            alert.setHeaderText("Book Management");
            alert.setContentText("Deleted!");
 
              alert.showAndWait();
              table();

    }
        
         
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
        
        
        
        

    

    @FXML
    void Update(ActionEvent event) {
        
        
         String bname, author, qty;
        
         myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
          
            bname = textname.getText();
            author = txtauthor.getText();
            qty = txtqty.getText();
        
           try
        {
            pst = con.prepareStatement("update book set bname = ?,author = ? ,quantity = ? where id = ? ");
            pst.setString(1, bname);
            pst.setString(2, author);
            pst.setString(3, qty);
            pst.setInt(4, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
 
            alert.setHeaderText("Book Management");
            alert.setContentText("Updated!");
 
                alert.showAndWait();
                table();
                
                
                
                 }
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }

    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;
    
    
    
     public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bookstore","root","");
            
        } catch (ClassNotFoundException ex) {
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connect();
        table();

        
    } 
    }    
    




