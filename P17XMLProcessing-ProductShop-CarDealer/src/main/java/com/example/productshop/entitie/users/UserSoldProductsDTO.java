package com.example.productshop.entities.user;

import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductSoldProductDTO;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<ProductSoldProductDTO> soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductSoldProductDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSoldProductDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
